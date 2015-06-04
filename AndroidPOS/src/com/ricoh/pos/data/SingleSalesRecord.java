package com.ricoh.pos.data;

import java.util.ArrayList;
import java.util.Date;

public class SingleSalesRecord {
	
	private Date salesDate;
	private double discountValue;
	private String userAttribute;
	private ArrayList<Order> orders;
	
	public SingleSalesRecord(Date date){
		orders = new ArrayList<Order>();
		this.salesDate = date;
		this.discountValue = 0;
	}
	
	public void setOrders(ArrayList<Order> orderList){
		this.orders = orderList;
	}
	
	public void addOrder(Order order){
		if (order == null) {
			throw new IllegalArgumentException("Order is null");
		}
		orders.add(order);
	}
	
	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	
	public ArrayList<Order> getAllOrders(){
		return orders;
	}
	
	public double getTotalSales(){
		double totalSales = 0;
		for (Order order : orders) {
			totalSales += order.getTotalAmount();
		}
		return totalSales;
	}
	
	public double getTotalCost(){
		double totalCost = 0;
		for (Order order : orders) {
			totalCost += order.getTotalCost();
		}
		return totalCost;
	}
	
	public double getTotalRevenue(){
		double totalRevenue = getTotalSales() - getTotalCost();
		if (totalRevenue <= 0) {
			throw new IllegalStateException("Total revenue have to be positive");
		}
		return totalRevenue;
	}
	
	public Date getSalesDate(){
		return salesDate;
	}
	
	public double getDiscountValue() {
		return discountValue;
	}
	
	public void setUserAttribute(String attribute){
		if (attribute == null || attribute.length() == 0) {
			throw new IllegalArgumentException("User attribute is illegal");
		}
		this.userAttribute = attribute;
	}
	
	public String getUserAttribute(){
		return userAttribute;
	}
}
