package com.dietdiary.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Page extends JPanel{
	
	
	
	DietDiaryMain main;
	public Page(DietDiaryMain main) {
		this.main = main;
		setPreferredSize(new Dimension(650, 450));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(255, 255, 128));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
	}
}
