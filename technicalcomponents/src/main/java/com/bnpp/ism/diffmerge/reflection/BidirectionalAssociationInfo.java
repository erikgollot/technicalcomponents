package com.bnpp.ism.diffmerge.reflection;

public class BidirectionalAssociationInfo {
	boolean isAssociation;
	boolean isBidirectional;
	String opposite;

	public BidirectionalAssociationInfo(boolean isAssociation,
			boolean isBidirectional, String opposite) {
		super();
		this.isAssociation = isAssociation;
		this.isBidirectional = isBidirectional;
		this.opposite = opposite;
	}

	public boolean isAssociation() {
		return isAssociation;
	}

	public boolean isBidirectional() {
		return isBidirectional;
	}

	public String getOpposite() {
		return opposite;
	}

	public void setAssociation(boolean isAssociation) {
		this.isAssociation = isAssociation;
	}

	public void setBidirectional(boolean isBidirectional) {
		this.isBidirectional = isBidirectional;
	}

	public void setOpposite(String opposite) {
		this.opposite = opposite;
	}
}
