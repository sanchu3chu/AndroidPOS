package com.ricoh.pos.model;

import java.util.EventListener;

import com.ricoh.pos.data.OrderUpdateInfo;

public interface UpdateOrderListener extends EventListener {

	public void notifyUpdateOrder(OrderUpdateInfo orderInfo);
	
}
