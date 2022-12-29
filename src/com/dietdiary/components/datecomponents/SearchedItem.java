package com.dietdiary.components.datecomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import com.dietdiary.client.date.DateInfoFrame;
import com.dietdiary.client.date.DetailSidePage;
import com.dietdiary.client.date.SearchSidePage;
import com.dietdiary.client.date.SidePage;

public class SearchedItem extends Item{
	JSONObject item;
	
	SearchSidePage searchSidePage;
	public SearchedItem(JComponent comp, JSONObject item, SearchSidePage searchSidePage) {
		super(comp, 9/10.0, 1/12.0);
		this.item = item;
		this.searchSidePage = searchSidePage;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DateInfoFrame infoFrame = searchSidePage.getInfoFrame();
				List<SidePage> list = infoFrame.getSidePages();
				DetailSidePage page = (DetailSidePage)list.get(DateInfoFrame.DETAIL_SIDE_PAGE);
				page.getDetail(infoFrame.parseJSONObjectToFood(item));
				page.setRegistButton(DetailSidePage.REGIST_BUTTON);
				infoFrame.showHide(DateInfoFrame.DETAIL_SIDE_PAGE);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 11));
		StringBuilder itemInfo = new StringBuilder();
		String foodName = (String)item.get("DESC_KOR");
		if(foodName.length()>7) {
			itemInfo.append(foodName.substring(0, 7) + "...");
		}else {
			itemInfo.append(foodName);
		}
		String cals = Integer.toString((int)(Double.parseDouble((String)item.get("NUTR_CONT1"))));
		itemInfo.append("(" + (String)item.get("SERVING_WT") + "g, ");
		itemInfo.append(cals + "kcal, ");
		itemInfo.append((String)item.get("BGN_YEAR") + ")");
		g.drawString(itemInfo.toString(), 10, 17);
	}

}
