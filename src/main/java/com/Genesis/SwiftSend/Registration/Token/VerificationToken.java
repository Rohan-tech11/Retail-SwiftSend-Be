/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.Registration.Token;

import java.util.Calendar;
import java.util.Date;

import com.Genesis.SwiftSend.Client.Client;
import com.Genesis.SwiftSend.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rohan
 *
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Date expirationTime;
	private static final int EXPIRATION_TIME = 30;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public VerificationToken(String token, User user) {
		super();
		this.token = token;
		this.user = user;
		this.expirationTime = this.getTokenExpirationTime();
	}

	public VerificationToken(String token, Client client) {
		super();
		this.token = token;
		this.client = client;
		this.expirationTime = this.getTokenExpirationTime();
	}

	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationTime = this.getTokenExpirationTime();
	}

	public Date getTokenExpirationTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
		return new Date(calendar.getTime().getTime());
	}
}
