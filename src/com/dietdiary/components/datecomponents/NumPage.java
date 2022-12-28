package com.dietdiary.components.datecomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;

import com.dietdiary.client.date.SearchSidePage;

public class NumPage extends JLabel{
	int pageNo;
	boolean isSelected;
	public NumPage(int pageNo) {
		super(Integer.toString(pageNo));
		setFont(new Font(getFont().getFontName(), getFont().getStyle(), 12));
		this.pageNo = pageNo;
		addMouseListener(new MouseAdapter() {
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
	

	public static void showSelectedNumPage(int index, List<NumPage> numPageList) {
		for(int i =0;i<numPageList.size();i++) {
			if(numPageList.get(i).getPageNo()==index) {
				numPageList.get(i).setForeground(Color.WHITE);
				numPageList.get(i).setSelected(true);
			}else {
				numPageList.get(i).setForeground(Color.BLACK);
				numPageList.get(i).setSelected(false);
			}
		}
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public boolean isSelected() {
		return isSelected;
	}
}
