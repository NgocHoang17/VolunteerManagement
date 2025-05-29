package vn.edu.volunteer.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

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
        if (session.getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null) {
            logger.info("Removing old saved request from session");
            session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
        }
        
        // Xác định URL đích dựa trên role
        String targetUrl = determineTargetUrl(roles);
        logger.info("Determined target URL: {}", targetUrl);
        
        // Thêm thông tin user vào session
        session.setAttribute("currentUser", authentication.getPrincipal());
        logger.info("Added user principal to session with ID: {}", session.getId());
        
        // Log session attributes
        java.util.Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            logger.debug("Session attribute - {}: {}", attributeName, session.getAttribute(attributeName));
        }
        
        // Chuyển hướng đến trang đích
        logger.info("Redirecting to: {}", targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        logger.info("=== Authentication Success Handler Completed ===");
    }
    
    private String determineTargetUrl(Set<String> roles) {
        logger.debug("Determining target URL for roles: {}", roles);
        String targetUrl;
        
        if (roles.contains("ROLE_ADMIN")) {
            targetUrl = "/admin/dashboard";
        } else if (roles.contains("ROLE_ORGANIZATION")) {
            targetUrl = "/organization/dashboard";
        } else if (roles.contains("ROLE_STUDENT")) {
            targetUrl = "/student/dashboard";
        } else if (roles.contains("ROLE_MANAGER")) {
            targetUrl = "/manager/dashboard";
        } else {
            targetUrl = "/home";
        }
        
        logger.debug("Determined target URL: {}", targetUrl);
        return targetUrl;
    }

    private boolean isUrlAllowedForRoles(String url, Set<String> roles) {
        logger.debug("Checking if URL '{}' is allowed for roles: {}", url, roles);
        boolean isAllowed;
        
        if (roles.contains("ROLE_ADMIN")) {
            isAllowed = true; // Admin có thể truy cập mọi URL
        } else if (roles.contains("ROLE_ORGANIZATION")) {
            isAllowed = url.startsWith("/organization") || url.startsWith("/profile");
        } else if (roles.contains("ROLE_STUDENT")) {
            isAllowed = url.startsWith("/student") || url.startsWith("/profile");
        } else if (roles.contains("ROLE_MANAGER")) {
            isAllowed = url.startsWith("/manager") || url.startsWith("/profile");
        } else {
            isAllowed = false;
        }
        
        logger.debug("URL '{}' allowed: {}", url, isAllowed);
        return isAllowed;
    }
} 