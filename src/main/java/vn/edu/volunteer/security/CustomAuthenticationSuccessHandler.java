package vn.edu.volunteer.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {
        
        logger.info("=== Authentication Success Handler Started ===");
        logger.info("User '{}' successfully authenticated", authentication.getName());
        
        HttpSession session = request.getSession();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        
        logger.info("User roles: {}", roles);
        
        // Xóa session cũ nếu có
        clearAuthenticationAttributes(request);
        
        // Xác định URL đích dựa trên role
        String targetUrl = determineTargetUrl(authentication);
        logger.info("Base target URL: {}", targetUrl);
        
        // Thêm thông tin user vào session
        session.setAttribute("currentUser", authentication.getPrincipal());
        logger.info("Added user principal to session with ID: {}", session.getId());
        
        // Chuyển hướng đến trang đích
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        logger.info("=== Authentication Success Handler Completed ===");
    }
    
    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("Determining target URL for user: {} with roles: {}", 
            authentication.getName(), roles);

        String targetUrl;
        if (roles.contains("ROLE_ADMIN")) {
            targetUrl = "/admin/dashboard";
            logger.info("User is ADMIN, target URL: {}", targetUrl);
        } else if (roles.contains("ROLE_ORGANIZATION")) {
            targetUrl = "/organization/dashboard";
            logger.info("User is ORGANIZATION, target URL: {}", targetUrl);
        } else if (roles.contains("ROLE_STUDENT")) {
            targetUrl = "/student/dashboard";
            logger.info("User is STUDENT, target URL: {}", targetUrl);
        } else {
            targetUrl = "/home";
            logger.warn("No specific role found, defaulting to: {}", targetUrl);
        }

        return targetUrl;
    }

    private boolean isUrlAllowed(String url, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("Checking if URL {} is allowed for roles: {}", url, roles);

        if (roles.contains("ROLE_ADMIN")) {
            return url.startsWith("/admin") || url.startsWith("/profile");
        } else if (roles.contains("ROLE_ORGANIZATION")) {
            return url.startsWith("/organization") || url.startsWith("/profile");
        } else if (roles.contains("ROLE_STUDENT")) {
            return url.startsWith("/student") || url.startsWith("/profile");
        }

        return false;
    }
} 