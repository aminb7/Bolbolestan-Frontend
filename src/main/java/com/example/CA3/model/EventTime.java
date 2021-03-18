package com.example.CA3.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface EventTime {
	public boolean overlaps(EventTime other);
	public ObjectNode getJsonInfo();

	public String getHtmlTable();
}
