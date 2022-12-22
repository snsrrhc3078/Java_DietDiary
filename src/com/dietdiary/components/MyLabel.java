package com.dietdiary.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MyLabel extends JLabel{
	
	public MyLabel(String label, int fontSize) {
		super(label);
//		setBorder(LineBorder.createBlackLineBorder());
		Font font = new Font(getFont().getFontName(), Font.BOLD, fontSize);
		setFont(font);
		Dimension d = getPreferredSize();
		d.setSize(d.width + 15, d.height+2);
		setPreferredSize(d);
		setHorizontalAlignment(JLabel.CENTER);
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(224, 184, 138));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 128));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		super.paintComponent(g);
	}
}
