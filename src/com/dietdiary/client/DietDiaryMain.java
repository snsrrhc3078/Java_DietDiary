package com.dietdiary.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.model.repository.DietDiaryMembersDAO;
import com.dietdiary.util.DBManager;

public class DietDiaryMain extends JFrame{
	
	ArrayList<Page> pages = new ArrayList<>();
	
	
	DietDiaryMembersDAO membersDAO = new DietDiaryMembersDAO();
	
	
	private boolean isLogin = false;
	private DietDiaryMembers loginedUserInfo;
	
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public DietDiaryMembers getLoginedUserInfo() {
		return loginedUserInfo;
	}
	public void setLoginedUserInfo(DietDiaryMembers loginedUserInfo) {
		this.loginedUserInfo = loginedUserInfo;
	}
	

	public static final int LOGIN_PAGE = 0;
	public static final int SIGN_UP_PAGE = 1;
	public static final int DIARY_PAGE = 2;
	
	public DietDiaryMain() {
		
		setLayout(new FlowLayout());
		createPages();
		
		showHide(LOGIN_PAGE);
		
		setTitle("Diet Diary");
		setSize(700, 500);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBManager dbManager = DBManager.getInstance();
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
	}
	public void createPages() {
		pages.add(new LoginPage(this));
		pages.add(new JoinPage(this));
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
	
	public static void main(String[] args) {
		new DietDiaryMain();
	}
	
}
