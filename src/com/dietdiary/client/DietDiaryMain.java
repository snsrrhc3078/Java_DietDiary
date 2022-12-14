package com.dietdiary.client;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class DietDiaryMain extends JFrame{
	
	
	
	public DietDiaryMain() {
		
		Page page = new Page(this);
		setLayout(new FlowLayout());
		
		add(page);
		
		setSize(700, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new DietDiaryMain();
	}
}
