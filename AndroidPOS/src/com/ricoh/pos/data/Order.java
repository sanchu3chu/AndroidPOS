package com.ricoh.pos.data;

public class Order {
	
	private Product product;
	
	private int num;
	
	public Order() {
		// Do nothing
	}
	
	public Order(Product product,int numberOfOrder){
		
		if(product == null || numberOfOrder < 0) {
			throw new IllegalArgumentException();
		}
		
		setOrder(product,numberOfOrder);
	}
	
	public void setOrder(Product product,int numberOfOrder){
		
		if(product == null || numberOfOrder < 0) {
			throw new IllegalArgumentException();
		}
		
		this.product = product;
		this.num = numberOfOrder;
	}
	
	public void setNumberOfOrder(int num){
		this.num = num;
	}
	
	public int getNumberOfOrder(){
		return num;
	}

	public String getProductName(){
		return product.getName();
	}
	
	public int getTotalAmount(){
		return product.getPrice() * num;
	}
	
	protected boolean equals(String productName){
		return productName.equals(product.getName());
	}
	
}