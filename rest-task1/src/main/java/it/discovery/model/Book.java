package it.discovery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity
@Getter
@Setter
@ToString
public class Book {
	@Id
	@GeneratedValue
	private int id;

	@NotNull
	@Size(min = 4, max = 40)
	private String author;

	@JsonProperty("title")
	@XmlElement(name = "title")
	private String name;

	@Min(1800)
	private int year;

	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<Order> orders;

	public void addOrder(int amount) {
		if(orders == null) {
			orders = new ArrayList<>();
		}
		Order order = new Order();
		order.setAmount(amount);
		order.setBook(this);
		orders.add(order);
	}

	public boolean cancelOrder(String uuid) {
		if(orders == null) {
			return false;
		}		
		boolean found = orders.removeIf(order -> order.getId().equals(UUID.fromString(uuid)));
		if (found) {
			System.out.println("Order " + uuid + "was cancelled");
		}
		return found;
	}
}
