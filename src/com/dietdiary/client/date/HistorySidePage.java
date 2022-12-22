package com.dietdiary.client.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JLabel;

public class HistorySidePage extends SidePage{

	int year;
	int month;
	int date;
	
	JLabel lbTime;
	
	public HistorySidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		
		lbTime = new JLabel();
		lbTime.setFont(new Font(lbTime.getFont().getName(), Font.BOLD, 20));
		add(lbTime);
	}
	public void setTimeLabel(Calendar cal) {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
		
		lbTime.setText(year+"년 "+(month+1)+"월 "+ date+"일 식단");
		updateUI();
	}
}
