package com.dietdiary.components;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class DateCell extends MyCell{

	private boolean flag;
	private int date;
	
	public DateCell(double width, double height) {
		super(width, height);
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	

}
