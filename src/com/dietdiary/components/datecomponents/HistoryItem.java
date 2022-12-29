package com.dietdiary.components.datecomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.dietdiary.client.date.DateInfoFrame;
import com.dietdiary.client.date.DetailSidePage;
import com.dietdiary.client.date.HistorySidePage;
import com.dietdiary.domain.Food;

public class HistoryItem extends Item{
	JPanel pEast;
	MySideButton btDel;
	Food food;
	HistorySidePage historySidePage;
	public HistoryItem(JComponent comp, double widthRatio, double heightRatio, Food food, HistorySidePage historySidePage) {
		super(comp, widthRatio, heightRatio);
		this.food = food;
		this.historySidePage = historySidePage;
		
		pEast = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
			}
		};
		btDel = new MySideButton("X");
		pEast.add(btDel);
		add(pEast, BorderLayout.EAST);
		
		btDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DetailSidePage page = (DetailSidePage)historySidePage.getInfoFrame().getSidePages().get(DateInfoFrame.DETAIL_SIDE_PAGE);
				page.deleteFood(food.getFood_idx());
				historySidePage.init();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Font defaultFont = g.getFont();
		Font foodNameFont = new Font(defaultFont.getFontName(), Font.BOLD, 12);
		
		g.setColor(Color.BLACK);
		g.setFont(foodNameFont);
		g.drawString(food.getName(), 10, 15);
		
		g.setFont(defaultFont);
		double quantity = food.getQuantity();
		int totalServeSize = (int)(Integer.parseInt(food.getServeSize()) * quantity);
		String brand = food.getBrand();
		if(brand==null) {
			brand = "";
		}else {
			brand += " ";
		}
		//만약 quantity의 값이 정수나 마찬가지라면(ex 2.0)
		if((double)(int)quantity == quantity) {
			g.drawString(brand + (int)quantity + "인분(" + totalServeSize + "g)", 10, 30);
		}else {
			g.drawString(brand + quantity + "인분(" + totalServeSize + "g)", 10, 30);
		}

		g.drawString("1000" + "kcal", 135, 30);
	}

}
