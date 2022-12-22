package com.dietdiary.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.dietdiary.client.DietDiaryMain;

public class DateCell extends MyCell{

	private boolean flag;
	private boolean isHasLog;
	private boolean isMouseOn;
	private boolean isMouseClicked;
	private int date;
	DietDiaryMain main;
	
	public DateCell(double width, double height, DietDiaryMain main) {
		super(width, height);
		this.main = main;
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
			@Override
			public void mousePressed(MouseEvent e) {
				isMouseClicked = true;
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				isMouseClicked=false;
				repaint();		
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(flag) {
					main.getDateInfoFrame().getSelected(date);
				}
			}
		});
	}
	
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isHasLog() {
		return isHasLog;
	}
	public void setHasLog(boolean isHasLog) {
		this.isHasLog = isHasLog;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(flag) {
			g.setFont(new Font(getFont().getName(), Font.BOLD, 15));
			
			
			if(date==5) isHasLog=true;
			else isHasLog = false;
			//마우스가 올라가있으면 출력
			if(isMouseOn) {
				g.setColor(new Color(224, 184, 138));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.WHITE);
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			}
			
			//마우스가 눌렸으면 출력
			if(isMouseClicked) {
				g.setColor(new Color(224, 184, 138));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.GRAY);
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			}
			
			//date 출력
			g.setColor(Color.BLACK);
			if(date<10) {
				g.drawString(Integer.toString(date), getWidth()/2-3, getHeight()/3);
			}else {
				g.drawString(Integer.toString(date), getWidth()/2-9, getHeight()/3);				
			}
			
			//식단 저장한 기록이 있으면 출력
			if(isHasLog) {
				g.setFont(new Font(getFont().getName(), Font.PLAIN, 10));
				g.drawString(1353 + "kcal", 10, getHeight()/3*2);
				g.drawString(10+"g "+50+"g "+20+"g", 10, getHeight()/3*3-5);
				
				g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
			}
		}
	}
	

}
