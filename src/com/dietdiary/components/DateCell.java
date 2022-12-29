package com.dietdiary.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.dietdiary.client.DietDiaryMain;
import com.dietdiary.domain.History;

public class DateCell extends MyCell{

	private boolean flag;
	private boolean isHasLog;
	private boolean isMouseOn;
	private boolean isMouseClicked;
	private int date;
	DietDiaryMain main;
	History history;
	public boolean isToday;
	Calendar currentTime;
	
	public DateCell(double width, double height, DietDiaryMain main, Calendar currentTime) {
		super(width, height);
		this.main = main;
		this.currentTime = currentTime;
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
	public History getHistory() {
		return history;
	}
	public void setHistory(History history) {
		if(history != null) {
			isHasLog=true;
		}else {
			isHasLog=false;
		}
		this.history = history;
	}
	
	public void setToday() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		if(
				year == currentTime.get(Calendar.YEAR) &&
				month == currentTime.get(Calendar.MONTH) &&
				day == date) {
			isToday = true;
		}else {
			isToday = false;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(flag) {
			g.setFont(new Font(getFont().getName(), Font.BOLD, 15));
			
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
				g.setColor(new Color(0x7f99f8));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			}
			
			if(isHasLog) {
				g.setColor(new Color(0xFFDE66));
				
				if(isMouseOn) {
					g.setColor(new Color(0x7f99f8));
				}
				
				if(isMouseClicked) {
					g.setColor(Color.WHITE);
				}
				int size = 4;
				g.fillRoundRect(size, size, getWidth()-size*2, getHeight() - size*2, 10, 10);
			}
			
			//date 출력
			if(isToday) {
				g.setColor(Color.RED);
			}else {
				g.setColor(Color.BLACK);
			}
			if(date<10) {
				g.drawString(Integer.toString(date), getWidth()/2-3, getHeight()/3+2);
			}else {
				g.drawString(Integer.toString(date), getWidth()/2-9, getHeight()/3+2);				
			}
			
			//식단 저장한 기록이 있으면 출력
			if(isHasLog) {
				g.setColor(Color.BLACK);
				g.setFont(new Font(getFont().getName(), Font.PLAIN, 10));
				g.drawString(history.getTotal_calories() + "kcal", 10, getHeight()/3*2-3);
				g.drawString(history.getTotal_carbs()+"g "+history.getTotal_proteins()+"g "+history.getTotal_fats()+"g", 10, getHeight()/3*3-8);
				
//				g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
			}
		}
	}
	

}
