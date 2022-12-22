package com.dietdiary.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MyCell extends JPanel{
	public MyCell(double width, double height) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension((int)width, (int)height));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(224, 184, 138));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 128));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		super.paintComponents(g);
	}
}
