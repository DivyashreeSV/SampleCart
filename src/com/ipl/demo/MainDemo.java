package com.ipl.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ipl.entity.Items;
import com.ipl.entity.Login;
import com.ipl.entity.OrderItem;
import com.ipl.entity.OrdersRecord;

public class MainDemo {

	static Scanner scan = new Scanner(System.in);
	static int userId;
	static SessionFactory factory;
	static boolean status = false;
	static Map<Integer, Items> itemMap;
	static List<Items> itemList;

	static {
		factory = HibernateUtil.getSessionFactory();
		loadItems();
	}

	public static void main(String[] args) {
		while (!status) {
			if (validateUser()) {
				viewMenu();
			}
		}
	}

	public static void viewMenu() {
		System.out.println("Nandan");
		List<Items> cartItems = new ArrayList<Items>();
		boolean flag = true;
		do {
			System.out.println("\nMenu Option");
			System.out.println("1:Show Items\n2:Add Item to Cart \n3:View Cart\n4:Place Order");
			System.out.println("5:View previous orders\n6:Logout\n7:Exit");
			Integer option = scan.nextInt();
			switch (option) {
			case 1:
				showMenu();
				break;
			case 2:
				addItemToCart(cartItems);
				break;
			case 3:
				viewCart(cartItems);
				break;
			case 4:
				placeOrder(cartItems);
				break;
			case 5:
				viewPreviousOrders();
				break;
			case 6:
				flag = false;
				status = false;
				System.out.println("Successfully logged out.");
				break;
			case 7:
				flag = false;
				System.out.println("Successfully Application got shut down");
				break;
			default:
				System.out.println("Invalid Option");
			}
		} while (flag);
	}

	public static boolean validateUser() {

		System.out.println("Please enter UserId");
		Integer username = scan.nextInt();
		scan.nextLine();
		System.out.println("Please enter Password");
		String password = scan.nextLine();

		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Login login = session.get(Login.class, username);
			if (login != null) {
				if (login.getPassword().equals(password)) {
					System.out.println("Successfully Logged In");
					userId = username;
					status = true;

				} else {
					System.out.println("UserId/Password is incorrect, Please Retry");
				}
			} else {
				System.out.println("UserId does not exist.");
			}
		} finally {
			session.close();

		}
		return status;
	}

	public static void loadItems() {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			itemList = session.createCriteria(Items.class).list();
			if (itemList != null) {
				itemMap = new HashMap<>();
				for (Items items : itemList) {
					itemMap.put(items.getItemId(), items);
				}
			}
		} finally {
			session.close();
		}
	}

	public static void showMenu() {

		System.out.println("Present items are in the store : ");
		for (Items item : itemList) {
			System.out.println("Item Id : " + item.getItemId() + "--Item Name : " + item.getItemName());
		}

	}

	public static void addItemToCart(List<Items> cart) {
		showMenu();
		System.out.println("Enter Item Id to add into the cart");
		Integer itemId = scan.nextInt();

		if (!itemMap.containsKey(itemId)) {
			System.out.println("Invalid Item");
		} else {
			cart.add(itemMap.get(itemId));
			System.out.println("Successfully added to the cart \n");
		}

	}

	public static void viewCart(List<Items> cart) {
		System.out.println("Items in the cart :");
		if (cart != null && cart.size() != 0) {
			for (Items item : cart) {
				System.out.println(item.getItemId() + ":" + item.getItemName());
			}
		} else {
			System.out.println("Cart is empty,Please add items");
		}

	}

	public static void placeOrder(List<Items> cart) {

		if (cart != null && cart.size() != 0) {
			Session session = factory.openSession();
			try {
				session.beginTransaction();
				OrdersRecord temp = new OrdersRecord(userId);
				session.save(temp);
				for (Items item : cart) {
					OrderItem orderItem = new OrderItem(item.getItemId());
					temp.add(orderItem);
					session.save(orderItem);

				}

				session.getTransaction().commit();
				System.out.println("Successfully Placed Your Order, Thank you");
				cart.clear();

			} finally {
				session.close();
			}

		} else {
			System.out.println("Cart is empty ,unable to place order");
		}

	}

	public static void viewPreviousOrders() {
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Query q = session.createQuery("from OrdersRecord where userId=:id");
			q.setInteger("id", userId);
			List<OrdersRecord> previousOrders = q.getResultList();
			if (previousOrders != null && previousOrders.size() != 0) {
				System.out.println("Following are the Previous orders Id");
				for (OrdersRecord order : previousOrders) {
					System.out.println(order.getOrderId());
				}
				System.out.println();
				System.out.println("Please enter the order id to view items");
				int orderId = scan.nextInt();
				OrdersRecord record = session.get(OrdersRecord.class, new Integer(orderId));
				if (record != null) {
					for (OrderItem item : record.getItems()) {
						System.out.println("Items are " + itemMap.get(item.getItemId()).getItemName());
					}
				} else {
					System.out.println("Invalid order Id");
				}
			} else {
				System.out.println("No previous orders");
			}

		} finally {
			session.close();
		}
	}

}