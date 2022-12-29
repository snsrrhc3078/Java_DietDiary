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
	NumPageArrow lbPrev, lbNext;
	
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
		lbPrev = new NumPageArrow("◀", NumPageArrow.PREV);
		lbNext = new NumPageArrow("▶", NumPageArrow.NEXT);
		
		
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
		
		lbPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagingManager.moveBlockPrev();
				
				Thread thread = new Thread() {
					@Override
					public void run() {
						blockMoveInit();
					}
				};
				thread.start();
			}
		});
		
		lbNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagingManager.moveBlockNext();
				
				Thread thread = new Thread() {
					@Override
					public void run() {
						blockMoveInit();
					}
				};
				thread.start();
			}
		});
			
	}
	public synchronized void init() {
		setRequestProperties(tSearch.getText(), 1);
		getItemListFromJSON();
		pagingManager.init((int)totalCount);
		createItemList();
		createNumPages();
		NumPage.showSelectedNumPage(1, numPageList);
	}
	public void blockMoveInit() {
		setRequestProperties(tSearch.getText(), 1 + (pagingManager.getCurrentBlock() * pagingManager.getBlockSize()));
		getItemListFromJSON();
		createItemList();
		createNumPages();
		NumPage.showSelectedNumPage(numPageList.get(0).getPageNo(), numPageList);
	}
	public void numPageInit(int pageNo) {
		setRequestProperties(tSearch.getText(), pageNo);
		getItemListFromJSON();
		createItemList();
		createNumPages();
		NumPage.showSelectedNumPage(pageNo, numPageList);
	}
	
	//컴포넌트 생성
	public void createNumPages() {
		pageForm.removeAll();
		numPageList.removeAll(numPageList);
		
		int block = pagingManager.getCurrentBlock() * pagingManager.getBlockSize();
		int page = pagingManager.getCurrentPageSize();
		
		pageForm.add(lbPrev);
		for(int i =1 + block;i<=block + page;i++) {
			NumPage np = new NumPage(i);
			pageForm.add(np);
			numPageList.add(np);
			
			np.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Thread thread = new Thread() {
						@Override
						public void run() {
							numPageInit(np.getPageNo());
						}
					};
					thread.start();
				}
			});
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

	public MyLabel getLbTitle() {
		return lbTitle;
	}
	
}
