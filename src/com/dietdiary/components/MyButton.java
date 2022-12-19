package com.dietdiary.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;

public class MyButton extends JButton{
	String label;
	Dimension labelSize;
	public MyButton(String label) {
		super(label);
		this.label = label;
		Dimension d = getPreferredSize();
		setBorder(null);
		labelSize = getPreferredSize();
		setPreferredSize(d);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(224,184, 138));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(188, 94, 0));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		g.setColor(Color.BLACK);
//		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
		g.setFont(new Font(getFont().getFontName(), Font.BOLD, getFont().getSize()));
		g.drawString(label, getWidth()/2-(labelSize.width/2), getHeight()/2+ 5);
	}
}
