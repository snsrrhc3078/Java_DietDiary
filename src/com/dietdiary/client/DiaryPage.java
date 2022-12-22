package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;

import com.dietdiary.components.DateCell;
import com.dietdiary.components.DayCell;
import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyCell;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;

public class DiaryPage extends Page {

	double pWidth = 29 / 30.0; // 패널들의 width 사이즈
	
	public static final int LOG_OUT = 0;

	// 네비게이션 바 선언
	MyFormWrapper navigationBar;
	MyButtonForm navButtonForm;
	JLabel lbName;
	
	List<MyButton> navBtns;

	// 컨트롤러 선언
	MyFormWrapper diaryController;
	MyLabel month; //년도와 달을 표시할 라벨
	MyButton btPrev, btNext;

	// 다이어리 선언
	MyFormWrapper diary;
	String[] daysName = {
			"Sun",
			"Mon",
			"Tue",
			"Wed",
			"Thu",
			"Fri",
			"Sat"
	};
	double cellWidth;
	double cellHeight;
	DayCell[] days;
	DateCell[][] dates;
	Calendar currentTime;

	public DiaryPage(DietDiaryMain main) {
		super(main);
		currentTime = Calendar.getInstance();
		
		createNav();
		createController();
		createDiary();

		add(navigationBar);
		add(diaryController);
		add(diary);
	}

	public void createNav() {
		// nav할당
		navigationBar = new MyFormWrapper(this, pWidth, 1 / 12.0, 25);
		lbName = new JLabel("  Rabin 님");
		navButtonForm = new MyButtonForm();
		navButtonForm.addMyButton("Log out");
		
		navBtns = navButtonForm.getButtons();

		// nav디자인
		lbName.setFont(new Font(lbName.getFont().getFontName(), Font.BOLD, 25));

		// nav조립
		navigationBar.add(lbName, BorderLayout.WEST);
		navigationBar.add(navButtonForm, BorderLayout.EAST);
	}
	public void createController() {
		diaryController = new MyFormWrapper(this, pWidth, 1/12.0, 25);
		diaryController.setLayout(new FlowLayout());
		
		btPrev = new MyButton("◀");
		btNext = new MyButton("▶");
		month = new MyLabel("YYYY년 MM월", 20); //임시 문자열
		setMonth();
		
		diaryController.add(btPrev);
		diaryController.add(month);
		diaryController.add(btNext);
		
		btPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH)-1);
				setMonth();
				getDates();
			}
		});
		btNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH)+1);
				setMonth();
				getDates();
			}
		});
	}
	public void createDiary() {
		diary = new MyFormWrapper(this, pWidth, 9.5/12.0, 25);
		diary.setLayout(new FlowLayout());
		
		cellWidth = (diary.getPreferredSize().width-50)/7;
		cellHeight = (diary.getPreferredSize().height-35)/7;
				
		days = new DayCell[7];
		for(int i =0;i<days.length;i++) {
			days[i] = new DayCell(cellWidth, cellHeight, daysName[i]);
			diary.add(days[i]);
		}
		dates = new DateCell[6][7];
		for(int i =0;i<dates.length;i++) {
			for(int j = 0;j<dates[i].length;j++) {
				dates[i][j] = new DateCell(cellWidth, cellHeight);
				diary.add(dates[i][j]);
			}
		}
		
		//dates 제작
		getDayOfStart(currentTime);
		getDateOfLast(currentTime);
		getDates();
	}
	
	public void setMonth() {
		month.setText(currentTime.get(Calendar.YEAR)+"년 " + (currentTime.get(Calendar.MONTH)+1) + "월");
	}
	public int getDayOfStart(Calendar cal) {	
		Calendar temp = Calendar.getInstance();
		temp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		return temp.get(Calendar.DAY_OF_WEEK);
	}
	public int getDateOfLast(Calendar cal) {
		Calendar temp = Calendar.getInstance();
		temp.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 0);
		
		return temp.get(Calendar.DATE);
	}
	public void getDates() {
		int index = 0;
		int n = 0;
		for(int i =0;i<dates.length;i++) {
			for(int j = 0;j<dates[i].length;j++) {
				index++;
				if(getDayOfStart(currentTime)<=index) {
					n++;
					if(getDateOfLast(currentTime)>=n) {
//						System.out.println(n);
						dates[i][j].setDate(n);
						dates[i][j].setFlag(true);
					}else {
						dates[i][j].setFlag(false);
					}
				}else {
					dates[i][j].setFlag(false);
				}
			}
		}
		for(int i =0;i<dates.length;i++) {
			for(int j = 0;j<dates[i].length;j++) {
				dates[i][j].showInfo();
			}
		}
	}
}
