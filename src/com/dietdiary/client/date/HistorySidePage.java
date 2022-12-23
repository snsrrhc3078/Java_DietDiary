package com.dietdiary.client.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.components.datecomponents.HistoryItem;
import com.dietdiary.components.datecomponents.Item;

public class HistorySidePage extends SidePage{

	int year;
	int month;
	int date;
	
	MyFormWrapper historyInfo;
	MyLabel lbTime, lbTotalCal, lbTotalNutritions;
	
	MyFormWrapper foodHistory;
	MyButton btReg;
	
	
	public HistorySidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		
		lbTime = new MyLabel("YYYY년 MM월 DD일 식단", 15);
		lbTotalCal = new MyLabel("섭취한 칼로리: " + 0 + "kcals", 15);
		lbTotalNutritions = new MyLabel("탄수 :"+ 0 + "g 단백질 :" +0+"g 지방 :"+0 + "g" , 15);
		
		historyInfo = new MyFormWrapper(this, 9/10.0, 2/10.0);
		foodHistory = new MyFormWrapper(this, 9/10.0, 7.5/10.0);
		
		btReg = new MyButton("Add Food");
		
		//테스트 코드
		Item item = new HistoryItem(foodHistory);
		
		
		historyInfo.setLayout(new FlowLayout());
		foodHistory.setLayout(new FlowLayout());
		
		historyInfo.add(lbTime);
		historyInfo.add(lbTotalCal);
		historyInfo.add(lbTotalNutritions);
		
		foodHistory.add(btReg);
		foodHistory.add(item);
		
		add(historyInfo);
		add(foodHistory);
		
		btReg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.showHide(DateInfoFrame.SEARCH_SIDE_PAGE);
			}
		});
	}
	public void setTimeLabel(Calendar cal) {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
		
		lbTime.setText(year+"년 "+(month+1)+"월 "+ date+"일 식단");
		updateUI();
	}
}
