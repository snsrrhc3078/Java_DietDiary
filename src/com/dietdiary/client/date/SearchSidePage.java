package com.dietdiary.client.date;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.components.datecomponents.MySideButton;
import com.dietdiary.components.datecomponents.MyTextField;
import com.dietdiary.components.datecomponents.SearchedItem;

@SuppressWarnings("serial")
public class SearchSidePage extends SidePage{

	MyFormWrapper searchForm;
	MyLabel lbTitle;
	MyTextField tSearch;
	
	MyFormWrapper searchedListForm;
	
	MyFormWrapper pageForm;
	
	MyFormWrapper backButtonForm;
	MySideButton btBack;
	
	public SearchSidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		searchForm = new MyFormWrapper(this, 9/10.0, 1.5/10.0);
		searchForm.setLayout(new FlowLayout());
		lbTitle = new MyLabel("먹은 음식을 검색해주세요", 17);
		tSearch = new MyTextField(lbTitle, 1, 1);
		
		searchForm.add(lbTitle);
		searchForm.add(tSearch);
		
		searchedListForm = new MyFormWrapper(this, 9/10.0, 7/10.0);
		pageForm = new MyFormWrapper(this, 9/10.0, 0.5/10.0, 25);
		backButtonForm = new MyFormWrapper(this, 9/10.0, 0.5/10.0, 25);
		searchedListForm.setLayout(new FlowLayout());
		
		
		btBack = new MySideButton("Back");
		backButtonForm.add(btBack);
		
		//테스트코드
		ArrayList<SearchedItem> list = new ArrayList<>();
		for(int i =0;i<10;i++) {
			SearchedItem item = new SearchedItem(searchedListForm);
			searchedListForm.add(item);
			list.add(item);
		}
		
		add(searchForm);
		add(searchedListForm);
		add(pageForm);
		add(backButtonForm);
	}

}
