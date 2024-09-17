package com.project.vending.model;

import lombok.Data;

@Data
public class Cash {
	  private double insertedAmount;

	public Cash() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Cash [insertedAmount=" + insertedAmount + "]";
	}

	public Cash(double insertedAmount) {
		super();
		this.insertedAmount = insertedAmount;
	}

	public double getInsertedAmount() {
		return insertedAmount;
	}

	public void setInsertedAmount(double insertedAmount) {
		this.insertedAmount = insertedAmount;
	}
}
