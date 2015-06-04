package com.ricoh.pos.model;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.ricoh.pos.data.WomanShopDataDef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WomanShopIOManager implements IOManager {

	private SQLiteDatabase database;
	private static String DATABASE_NAME = "products_dummy";
	private static String csvStorageFolder = "/Ricoh";
	
	private final String[] WomanShopDataStructure = new String[]{
			WomanShopDataDef.PRODUCT_CODE.name(),
			WomanShopDataDef.ITEM_CATEGORY.name(),
			WomanShopDataDef.PRODUCT_CATEGORY.name(),
			WomanShopDataDef.SALE_PRICE.name(),
			WomanShopDataDef.COST_TO_ENTREPRENEUR.name() };

	public WomanShopIOManager() {
		// Nothing to do
	}

	@Override
	public BufferedReader importCSVfromAssets(AssetManager assetManager) {
		BufferedReader bufferReader = null;
		try {
			// TODO: Should import & export data
			bufferReader = new BufferedReader(new InputStreamReader(assetManager.open(DATABASE_NAME
					+ ".csv")));
		} catch (IOException e) {
			Log.d("debug", "" + e + "");
		}
		return bufferReader;
	}

	@Override
	public void insertRecords(BufferedReader bufferReader) throws IOException {

		ContentValues contentValue = new ContentValues();
		try {
			readFieldName(bufferReader);

			String record;
			while ((record = bufferReader.readLine()) != null) {
				String[] fieldValues = record.split(",");
				Log.d("debug", "Product Code" + fieldValues[0]);

				int i = 0;
				for (WomanShopDataDef field : WomanShopDataDef.values()) {
					contentValue.put(field.name(), fieldValues[i++]);
				}

				database.insertWithOnConflict(DATABASE_NAME, null, contentValue,
						SQLiteDatabase.CONFLICT_REPLACE);
			}
		} catch (IOException e) {
			Log.d("debug", "" + e + "", e);
            throw e;
		}
	}

	@Override
	public String[] searchAlldata() {
		Cursor cursor = null;

		try {
			cursor = database.query(
					DATABASE_NAME,
					WomanShopDataStructure, 
							null, null, null, null, null);
			String[] results = new String[cursor.getCount()];
			Log.d("debug", "count:" + cursor.getCount());
			for (int i = 0; i < cursor.getCount(); i++) {
				results[i] = readCursor(cursor);
			}
			return results;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	// TODO: Temporary function
	@Override
	public String searchByCode(String code) {
		Cursor cursor = null;
		try {
			cursor = database.query(
					DATABASE_NAME, 
					WomanShopDataStructure, 
							WomanShopDataDef.PRODUCT_CODE.name() + " = ?", 
							new String[] { code }, null, null, null);
			return readCursor(cursor);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public BufferedReader importCSVfromSD() throws FileNotFoundException {
		BufferedReader bufferReader = null;
		
		try {
			String csvStoragePath = getCSVStoragePath();
			File productDataCSV = new File(csvStoragePath + "/Product.csv");
			bufferReader = new BufferedReader(new FileReader(productDataCSV));
		} catch (FileNotFoundException e) {
			Log.d("debug", "import error", e);
            throw e;
		}
		return bufferReader;
	}
	
	// TODO: Add this function to interface
	public void setDatabase(SQLiteDatabase database) {
		if (this.database == null) {
			Log.d("debug", "Database set:" + DATABASE_NAME);
			this.database = database;
		}
	}
	
	// TODO: Add this function to interface
	public void closeDatabase() {
		if (database != null) {
			Log.d("debug", "Database closed:" + DATABASE_NAME);
			database.close();
		}
	}

	private void readFieldName(BufferedReader bufferReader) throws IOException {
		String record = bufferReader.readLine();

		// TODO: Show field name for debug
		String[] fieldNames = record.split(",");
		for (String fieldName : fieldNames) {
			Log.d("debug", fieldName);
		}
	}

	private String readCursor(Cursor cursor) {
		String result = "";

		int indexProductCode = cursor.getColumnIndex(WomanShopDataDef.PRODUCT_CODE.name());
		int indexProductCategory = cursor.getColumnIndex(WomanShopDataDef.PRODUCT_CATEGORY.name());
		int indexItemCategory = cursor.getColumnIndex(WomanShopDataDef.ITEM_CATEGORY.name());
		int indexCostToEntrepreneur = cursor.getColumnIndex(WomanShopDataDef.COST_TO_ENTREPRENEUR.name());
		int indexSalePrice = cursor.getColumnIndex(WomanShopDataDef.SALE_PRICE.name());

		if (cursor.moveToNext()) {
			String productCode = cursor.getString(indexProductCode);
			String productCategory = cursor.getString(indexProductCategory);
			String itemCategory = cursor.getString(indexItemCategory);
			double salePrice = cursor.getDouble(indexSalePrice);
			double costToEntrepreneur = cursor.getDouble(indexCostToEntrepreneur);

			result += productCode + ":" + itemCategory + ":" + productCategory + ":"
					+ costToEntrepreneur + ":" + salePrice + ":" + "\n";
		}
		return result;
	}
	
	public String getCSVStoragePath() {
		File exterlStorage = Environment.getExternalStorageDirectory();
		Log.d("debug", "Environment External:" + exterlStorage.getAbsolutePath());
		return exterlStorage.getAbsolutePath() + csvStorageFolder;
	}
}
