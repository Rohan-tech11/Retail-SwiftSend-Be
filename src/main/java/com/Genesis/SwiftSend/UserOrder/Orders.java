/**
 * created by @Rohan
 */
package com.Genesis.SwiftSend.UserOrder;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.Genesis.SwiftSend.Client.Client;
import com.Genesis.SwiftSend.Client.ClientServices;
import com.Genesis.SwiftSend.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rohan
 *
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_orders", indexes = @Index(columnList = "uuid", unique = true))
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)", unique = true, nullable = false, updatable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID uuid;

	@Column(nullable = false)
	private String source;

	@Column(nullable = false)
	private String destination;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "client_services_id")
	private ClientServices clientServices;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@Column(nullable = false)
	private String premium;

	@Column(nullable = false)
	private String dimensions;

	@Column(nullable = false)
	private double weight;

	@Column(nullable = false)
	private String type;

	@Column
	private String imageURL;

	// Constructors, getters, and setters

	@PrePersist
	protected void onCreate() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	}
}
