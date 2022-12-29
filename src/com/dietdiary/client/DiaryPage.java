package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;

import com.dietdiary.components.DateCell;
import com.dietdiary.components.DayCell;
import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.domain.History;
import com.dietdiary.model.repository.HistoryDAO;

public class DiaryPage extends Page {

	double pWidth = 29 / 30.0; // 패널들의 width 사이즈
	
	public static final int SIGN_OUT = 0;

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
	
	//로그가 있는지 확인하기 위해 정보를 받아올 ArrayList
	List<History> historyList = new ArrayList<>();

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
		lbName = new JLabel("  " + "NAME" + " 님");
		navButtonForm = new MyButtonForm();
		navButtonForm.addMyButton("Sign Out");
		
		navBtns = navButtonForm.getButtons();

		// nav디자인
		lbName.setFont(new Font(lbName.getFont().getFontName(), Font.BOLD, 25));

		// nav조립
		navigationBar.add(lbName, BorderLayout.WEST);
		navigationBar.add(navButtonForm, BorderLayout.EAST);
		
		navBtns.get(SIGN_OUT).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				signOut();
			}
		});
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
				moveMonth(-1);
				setMonth();
				init();
			}
		});
		btNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveMonth(1);
				setMonth();
				init();
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
		days[0].getLabel().setForeground(Color.RED);
		days[6].getLabel().setForeground(Color.BLUE);
		
		dates = new DateCell[6][7];
		for(int i =0;i<dates.length;i++) {
			for(int j = 0;j<dates[i].length;j++) {
				dates[i][j] = new DateCell(cellWidth, cellHeight, main, currentTime);
				if(j==0) {
					dates[i][j].setSun(true);
				}else if(j==6) {
					dates[i][j].setSat(true);
				}
				diary.add(dates[i][j]);
			}
		}
		
	}
	public void moveMonth(int n) {
		currentTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH)+n);
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
						dates[i][j].setDate(n);
						dates[i][j].setFlag(true);
					}else {
						dates[i][j].setFlag(false);
						dates[i][j].setDate(0);
					}
				}else {
					dates[i][j].setFlag(false);
					dates[i][j].setDate(0);
				}
			}
		}
	}
	public void getHistoryList() {
		HistoryDAO historyDAO = main.dateInfoFrame.getHistoryDAO();
		
		DietDiaryMembers dietDiaryMembers = new DietDiaryMembers();
		DietDiaryMembers loginInfo = main.getLoginedUserInfo();
		dietDiaryMembers.setDiet_diary_members_idx(loginInfo.getDiet_diary_members_idx());
		
		History FKAndMonth = new History();
		FKAndMonth.setDietDiaryMembers(dietDiaryMembers);
		FKAndMonth.setYear(currentTime.get(Calendar.YEAR));
		FKAndMonth.setMonth(currentTime.get(Calendar.MONTH) + 1);
		historyList = historyDAO.selectAllByFKAndMonth(FKAndMonth);
	}
	public void setHasLogOnItems() {
		for(int i =0;i<dates.length;i++) {
			for(int j =0;j<dates[i].length;j++) {
				DateCell date = dates[i][j];
				History historyOfDate = null;
				for(int k =0;k<historyList.size();k++) {
					if(date.getDate()==historyList.get(k).getDay()) {
						historyOfDate = historyList.get(k);
					}
				}
				date.setHistory(historyOfDate);
				date.setToday();//오늘인지 판별하고 isToday설정하는 메서드
			}
		}
	}
	public void setDatesTime() {
		for(int i = 0;i<dates.length;i++) {
			for(int j = 0;j<dates[i].length;j++) {
				dates[i][j].setCurrentTime(currentTime);
			}
		}
	}
	public void setNameLabel() {
		lbName.setText("  " + main.getLoginedUserInfo().getName() + " 님");
	}
	
	
	public void init() {
		getDates();
		getHistoryList();
		setHasLogOnItems();
		diary.updateUI();
	}
	public void getSignIn() {
		currentTime = Calendar.getInstance();
		setDatesTime();
		setNameLabel();
		init();
	}
	public void signOut() {
		main.getDateInfoFrame().setVisible(false);
		SignInPage page = (SignInPage)main.getPages().get(DietDiaryMain.SIGN_IN_PAGE);
		page.fields.get(SignInPage.SIGN_IN).grabFocus();
		lbName.setText("");
		historyList.removeAll(historyList);
		main.setLoginedUserInfo(null);
		main.showHide(DietDiaryMain.SIGN_IN_PAGE);
	}
	
	public Calendar getCurrentTime() {
		return currentTime;
	}
}
