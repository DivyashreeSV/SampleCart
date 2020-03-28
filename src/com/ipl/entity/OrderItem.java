package com.ipl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS_ITEMS")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SERIAL_NO")
	private int slNo;
	
	@Column(name="ITEM_ID")
	private int itemId;

	@ManyToOne
	@JoinColumn(name="ORDER_ID")
	private OrdersRecord ordersRecord;
	
	public OrderItem(int itemId) {
		this.itemId = itemId;
	}

	public OrderItem() {
	}

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public OrdersRecord getOrdersRecord() {
		return ordersRecord;
	}

	public void setOrdersRecord(OrdersRecord ordersRecord) {
		this.ordersRecord = ordersRecord;
	}
	
}
