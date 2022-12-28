package com.dietdiary.client.date;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;

import javax.management.monitor.Monitor;
import javax.swing.JFrame;

import com.dietdiary.client.DiaryPage;
import com.dietdiary.client.DietDiaryMain;
import com.dietdiary.model.repository.FoodDAO;
import com.dietdiary.model.repository.HistoryDAO;

public class DateInfoFrame extends JFrame{
	DietDiaryMain main;
	Calendar selectedTime;
	
	ArrayList<SidePage> sidePages = new ArrayList<>();
	public static final int HISTORY_SIDE_PAGE = 0;
	public static final int SEARCH_SIDE_PAGE = 1;
	public static final int DETAIL_SIDE_PAGE = 2;
	
	FoodDAO foodDAO = new FoodDAO();
	HistoryDAO historyDAO = new HistoryDAO();
	
	public DateInfoFrame(DietDiaryMain main) {
		this.main = main;
		setBounds(main.getX() + main.getWidth()-10, main.getY(), 300, 500);
		setLayout(new FlowLayout());
		
		createSidePages();
	}
	public void createSidePages() {
		sidePages.add(new HistorySidePage(this));
		sidePages.add(new SearchSidePage(this));
		sidePages.add(new DetailSidePage(this));
		
		for(int i =0;i<sidePages.size();i++) {
			add(sidePages.get(i));
		}
	}
	
	public void getSelected(int date) {
		//페이지들 초기화
		clearAllPages();
		
		DiaryPage diaryPage = (DiaryPage)main.getPages().get(DietDiaryMain.DIARY_PAGE);
		selectedTime = Calendar.getInstance();
		int year = diaryPage.getCurrentTime().get(Calendar.YEAR);
		int month = diaryPage.getCurrentTime().get(Calendar.MONTH);
		selectedTime.set(year, month, date);

		HistorySidePage page = (HistorySidePage)sidePages.get(HISTORY_SIDE_PAGE);
		page.setTimeLabel(selectedTime);
		
		
		showHide(HISTORY_SIDE_PAGE);
		setVisible(true);
	}
	
	public void showHide(int n) {
		for(int i =0;i<sidePages.size();i++) {
			if(i==n) {
				sidePages.get(i).setVisible(true);
			}else {
				sidePages.get(i).setVisible(false);
			}
		}
	}
	public void clearAllPages() {
		//히스토리 페이지 초기화 해야함
		((SearchSidePage)sidePages.get(SEARCH_SIDE_PAGE)).clearPage();
		((DetailSidePage)sidePages.get(DETAIL_SIDE_PAGE)).clearPage();
	}
	
	public ArrayList<SidePage> getSidePages() {
		return sidePages;
	}
	public Calendar getSelectedTime() {
		return selectedTime;
	}
	public FoodDAO getFoodDAO() {
		return foodDAO;
	}
	public HistoryDAO getHistoryDAO() {
		return historyDAO;
	}
}
