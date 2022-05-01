package com.KMA.BookingCare.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String targetUrl = determineTargetUrl(authentication);
		if (response.isCommitted()) {
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	public static List<String> getAuthorities() {
		List<String> results = new ArrayList<String>();
		List<GrantedAuthority> authorities = (List<GrantedAuthority>)(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        for (GrantedAuthority authority : authorities) {
            results.add(authority.getAuthority());
        }
		return results;
	}
	public String determineTargetUrl(Authentication authentication) {
		String url = "";
		List<String> roles = getAuthorities();
		if (isAdmin(roles)) {
			url = "/admin/home";
		} else { if (isUser(roles)) {
			url = "/home";
		}else 
			if (isDoctor(roles)) {
				url = "/doctor/home";
		}}
		return url;
	}
	
	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")||roles.contains("ROLE_MEMBER")) {
			return true;
		}
		return false;
	}
	private boolean isDoctor(List<String> roles) {
		if (roles.contains("ROLE_DOCTOR")) {
			return true;
		}
		return false;
	}
	
	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) {
			return true;
		}
		return false;
	}
}
