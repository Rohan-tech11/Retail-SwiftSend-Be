package com.Genesis.SwiftSend.Role;

import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles", indexes = @Index(columnList = "uuid", unique = true))
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = -140039704904803726L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	private String authority;

	@Column(unique = true, nullable = false, updatable = false)
	private UUID uuid;

	public Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	@PrePersist
	protected void onCreate() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	}
}