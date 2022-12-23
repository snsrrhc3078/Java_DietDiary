package com.dietdiary.components.datecomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;


public class Item extends JPanel{

	int arcSize = 25;
	public Item(JComponent comp, double widthRatio, double heightRatio) {
		int compWidth = comp.getPreferredSize().width;
		int compHeight = comp.getPreferredSize().height;
		setPreferredSize(new Dimension((int) (compWidth * widthRatio), (int) (compHeight * heightRatio)));
		setLayout(new BorderLayout());
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(224, 184, 138));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 128));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);
	}
}
