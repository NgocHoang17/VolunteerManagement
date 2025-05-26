package vn.edu.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.service.UserService;
import vn.edu.volunteer.service.HoatDongService;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HoatDongService hoatDongService;

    @GetMapping
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/manage/home";
        }
        model.addAttribute("mostPopularActivity", hoatDongService.getMostPopularActivity());
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/manage/home";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
            return "auth/register";
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "error.user", "Email đã được sử dụng");
            return "auth/register";
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            userService.save(user);
            
            redirectAttributes.addFlashAttribute("registrationSuccess", true);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại.");
            return "redirect:/register";
        }
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "auth/access-denied";
    }
} 