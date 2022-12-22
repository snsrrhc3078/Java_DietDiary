package com.dietdiary.client.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SidePage extends JPanel{
	DateInfoFrame infoFrame;
	
	public SidePage(DateInfoFrame infoFrame) {
		this.infoFrame = infoFrame;
		setPreferredSize(new Dimension(270, 450));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(255, 255, 128));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
	}
}
