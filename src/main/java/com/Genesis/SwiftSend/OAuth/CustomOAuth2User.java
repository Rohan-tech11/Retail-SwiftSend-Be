/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.OAuth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

/**
 * @author rohan
 *
 */
public class CustomOAuth2User extends DefaultOAuth2User {

	private String jwtToken;

	public CustomOAuth2User(Map<String, Object> attributes,
			Collection<? extends GrantedAuthority> authorities,
			String jwtToken) {
		super(authorities, attributes, "sub");
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}
}
