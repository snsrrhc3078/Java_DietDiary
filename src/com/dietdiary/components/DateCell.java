package com.dietdiary.components;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class DateCell extends MyCell{

	private boolean flag;
	private int date;
	JLabel lbDate, lbCalbs, lbNutrition;
	
	public DateCell(double width, double height) {
		super(width, height);
		lbDate = new JLabel("123");
		lbDate.setFont(new Font(lbDate.getFont().getFontName(), Font.BOLD, 15));
		lbDate.setHorizontalAlignment(JLabel.CENTER);
		
		lbCalbs = new JLabel();
		lbNutrition = new JLabel();
		
		setLayout(new GridLayout(3,1));
		
		add(lbDate);
		add(lbCalbs);
		add(lbNutrition);
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
	
	public void showInfo() {
		if(flag) {
			lbDate.setText(Integer.toString(date));
		}else {
			lbDate.setText("");
		}
	}

}
