package com.dietdiary.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JFrame;

public class DietDiaryMain extends JFrame{
	
	
	
	public DietDiaryMain() {
		
//		LoginPage page = new LoginPage(this);
		JoinPage page = new JoinPage(this);
		
		setLayout(new FlowLayout());
		
		add(page);
		
		setTitle("Diet Diary");
		setSize(700, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new DietDiaryMain();
	}
}
