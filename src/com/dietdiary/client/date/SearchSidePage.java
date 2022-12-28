package com.dietdiary.client.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.components.datecomponents.MySideButton;
import com.dietdiary.components.datecomponents.MyTextField;
import com.dietdiary.components.datecomponents.NumPage;
import com.dietdiary.components.datecomponents.NumPageArrow;
import com.dietdiary.components.datecomponents.SearchedItem;
import com.dietdiary.util.APIRequester;
import com.dietdiary.util.JSONManager;
import com.dietdiary.util.PagingManager;

@SuppressWarnings("serial")
public class SearchSidePage extends SidePage{

	MyFormWrapper searchForm;
	MyLabel lbTitle;
	MyTextField tSearch;
	
	MyFormWrapper searchedListForm;
	
	MyFormWrapper pageForm;
	ArrayList<NumPage> numPageList = new ArrayList<>();
	
	MyFormWrapper backButtonForm;
	MySideButton btBack;
	
	List<JSONObject> itemInfoList = new ArrayList<>();
	ArrayList<SearchedItem> itemList = new ArrayList<>();
	PagingManager pagingManager = new PagingManager();
	long totalCount;
	
	APIRequester requester = new APIRequester(infoFrame.main.getRequestURL(), infoFrame.main.getServiceKey());
	
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
		FlowLayout f = new FlowLayout();
		f.setVgap(2);
		f.setHgap(2);
		pageForm.setLayout(f);
		
		btBack = new MySideButton("Back");
		backButtonForm.add(btBack);
		
		
		add(searchForm);
		add(searchedListForm);
		add(pageForm);
		add(backButtonForm);
		
		
		tSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Thread thread = new Thread() {
					public void run() {
						init();
					};
				};
				thread.start();
			}
		});
		btBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.showHide(DateInfoFrame.HISTORY_SIDE_PAGE);
				clearPage();
			}
		});
			
	}
	public void init() {
		setRequestProperties(tSearch.getText(), 1);
		getItemListFromJSON();
		pagingManager.init((int)totalCount);
		createItemList();
		createNumPages();
		showSelectedNumPage(numPageList.get(0).getIndex());
	}
	public void blockMoveInit() {
		setRequestProperties(tSearch.getText(), 1 + (pagingManager.getCurrentBlock() * pagingManager.getBlockSize()));
		getItemListFromJSON();
		createItemList();
		createNumPages();
		showSelectedNumPage(numPageList.get(0).getIndex());
	}
	
	//컴포넌트 생성
	public void createNumPages() {
		pageForm.removeAll();
		numPageList.removeAll(numPageList);
		
		NumPageArrow lbPrev = new NumPageArrow("◀", this, NumPageArrow.PREV);
		NumPageArrow lbNext = new NumPageArrow("▶", this, NumPageArrow.NEXT);
		
		int block = pagingManager.getCurrentBlock() * pagingManager.getBlockSize();
		int page = pagingManager.getCurrentPageSize();
		
		pageForm.add(lbPrev);
		for(int i =1 + block;i<=block + page;i++) {
			NumPage np = new NumPage(Integer.toString(i), this, i);
			pageForm.add(np);
			numPageList.add(np);
		}
		pageForm.add(lbNext);
		pageForm.updateUI();
		
	}
	public void createItemList() {
		searchedListForm.removeAll();
		itemList.removeAll(itemList);
		for(int i =0;i<itemInfoList.size();i++) {
			SearchedItem item = new SearchedItem(searchedListForm, itemInfoList.get(i), this);
			searchedListForm.add(item);
			itemList.add(item);
		}
		searchedListForm.updateUI();
	}
	
	//api, json관리
	public void setRequestProperties(String foodName, int pageNo) {
		requester.init();
		requester.addProperty("numOfRows", Integer.toString(10));
		requester.addProperty("type", "json");
		requester.addProperty("desc_kor", foodName);
		requester.addProperty("pageNo", Integer.toString(pageNo));
	}
	public void getItemListFromJSON() {
		 JSONObject json = JSONManager.parse(requester.request());
		 if(json != null) {
			 JSONObject body = (JSONObject)json.get("body");
			 totalCount = (Long)body.get("totalCount");
			 itemInfoList = JSONManager.getJSONArrayToList(body.get("items"));
		 }
	}
	//기타 메서드
	public void showSelectedNumPage(int index) {
		for(int i =0;i<numPageList.size();i++) {
			if(numPageList.get(i).getIndex()==index) {
				numPageList.get(i).setForeground(Color.WHITE);
				numPageList.get(i).setSelected(true);
			}else {
				numPageList.get(i).setForeground(Color.BLACK);
				numPageList.get(i).setSelected(false);
			}
		}
	}
	public void clearPage() {
		tSearch.setText("");
		itemInfoList.removeAll(itemInfoList);
		itemList.removeAll(itemList);
		numPageList.removeAll(numPageList);
		pagingManager.init(0);
		pageForm.removeAll();
		searchedListForm.removeAll();
	}
	
	//getter, setter
	public MyTextField gettSearch() {
		return tSearch;
	}
	public PagingManager getPagingManager() {
		return pagingManager;
	}
	public MyLabel getLbTitle() {
		return lbTitle;
	}
	
	
}
