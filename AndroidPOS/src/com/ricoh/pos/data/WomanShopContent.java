package com.ricoh.pos.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing WomanShop content for user interfaces.
 * 
 * @author Takuya Mizuhara
 * 
 */
public class WomanShopContent {
	
	private String categoryAll;
	
	/**
	 * An array of woman shop items.
	 */
	public List<WomanShopItem> ITEMS = new ArrayList<WomanShopItem>();

	/**
	 * A map of woman shop items, by ID.
	 */
	public Map<String, WomanShopItem> ITEM_MAP = new HashMap<String, WomanShopItem>();

	/**
	 * Constructor.
	 */
	public WomanShopContent() {
		// Nothing to do
	}
	
	public void setCategoryAllName(String categoryAllName) {
		categoryAll = categoryAllName;
	}

	/**
	 * Register all category name with item list.
	 * 
	 * @param categoryNames
	 *            all category name
	 */
	public void RegisterCategory(String[] categoryNames) {
		if (categoryNames == null) {
			throw new IllegalArgumentException("Passing categoryNames is null.");
		}
		addItem(new WomanShopItem(categoryAll, categoryAll));
		for (String categoryName : categoryNames) {
			if (categoryName == null) {
				throw new IllegalArgumentException("Passing categoryName is null.");
			}
			addItem(new WomanShopItem(categoryName, categoryName));
		}

	}

	private void addItem(WomanShopItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A woman shop item representing a piece of content.
	 */
	public static class WomanShopItem {
		public String id;
		public String content;

		public WomanShopItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
