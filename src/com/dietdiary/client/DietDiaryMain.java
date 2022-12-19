package com.dietdiary.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DietDiaryMain extends JFrame{
	
	ArrayList<Page> pages = new ArrayList<>();
	
	public DietDiaryMain() {
		
		setLayout(new FlowLayout());
		createPages();
		
		
		setTitle("Diet Diary");
		setSize(700, 500);
//		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	public void createPages() {
		pages.add(new LoginPage(this));
		pages.add(new JoinPage(this));
		
		for(int i =0;i<pages.size();i++) {
			add(pages.get(i));
		}
	}
	
	public void showHide(int n) {
		
	}
	
	public static void main(String[] args) {
		new DietDiaryMain();
	}
}
