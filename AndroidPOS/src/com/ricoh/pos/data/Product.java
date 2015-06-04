package com.ricoh.pos.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Product {

	private String code;
	private String category;
	private String name;
	private double originalCost;
	private double price;
	private int stock;
	private String imagePath;
	private static String imageStorageFolder = "/Ricoh";

	public Product(String code, String category, String name) {
		if (code == null || code.length() == 0) {
			throw new IllegalArgumentException("Passing code is not valid");
		}
		if (category == null || category.length() == 0) {
			throw new IllegalArgumentException("Passing category is not valid");
		}
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Passing name is not valid");
		}
		this.code = code;
		this.category = category;
		this.name = name;
		this.originalCost = 0.0;
		this.price = 0.0;
		this.stock = 0;
		this.imagePath = "";
	}

	// /////////////////////////
	// Setter
	// /////////////////////////
	public void setOriginalCost(double cost) {

		if (cost < 0) {
			throw new IllegalArgumentException("Original cost should be over zero");
		}

		this.originalCost = cost;
	}

	public void setPrice(double price) {

		if (price < 0) {
			throw new IllegalArgumentException("Price should be over zero");
		}

		this.price = price;
	}

	public void setStock(int stock) {

		if (stock < 0) {
			throw new IllegalArgumentException("Stock should be positive");
		}

		this.stock = stock;
	}

	public void setProductImagePath(String imagePath) {

		if (imagePath == null || imagePath.length() == 0) {
			throw new IllegalArgumentException("Passing imagePath is not valid");
		}
		
		String imageStoragePath = getImageStoragePath();
		this.imagePath = imageStoragePath + "/" + imagePath + ".jpg";
	}

	// /////////////////////////
	// Getter
	// /////////////////////////

	public String getCode() {
		return this.code;
	}
	
	public String getCategory() {
		return this.category;
	}

	public String getName() {
		return this.name;
	}

	public double getOriginalCost() {
		return this.originalCost;
	}

	public double getPrice() {
		return this.price;
	}

	public int getStock() {
		return this.stock;
	}

	public String getProductImagePath() {
		return imagePath;
	}

	@Override
	public boolean equals(Object object){
		Product targetProduct = (Product) object;
		return this.code.equals(targetProduct.getCode());
	}
	
	private String getImageStoragePath() {
		File exterlStorage = Environment.getExternalStorageDirectory();
		Log.d("debug", "Environment External:" + exterlStorage.getAbsolutePath());
		return exterlStorage.getAbsolutePath() + imageStorageFolder;
	}

	/**
	 * Productの画像を返す
	 * 引数のサイズに合わせて画像を縮小してデコードする
	 * 縮小しないとメモリが溢れる可能性があるため
	 *
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 * @throws FileNotFoundException
	 */
	public Bitmap decodeProductImage(int imageWidth, int imageHeight) throws FileNotFoundException {
		File imageFile = new File(imagePath);

		InputStream inputStream = new FileInputStream(imageFile);
		//　画像サイズ取得
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(inputStream, null, options);
		int width = options.outWidth;
		int height = options.outHeight;

		// 縮小してデコード
		int scaleW = width / imageWidth; //imageViewの幅。getWidthだとなぜか0になるので決めうち
		int scaleH = height / imageHeight;
		options.inSampleSize = (int) Math.max(scaleW, scaleH);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imagePath, options);
	}
}
