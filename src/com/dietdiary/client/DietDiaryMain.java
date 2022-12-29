package com.dietdiary.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.dietdiary.client.date.DateInfoFrame;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.model.repository.DietDiaryMembersDAO;
import com.dietdiary.util.DBManager;

public class DietDiaryMain extends JFrame{
	
	private ArrayList<Page> pages = new ArrayList<>();
	DateInfoFrame dateInfoFrame;
	
	DBManager dbManager = DBManager.getInstance();
	DietDiaryMembersDAO membersDAO = new DietDiaryMembersDAO();
	
	
	
	private boolean isSignIn = false;
	private DietDiaryMembers loginedUserInfo;
	
	private String requestURL = "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1";
	private String serviceKey = "BjqQYzHtLjlq2NcHvzh%2BC1B%2FhWHIPERqruRu2rOMsG0bFYCyHaeFqy%2BKcHPHEPanneBg0nBjmqKSs82VGw7s%2BQ%3D%3D";

	public static final int SIGN_IN_PAGE = 0;
	public static final int SIGN_UP_PAGE = 1;
	public static final int DIARY_PAGE = 2;
	
	public DietDiaryMain() {
		
		setLayout(new FlowLayout());
		createPages();
		
		showHide(SIGN_IN_PAGE);
		
		setTitle("Diet Diary");
		setSize(700, 500);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		
		dateInfoFrame = new DateInfoFrame(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
	}
	public void createPages() {
		pages.add(new SignInPage(this));
		pages.add(new SignUpPage(this));
		pages.add(new DiaryPage(this));
		
		for(int i =0;i<pages.size();i++) {
			add(pages.get(i));
		}
	}
	
	public void showHide(int n) {
		for(int i =0;i<pages.size();i++) {
			if(n==i) {
				pages.get(i).setVisible(true);
			}else {
				pages.get(i).setVisible(false);				
			}
		}
	}
	
	public boolean isSignIn() {
		return isSignIn;
	}
	public void setSignIn(boolean isSignIn) {
		this.isSignIn = isSignIn;
	}
	public DietDiaryMembers getLoginedUserInfo() {
		return loginedUserInfo;
	}
	public void setLoginedUserInfo(DietDiaryMembers loginedUserInfo) {
		this.loginedUserInfo = loginedUserInfo;
	}
	public ArrayList<Page> getPages() {
		return pages;
	}
	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}
	public DateInfoFrame getDateInfoFrame() {
		return dateInfoFrame;
	}
	public void setDateInfoFrame(DateInfoFrame dateInfoFrame) {
		this.dateInfoFrame = dateInfoFrame;
	}
	public String getRequestURL() {
		return requestURL;
	}
	public String getServiceKey() {
		return serviceKey;
	}
	public DBManager getDbManager() {
		return dbManager;
	}
	
	
	public static void main(String[] args) {
		new DietDiaryMain();
	}
	
}
