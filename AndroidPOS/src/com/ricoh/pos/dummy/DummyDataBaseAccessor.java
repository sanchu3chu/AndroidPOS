package com.ricoh.pos.dummy;

import java.util.ArrayList;

import com.ricoh.pos.data.SingleSalesRecord;

public class DummyDataBaseAccessor {
	
	private ArrayList<SingleSalesRecord> salesRecords;
	
	public DummyDataBaseAccessor(){
		this.salesRecords = new ArrayList<SingleSalesRecord>();
	}
	
	public void saveSalesRecord(SingleSalesRecord record){
		salesRecords.add(record);
	}
	
	public ArrayList<SingleSalesRecord> getSalesRecords(){
		return salesRecords;
	}

}
