package com.ln;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ln.entity.CustomUserDetails;

public class SecurityUtils {

	public static CustomUserDetails getUser() {
		return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static String getUserId() {
		return getUser().getId();
	}

	public static List<String> getUserRoles() {
		return getUser().getAuthorities().stream().map(userAuth -> userAuth.getAuthority())
				.collect(Collectors.toList());
	}

}
