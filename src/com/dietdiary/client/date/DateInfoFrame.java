package com.dietdiary.client.date;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;

import javax.management.monitor.Monitor;
import javax.swing.JFrame;

import org.json.simple.JSONObject;

import com.dietdiary.client.DiaryPage;
import com.dietdiary.client.DietDiaryMain;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.domain.Food;
import com.dietdiary.domain.History;
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
		page.init();
		
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
		((HistorySidePage)sidePages.get(HISTORY_SIDE_PAGE)).clearPage();
		((SearchSidePage)sidePages.get(SEARCH_SIDE_PAGE)).clearPage();
		((DetailSidePage)sidePages.get(DETAIL_SIDE_PAGE)).clearPage();
		((DiaryPage)main.getPages().get(DietDiaryMain.DIARY_PAGE)).init();
	}
	public Food parseJSONObjectToFood(JSONObject obj) {

		DietDiaryMembers dietDiaryMembers = new DietDiaryMembers();
		dietDiaryMembers.setDiet_diary_members_idx(main.getLoginedUserInfo().getDiet_diary_members_idx());
		
		History history = new History(); //외래키를 위해 생성
		history.setDietDiaryMembers(dietDiaryMembers);
		history.setYear(selectedTime.get(Calendar.YEAR));
		history.setMonth(selectedTime.get(Calendar.MONTH)+1);
		history.setDay(selectedTime.get(Calendar.DATE));
		
		Food food = new Food();
		
		String foodName = (String)obj.get("DESC_KOR");
		String brand = (String)obj.get("ANIMAL_PLANT");
		int cal, carbs, proteins, fats;
		String servesize, regyear;
		cal = (int)Double.parseDouble((String)obj.get("NUTR_CONT1"));
		carbs = (int)Double.parseDouble((String)obj.get("NUTR_CONT2"));
		proteins = (int)Double.parseDouble((String)obj.get("NUTR_CONT3"));
		fats = (int)Double.parseDouble((String)obj.get("NUTR_CONT4"));
		servesize = (String)obj.get("SERVING_WT");
		regyear = (String)obj.get("BGN_YEAR");
//		//이름, 브랜드, 칼로리, 탄수, 단백질, 지방, 서빙사이즈, 연도
		food.setHistory(history);
		food.setName(foodName);
		food.setBrand(brand);
		food.setCalories(cal);
		food.setCarbs(carbs);
		food.setProteins(proteins);
		food.setFats(fats);
		food.setServeSize(servesize);
		food.setRegyear(regyear);
		food.setQuantity(1);
		
		return food;
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
