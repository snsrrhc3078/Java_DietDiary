package com.dietdiary.components.datecomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;


public class Item extends JPanel{

	int arcSize = 25;
	boolean isMouseOn;
	public Item(JComponent comp, double widthRatio, double heightRatio) {
		int compWidth = comp.getPreferredSize().width;
		int compHeight = comp.getPreferredSize().height;
		setPreferredSize(new Dimension((int) (compWidth * widthRatio), (int) (compHeight * heightRatio)));
		setLayout(new BorderLayout());
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isMouseOn=true;
				repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				isMouseOn=false;
				repaint();
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(224, 184, 138));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 128));
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);
		
		if(isMouseOn) {
			g.setColor(Color.WHITE);
			g.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);
		}
	}
}
