package com.ricoh.pos.model;

import java.util.Date;

public class SalesCalenderManager {

	// Actually I want to pass this selected date from Activity to Fragment.
	// But I can't find the way to do that, because setArguments() causes IllegalStateException.
	// That's because I implement SalesCalenderMaager.
	private Date selectedDate;
	
	private Date selectedSalesDate;
	
	private static SalesCalenderManager instance;
	
	private SalesCalenderManager(){}
	
	public static SalesCalenderManager getInstance(){
		if (instance == null) {
			instance = new SalesCalenderManager();
		}
		return instance;
	}
	
	public void setSelectedDate(Date date){
		if (date == null) {
			throw new IllegalArgumentException("The passing date is null");
		}
		
		this.selectedDate = date;
	}
	
	public Date getSelectedDate(){
		
		if (selectedDate == null) {
			throw new IllegalStateException("Date has not been selected yet");
		}
		
		return selectedDate;
	}
	
	public void setSelectedSalesDate(Date date){
		if (date == null) {
			throw new IllegalArgumentException("The passing date is null");
		}
		
		this.selectedSalesDate = date;
	}
	
	public Date getSelectedSalesDate() {
		return selectedSalesDate;
	}
	
}
