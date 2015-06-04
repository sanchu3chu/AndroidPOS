package com.ricoh.pos.data;

import java.util.Date;

public class WomanShopFormatter {
	
	public static String formatDate(Date date) {
		String formatDate = "";
		String[] splitDate = null;

		splitDate = date.toString().split(" ");
		for (String element : splitDate) {
			if (element.indexOf(":") != -1) {
				element = element.replaceAll("[+:]", "");
			}
			formatDate += element;
		}

		return formatDate;
	}

}
