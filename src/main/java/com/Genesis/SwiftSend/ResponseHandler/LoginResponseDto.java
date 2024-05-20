/**
 * created by @Rohan
 */
package com.Genesis.SwiftSend.ResponseHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rohan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

	private String userName;
	private String jwt;
	private boolean isAdminApproved;

	public LoginResponseDto(String userName, String jwt) {
		this.userName = userName;
		this.jwt = jwt;
	}

}
