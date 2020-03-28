package com.ipl.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS_RECORD")
public class OrdersRecord {
	
	@Id
	@GeneratedValue
	(strategy = GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	private int orderId;
	
	@Column(name="USER_ID")
	private int userId;
	
	@OneToMany(mappedBy="ordersRecord")
	private List<OrderItem> items;
	
	public OrdersRecord() {
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public OrdersRecord(int userId) {
		this.userId = userId;
	}

	public void add(OrderItem item) {
		
		if(items == null) {
			items= new ArrayList<OrderItem>();
			
		}
		items.add(item);
		item.setOrdersRecord(this);
	}
	
}