package com.ricoh.pos.data;

public class Order {

	private Product product;
	private int num;
	private double discountValue;

	public Order(Product product, int numberOfOrder) {
		if (product == null || numberOfOrder < 0) {
			throw new IllegalArgumentException();
		}
		setOrder(product, numberOfOrder);
	}

	public void setOrder(Product product, int numberOfOrder) {
		if (product == null || numberOfOrder < 0) {
			throw new IllegalArgumentException();
		}

		this.product = product;
		this.num = numberOfOrder;
	}

	public void setNumberOfOrder(int num) {
		if (num < 0) {
			throw new IllegalArgumentException("Number of order should be positive");
		}

		this.num = num;
	}

	public void plusNumberOfOrder() {
		num++;
	}

	public void minusNumberOfOrder() {
		if (num == 0) {

		} else if (num > 0) {
			num--;
		} else {
			throw new IllegalStateException("number of order is illegal");
		}
	}

	public int getNumberOfOrder() {
		return num;
	}

	public Product getProduct() {
		return product;
	}

	public String getProductCode() {
		return product.getCode();
	}

	public String getProductCategory() {
		return product.getCategory();
	}

	public String getProductName() {
		return product.getName();
	}

	public double getProductPrice() {
		return product.getPrice();
	}

	public double getTotalAmount() {
		return product.getPrice() * num;
	}

	public double getTotalCost() {
		return product.getOriginalCost() * num;
	}

	public double getRevenue(boolean enableDisCount) {
		double result = (product.getPrice() - product.getOriginalCost()) * num;
		if (enableDisCount) {
			return (result - discountValue);
		}
		return result;
	}

	public void setDiscount(double discount) {
		discountValue = discount;
	}

	public double getDiscount() {
		return discountValue;
	}

	protected boolean equals(String productName) {
		return productName.equals(product.getName());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Order{");
		sb.append("product=").append(product == null ? "null" : product);
		sb.append(", num=").append(num);
		sb.append(", discountValue=").append(discountValue);
		sb.append("}");
		return sb.toString();
	}
}
