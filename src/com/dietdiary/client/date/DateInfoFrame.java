package com.dietdiary.client.date;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;

import javax.management.monitor.Monitor;
import javax.swing.JFrame;

import com.dietdiary.client.DiaryPage;
import com.dietdiary.client.DietDiaryMain;

public class DateInfoFrame extends JFrame{
	DietDiaryMain main;
	Calendar selectedTime;
	
	ArrayList<SidePage> sidePages = new ArrayList<>();
	public static final int HISTORY_SIDE_PAGE = 0;
	public static final int SEARCH_SIDE_PAGE = 1;
	
	public DateInfoFrame(DietDiaryMain main) {
		this.main = main;
		setBounds(main.getX() + main.getWidth()-10, main.getY(), 300, 500);
		setLayout(new FlowLayout());
		
		createSidePages();
	}
	public void createSidePages() {
		sidePages.add(new HistorySidePage(this));
		sidePages.add(new SearchSidePage(this));
		
		for(int i =0;i<sidePages.size();i++) {
			add(sidePages.get(i));
		}
	}
	
	public void getSelected(int date) {
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
}
