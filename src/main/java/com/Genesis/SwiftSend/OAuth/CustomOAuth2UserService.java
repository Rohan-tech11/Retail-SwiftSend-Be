/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.OAuth;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.Genesis.SwiftSend.Registration.Token.JwtTokenService;

/**
 * @author rohan
 *
 */
@Service
public class CustomOAuth2UserService
		implements
			OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final JwtTokenService jwtTokenService;

	@Autowired
	public CustomOAuth2UserService(JwtTokenService jwtTokenService) {
		this.jwtTokenService = jwtTokenService;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest)
			throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oauth2User = delegate.loadUser(userRequest);

		// Custom logic to generate JWT
		String jwtToken = jwtTokenService.generateJwt(oauth2User);

		// Create authorities from OAuth2User attributes
		Set<SimpleGrantedAuthority> authorities = oauth2User.getAuthorities()
				.stream().map(authority -> new SimpleGrantedAuthority(
						authority.getAuthority()))
				.collect(Collectors.toSet());

		// Add default role for OAuth2 users (if needed)
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		// Return a custom OAuth2User implementation with JWT as an attribute
		return new CustomOAuth2User(oauth2User.getAttributes(), authorities,
				jwtToken);
	}
}