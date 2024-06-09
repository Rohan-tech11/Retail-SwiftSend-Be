/**
 * created by @Rohan
 */
package com.Genesis.SwiftSend.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.type.SqlTypes;

import com.Genesis.SwiftSend.Role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rohan
 *
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "clients", indexes = @Index(columnList = "uuid", unique = true))
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "client_id")
	private Integer clientId;

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)", unique = true, nullable = false, updatable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID uuid;

	private String businessName;

	@NaturalId(mutable = true)
	@Column(unique = true)
	private String email;

	@JsonIgnore // Ignore this field during serialization
	private String password;

	@JsonIgnore // Ignore this field during serialization
	private boolean isEnabled = false;

	@Column(unique = true)
	@JsonIgnore // Ignore this field during serialization
	private String mobileNumber;

	@Column(unique = true)
	private String businessRegistryId;

	private String registeredOfficeLocation;

	private boolean isAdminApproved;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "client_role_junction", joinColumns = {
			@JoinColumn(name = "client_id")}, inverseJoinColumns = {
					@JoinColumn(name = "role_id")})
	private Set<Role> authorities;

	public Client() {
		super();
		authorities = new HashSet<>();
	}

	@PrePersist
	protected void onCreate() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	}

}
