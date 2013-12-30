package com.ricoh.pos.data;

public class Product {
	
	private String category;
	private String name;
	private int originalCost;
	private int price;
	private int stock;
	private String imagePath;
	
	public Product(String category,String name){
		this.category = category;
		this.name = name;
	}
	
	///////////////////////////
	// Setter
	///////////////////////////
	public void setOriginalCost(int cost){
		
		if (cost <= 0) {
			throw new IllegalArgumentException("Original cost should be over zero");
		}
		
		this.originalCost = cost;
	}
	
	public void setPrice(int price){
		
		if (price <= 0) {
			throw new IllegalArgumentException("Price should be over zero");
		}
		
		this.price = price;
	}
	
	public void setStock(int stock){
		
		if (stock < 0) {
			throw new IllegalArgumentException("Stock should be positive");
		}
		
		this.stock = stock;
	}
	
	public void setProductImagePath(String imagePath){
		
		if (imagePath == null || imagePath.length() == 0) {
			throw new IllegalArgumentException("Passing imagePath is not valid");
		}
		
		this.imagePath = imagePath;
	}
	

	///////////////////////////
	// Getter
	///////////////////////////

	public String getCategory(){
		return this.category;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getOriginalCost(){
		return this.originalCost;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public int getStock(){
		return this.stock;
	}
	
	public String getProductImagePath(){
		return imagePath;
	}
	
}
