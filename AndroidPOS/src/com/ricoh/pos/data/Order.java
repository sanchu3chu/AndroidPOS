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
		if (num < 0) {
			 throw new IllegalArgumentException("Number of order should be positive");
		}
		
		this.num = num;
	}
	
	public void plusNumberOfOrder(){
		num++;
	}
	
	public void minusNumberOfOrder(){
		if (num == 0) {
			// Do Nothing		
		} else if (num > 0) {
			num--;
		} else {
			throw new IllegalStateException("number of order is illegal");
		}
	}
	
	public int getNumberOfOrder(){
		return num;
	}
	
	public Product getProduct(){
		return product;
	}
	
	public String getProductCode(){
		return product.getCode();
	}

	public String getProductCategory(){
		 return product.getCategory();
	}
	
	public String getProductName(){
		return product.getName();
	}
	
	public double getProductPrice(){
		return product.getPrice();
	}
	
	public double getTotalAmount(){
		return product.getPrice() * num;
	}
	
	public double getTotalCost(){
		return product.getOriginalCost() * num;
	}
	
	protected boolean equals(String productName){
		return productName.equals(product.getName());
	}
	
}
