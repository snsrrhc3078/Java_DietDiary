package com.dietdiary.components.datecomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.dietdiary.client.date.SearchSidePage;

public class NumPage extends JLabel{
	String label;
	SearchSidePage searchSidePage;
	int index;
	boolean isSelected;
	public NumPage(String label, SearchSidePage searchSidePage, int index) {
		super(label);
		setFont(new Font(getFont().getFontName(), getFont().getStyle(), 12));
		this.label = label;
		this.searchSidePage = searchSidePage;
		this.index = index;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Thread thread = new Thread() {
					@Override
					public void run() {
						init();
					}
				};
				thread.start();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!isSelected) {
					setForeground(Color.BLACK);
				}
			}
		});
	}
	
	public void init() {
		searchSidePage.setRequestProperties(searchSidePage.gettSearch().getText(), index);
		searchSidePage.getItemListFromJSON();
		searchSidePage.createItemList();
		searchSidePage.createNumPages();
		searchSidePage.showSelectedNumPage(index);
	}
	
	public int getIndex() {
		return index;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public boolean isSelected() {
		return isSelected;
	}
}
