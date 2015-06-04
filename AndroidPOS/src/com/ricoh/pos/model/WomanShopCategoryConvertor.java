package com.ricoh.pos.model;

import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;

import com.ricoh.pos.R;

public class WomanShopCategoryConvertor {
	
	private static Map<Integer,String> categoryMap;

	public static String convertToCategoryName(int categoryID, Resources resource){
		if (categoryMap == null) {
			categoryMap = createCategoryMap(resource);
		}
		
		return categoryMap.get(categoryID);
	}
	
	private static Map<Integer,String> createCategoryMap(final Resources resource){
		return new HashMap<Integer,String>(){{
			put(1,resource.getString(R.string.category_name_1));
			put(2,resource.getString(R.string.category_name_2));
			put(3,resource.getString(R.string.category_name_3));
			put(4,resource.getString(R.string.category_name_4));
			put(5,resource.getString(R.string.category_name_5));
			put(6,resource.getString(R.string.category_name_6));
			put(7,resource.getString(R.string.category_name_7));
			put(8,resource.getString(R.string.category_name_8));
			put(9,resource.getString(R.string.category_name_9));
			put(10,resource.getString(R.string.category_name_10));
			put(11,resource.getString(R.string.category_name_11));
			put(12,resource.getString(R.string.category_name_12));
			put(13,resource.getString(R.string.category_name_13));
			put(14,resource.getString(R.string.category_name_14));
			put(15,resource.getString(R.string.category_name_15));
			put(16,resource.getString(R.string.category_name_16));
			put(17,resource.getString(R.string.category_name_17));
		}};
	}


}
