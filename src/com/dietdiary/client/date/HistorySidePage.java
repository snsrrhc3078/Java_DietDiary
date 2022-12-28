package com.dietdiary.client.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.dietdiary.domain.Food;
import com.dietdiary.domain.History;
import com.dietdiary.model.repository.FoodDAO;
import com.dietdiary.model.repository.HistoryDAO;

public class HistorySidePage extends SidePage{

	int year;
	int month;
	int date;
	
	MyFormWrapper historyInfo;
	MyLabel lbTime, lbTotalCal, lbTotalNutritions;
	
	MyFormWrapper foodHistory;
	MyButton btReg;
	
	
	History history;
	List<Food> itemInfoList = new ArrayList<>();
	
	
	public HistorySidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		
		lbTime = new MyLabel("YYYY년 MM월 DD일 식단", 15);
		lbTotalCal = new MyLabel("섭취한 칼로리: " + 0 + "kcals", 15);
		lbTotalNutritions = new MyLabel("탄수 :"+ 0 + "g 단백질 :" +0+"g 지방 :"+0 + "g" , 15);
		
		historyInfo = new MyFormWrapper(this, 9/10.0, 2/10.0);
		foodHistory = new MyFormWrapper(this, 9/10.0, 7.5/10.0);
		
		btReg = new MyButton("Add Food");
		
		//테스트 코드
		Item item = new HistoryItem(foodHistory);
		
		
		historyInfo.setLayout(new FlowLayout());
		foodHistory.setLayout(new FlowLayout());
		
		historyInfo.add(lbTime);
		historyInfo.add(lbTotalCal);
		historyInfo.add(lbTotalNutritions);
		
		foodHistory.add(btReg);
		foodHistory.add(item);
		
		add(historyInfo);
		add(foodHistory);
		
		btReg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.showHide(DateInfoFrame.SEARCH_SIDE_PAGE);
			}
		});
	}
	public void setTimeLabel(Calendar cal) {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
		
		lbTime.setText(year+"년 "+(month+1)+"월 "+ date+"일 식단");
		updateUI();
	}
	
	
	/**
	 * 
	 * @param FKAndDates 등록할 history의 정보를 가진 dto, DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY 는 반드시 가지고 있어야 한다 
	 * @return DB에 insert되어 primaryKey객체의 정보를 가진 레코드 1개의 DTO를 반환한다. 그러나 이미 primaryKey 객체에 해당하는 레코드가 DB에 존재할경우 이미 존재하는 해당 레코드의 DTO를 반환한다
	 * @throws SQLException
	 */
	public History registHistory(History FKAndDates) throws SQLException{
		HistoryDAO historyDAO = infoFrame.getHistoryDAO();
		
		History isRecordExist = historyDAO.selectByFKAndDates(FKAndDates);
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
		isRecordExist = historyDAO.selectByFKAndDates(FKAndDates);
		//레코드를 가져오는데 성공했다면 반환, 실패했다면 throw
		if(isRecordExist!=null) {
			return isRecordExist;
		}else {
			throw new SQLException();
		}
		
		
	}
}
