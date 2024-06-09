/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.Registration.Token;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.Genesis.SwiftSend.Client.Client;
import com.Genesis.SwiftSend.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
@Table(name = "verificationToken", indexes = @Index(columnList = "uuid", unique = true))
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)", unique = true, nullable = false, updatable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID uuid;

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
	@PrePersist
	protected void onCreate() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	}
}
