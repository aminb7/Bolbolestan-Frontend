package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface EventTime {
	public boolean overlaps(EventTime other);
	public ObjectNode getJsonInfo();
}
