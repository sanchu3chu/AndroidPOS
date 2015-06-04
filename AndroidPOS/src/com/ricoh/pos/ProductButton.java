package com.ricoh.pos;

import com.ricoh.pos.data.Product;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ProductButton extends Button {

	Product product;
	ProductEditText editText;
	
	public ProductButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setProduct(Product product) {
		
		if (product == null) {
			throw new IllegalArgumentException("Passing product is null");
		}
		
		this.product = product;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProductEditText(ProductEditText editText){
		if (editText == null) {
			throw new IllegalArgumentException("Passing editText is null");
		}
		
		this.editText = editText;
	}

	public ProductEditText getProductEditText(){
		return editText;
	}
}
