package com.example.CA3.WaitingListTimer;

import com.example.CA3.model.BolbolestanApplication;

public class WaitingListUpdateJob implements Runnable {
	@Override
	public void run() {
		BolbolestanApplication.getInstance().updateWaitingLists();
	}
}
