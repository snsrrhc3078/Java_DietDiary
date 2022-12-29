package com.dietdiary.client.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.components.datecomponents.HistoryItem;
import com.dietdiary.components.datecomponents.Item;
import com.dietdiary.components.datecomponents.NumPage;
import com.dietdiary.components.datecomponents.NumPageArrow;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.domain.Food;
import com.dietdiary.domain.History;
import com.dietdiary.model.repository.FoodDAO;
import com.dietdiary.model.repository.HistoryDAO;
import com.dietdiary.util.PagingManager;

public class HistorySidePage extends SidePage{

	int year;
	int month;
	int day;
	
	MyFormWrapper historyInfoForm;
	MyLabel lbTime, lbTotalCal, lbTotalNutritions;
	
	MyFormWrapper itemListForm;
	MyButton btReg;
	
	MyFormWrapper pageForm;
	NumPageArrow lbPrev, lbNext;
	ArrayList<NumPage> numPageList = new ArrayList<>();
	
	History history;
	List<Food> itemInfoList = new ArrayList<>();
	List<HistoryItem> itemList = new ArrayList<>();
	PagingManager pagingManager = new PagingManager();
	
	public HistorySidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		createHistoryInfoForm();
		createItemListForm();
		createPageForm();
		
		pagingManager.setPageSize(5);
	
	}
	public void createHistoryInfoForm() {
		historyInfoForm = new MyFormWrapper(this, 9/10.0, 2/10.0);
		
		lbTime = new MyLabel("YYYY년 MM월 DD일 식단", 15);
		lbTotalCal = new MyLabel("섭취한 칼로리: " + 0 + "kcals", 15);
		lbTotalNutritions = new MyLabel("탄수 :"+ 0 + "g 단백질 :" +0+"g 지방 :"+0 + "g" , 15);
		
		historyInfoForm.setLayout(new FlowLayout());
		
		historyInfoForm.add(lbTime);
		historyInfoForm.add(lbTotalCal);
		historyInfoForm.add(lbTotalNutritions);
		
		add(historyInfoForm);
	}
	public void createItemListForm() {
		itemListForm = new MyFormWrapper(this, 9/10.0, 5.5/10.0);
		
		btReg = new MyButton("Add Food");
		
		itemListForm.setLayout(new FlowLayout());
		
		add(itemListForm);
		
		btReg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.showHide(DateInfoFrame.SEARCH_SIDE_PAGE);
			}
		});
	}
	public void createPageForm() {
		pageForm = new MyFormWrapper(this, 9/10.0, 0.5/10.0, 25);
		FlowLayout f = new FlowLayout();
		f.setVgap(2);
		f.setHgap(2);
		pageForm.setLayout(f);
		
		lbPrev = new NumPageArrow("◀", NumPageArrow.PREV);
		lbNext = new NumPageArrow("▶", NumPageArrow.NEXT);
		
		add(pageForm);
		
		lbPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagingManager.moveBlockPrev();
				
				Thread thread = new Thread() {
					@Override
					public void run() {
						init(1);
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
						init(1);
					}
				};
				thread.start();
			}
		});
	}

	
	public void setTimeLabel(Calendar cal) {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		day = cal.get(Calendar.DATE);
		
		lbTime.setText(year+"년 "+month+"월 "+ day+"일 식단");
		updateUI();
	}
	
	/**
	 * 최초에 itemInfoList 받아올떄 사용하는 init
	 */
	public void init() {
		getHistory();
		getItemInfoList();
		pagingManager.init(itemInfoList.size());
		createItemList();
		createNumPages();
		NumPage.showSelectedNumPage(1, numPageList);
	}

	public void init(int pageNo) {
		pagingManager.setCurrentPage(pageNo%pagingManager.getPageSize());
		createItemList();
		createNumPages();
		NumPage.showSelectedNumPage(pagingManager.getCurrentPage() + pagingManager.getCurrentBlockByPage(), numPageList);
	}
	public void getHistory() {
		HistoryDAO historyDAO = infoFrame.getHistoryDAO();
		DietDiaryMembers dietDiaryMembers = new DietDiaryMembers();
		dietDiaryMembers.setDiet_diary_members_idx(infoFrame.main.getLoginedUserInfo().getDiet_diary_members_idx());
		History FKAndDates = new History();
		FKAndDates.setDietDiaryMembers(dietDiaryMembers);
		FKAndDates.setYear(year);
		FKAndDates.setMonth(month);
		FKAndDates.setDay(day);
		history = historyDAO.selectByFKAndDates(FKAndDates);
	}
	
	public void getItemInfoList() {
		FoodDAO foodDAO = infoFrame.getFoodDAO();
		if(history!=null) {
			itemInfoList = foodDAO.selectAllByFK(history.getHistory_idx());
		}else {
			itemInfoList = new ArrayList<>();
		}
	}
	public void createItemList() {
		itemListForm.removeAll();
		itemList.removeAll(itemList);
		itemListForm.add(btReg);
		int pageByItem = pagingManager.getCurrentPageByItem();
		int itemSize = pagingManager.getCurrentItemSize();
		int blockByItem = pagingManager.getCurrentBlockByPage() * pagingManager.getPageSize();
		
		for(int i=0 + pageByItem + blockByItem;i< itemSize + pageByItem + blockByItem;i++) {
			HistoryItem item = new HistoryItem(itemListForm, 9.5/10.0, 1.463/10.0, itemInfoList.get(i), this);
			itemListForm.add(item);
			itemList.add(item);
		}
		
		itemListForm.updateUI();
	}
	public void createNumPages() {
		pageForm.removeAll();
		numPageList.removeAll(numPageList);
		
		int block = pagingManager.getCurrentBlockByPage();
		int page = pagingManager.getCurrentPageSize();
		
		pageForm.add(lbPrev);
		for(int i = 1 + block;i<=block + page;i++) {
			NumPage np = new NumPage(i);
			pageForm.add(np);
			numPageList.add(np);
			
			np.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Thread thread = new Thread() {
						@Override
						public void run() {
							init(np.getPageNo());
						}
					};
					thread.start();
				}
			});
		}
		pageForm.add(lbNext);
		pageForm.updateUI();
	}
	public void clearPage() {
		init();
	}
	
	/**
	 * 
	 * @param FKAndDates 등록할 history의 정보를 가진 dto, DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY 는 반드시 가지고 있어야 한다 
	 * @return DB에 insert되어 primaryKey객체의 정보를 가진 레코드 1개의 DTO를 반환한다. 그러나 이미 primaryKey 객체에 해당하는 레코드가 DB에 존재할경우 이미 존재하는 해당 레코드의 DTO를 반환한다
	 * @throws SQLException
	 */
	public History registHistory(History FKAndDates) throws SQLException{
		HistoryDAO historyDAO = infoFrame.getHistoryDAO();
		
		History isRecordExist = historyDAO.selectByFKAndDatesForUpdate(FKAndDates);
		//primaryKey에 해당하는 레코드가 있다면 해당 레코드를 반환
		if(isRecordExist!=null) {
			return isRecordExist;
		}
		
		//primaryKey에 해당하는 레코드가 없으므로 primaryKey를 insert
		int result = historyDAO.insert(FKAndDates);
		//insert했지만 변경된 row가 없다면 실패이므로 throw
		if(result==0) {
			throw new SQLException();
		}
		//primaryKey로 insert 했으므로 insert된 primaryKey의 레코드를 가져옴
		isRecordExist = historyDAO.selectByFKAndDatesForUpdate(FKAndDates);
		//레코드를 가져오는데 성공했다면 반환, 실패했다면 throw
		if(isRecordExist!=null) {
			return isRecordExist;
		}else {
			throw new SQLException();
		}
		
		
	}
}
