/**
 * created by @Rohan
 */
package com.Genesis.SwiftSend.Client;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.Genesis.SwiftSend.UserOrder.Orders;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author rohan
 *
 */
@Entity
@Table(name = "client_quotations")
@Data
public class ClientQuotation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)", unique = true, nullable = false, updatable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID uuid;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	private int deliveryDays;

	@Column(name = "platform_fee")
	private double platformFee;

	@Column(name = "tax")
	private double tax;

	@Column(name = "total_Cost")
	private double totalCost;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;

	// lifecycle callback if hibernate generator fails,it will add the uuid
	@PrePersist
	protected void onCreate() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	}
}
