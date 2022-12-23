package com.dietdiary.components.datecomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;

public class MySideButton extends JButton{
	String label;
	Dimension labelSize;
	boolean isMouseOn;
	boolean isMouseClicked;
	public MySideButton(String label) {
		super(label);
		this.label = label;
		Dimension d = getPreferredSize();
		setBorder(null);
		labelSize = getPreferredSize();
		setPreferredSize(d);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isMouseOn = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				isMouseOn = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				isMouseClicked = true;
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				isMouseClicked = false;
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(255, 255, 128));
		g.fillRect(0, 0, getWidth(), getHeight());
		if(isMouseClicked) {
			g.setColor(new Color(98, 52, 0));
		}else {
			g.setColor(new Color(188, 94, 0));
		}
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		g.setColor(Color.BLACK);
//		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
		g.setFont(new Font(getFont().getFontName(), Font.BOLD, getFont().getSize()));
		g.drawString(label, getWidth()/2-(labelSize.width/2), getHeight()/2+ 5);
		if(isMouseOn) {
			g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
		}
	}
}
