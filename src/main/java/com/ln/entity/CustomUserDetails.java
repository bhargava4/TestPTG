package com.ln.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(final User user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<SimpleGrantedAuthority> croles = new ArrayList<>();
		croles.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (super.getRoles() != null && super.getRoles().size() > 0) {
			super.getRoles().forEach(sr -> {
				croles.add(new SimpleGrantedAuthority("ROLE_" + sr));
			});
		}
		return croles;
	}

	@Override
	public String getUsername() {
		return super.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
