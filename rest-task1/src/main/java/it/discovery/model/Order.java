package it.discovery.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "orders")
@Entity
@Getter
@Setter
@ToString(exclude = "book")
public class Order {
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	private LocalDateTime createdAt;
	
	private int amount;
	
	@PrePersist
	public void onCreated() {
		createdAt = LocalDateTime.now();
	}

}
