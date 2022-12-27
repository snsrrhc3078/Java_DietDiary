package com.dietdiary.client.date;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.components.datecomponents.MySideButton;
import com.dietdiary.components.datecomponents.MyTextField;
import com.dietdiary.domain.Food;
import com.dietdiary.domain.History;

public class DetailSidePage extends SidePage{
	Food food; //dto

	MyFormWrapper nameForm;
	MyLabel lbFoodName;
	
	MyFormWrapper nutritionForm;
	MyLabel lbCarb, lbPro, lbFat, lbKcal ,lbYear;
	MyLabel lbCarbValue, lbProValue, lbFatValue, lbKcalValue, lbYearValue;
	JPanel[] labelWrapper = new JPanel[5];
	
	MyFormWrapper quantityForm;
	MyLabel lbServe;
	MyTextField tServe;
	
	MyFormWrapper backButtonForm;
	MySideButton btBack;
	
	int fontsize = 20;
	
	public DetailSidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		createNameForm();
		createNutritionForm();
		createQuantityForm();
		createBackButtonForm();
		
	}
	public void createNameForm() {
		nameForm = new MyFormWrapper(this, 9/10.0, 0.75/10.0, 25);
		lbFoodName = new MyLabel("음식이름", 17);
		
		//searcheSidePage의 lbTitle의 preferredSide를 가져와서 lbFoodName에 넣어주기 위함
		SearchSidePage page = (SearchSidePage)infoFrame.getSidePages().get(DateInfoFrame.SEARCH_SIDE_PAGE);
		Dimension d = page.getLbTitle().getPreferredSize();
		lbFoodName.setPreferredSize(d);
		
		nameForm.setLayout(new FlowLayout());
		
		nameForm.add(lbFoodName);
		add(nameForm);
	}
	public void createNutritionForm() {
		nutritionForm = new MyFormWrapper(this, 9/10.0, 4.5/10.0);
		nutritionForm.setLayout(new GridLayout(5, 1));
		
		
		lbCarb = new MyLabel("순탄수", fontsize);
		lbPro = new MyLabel("단백질", fontsize);
		lbFat = new MyLabel("지방", fontsize);
		lbKcal = new MyLabel("칼로리", fontsize);
		lbYear = new MyLabel("등록년도", fontsize);
		lbCarbValue = new MyLabel("NNN g ", fontsize);
		lbProValue = new MyLabel("NNN g ", fontsize);
		lbFatValue = new MyLabel("NNN g ", fontsize);
		lbKcalValue = new MyLabel("NNNN kcal", fontsize);
		lbYearValue = new MyLabel("YYYY 년", fontsize);
		
		for(int i =0;i<labelWrapper.length;i++) {
			labelWrapper[i] = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
				}
			};
		}
		
		labelWrapper[0].add(lbCarb);
		labelWrapper[0].add(lbCarbValue);
		labelWrapper[1].add(lbPro);
		labelWrapper[1].add(lbProValue);
		labelWrapper[2].add(lbFat);
		labelWrapper[2].add(lbFatValue);
		labelWrapper[3].add(lbKcal);
		labelWrapper[3].add(lbKcalValue);
		labelWrapper[4].add(lbYear);
		labelWrapper[4].add(lbYearValue);
		for(int i =0;i<labelWrapper.length;i++) {
			nutritionForm.add(labelWrapper[i]);
		}
		
		add(nutritionForm);
	}
	public void createQuantityForm() {
		quantityForm = new MyFormWrapper(this, 9/10.0, 1.6/10.0);
		quantityForm.setLayout(new FlowLayout());
		lbServe = new MyLabel("  인분(NNN g)  ", fontsize);
		tServe = new MyTextField(lbServe, 1, 1);
		tServe.setHorizontalAlignment(JTextField.CENTER);
		tServe.setText("1");
		
		quantityForm.add(lbServe);
		quantityForm.add(tServe);
		add(quantityForm);
	}
	public void createBackButtonForm() {
		backButtonForm = new MyFormWrapper(this, 9/10.0, 0.5/10.0, 25);
		btBack = new MySideButton("Back");
		backButtonForm.add(btBack);
		
		btBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.showHide(DateInfoFrame.HISTORY_SIDE_PAGE);
				clearPage();
			}
		});
		
		add(backButtonForm);
	}
	public void getDetail(JSONObject item) {
		Calendar selectedTime = infoFrame.getSelectedTime();
		
		History history = new History(); //외래키를 위해 생성
		history.setYear(selectedTime.get(Calendar.YEAR));
		history.setMonth(selectedTime.get(Calendar.MONTH));
		history.setDay(selectedTime.get(Calendar.DATE));
		
		food = new Food();
		
		String foodName = (String)item.get("DESC_KOR");
		int cal, carbs, proteins, fats;
		String servesize, regyear;
		cal = (int)Double.parseDouble((String)item.get("NUTR_CONT1"));
		carbs = (int)Double.parseDouble((String)item.get("NUTR_CONT2"));
		proteins = (int)Double.parseDouble((String)item.get("NUTR_CONT3"));
		fats = (int)Double.parseDouble((String)item.get("NUTR_CONT4"));
		servesize = (String)item.get("SERVING_WT");
		regyear = (String)item.get("BGN_YEAR");
		//이름, 칼로리, 탄수, 단백질, 지방, 연도, , 서빙사이즈
		food.setHistory(history);
		food.setName(foodName);
		food.setCalories(cal);
		food.setCarbs(carbs);
		food.setProteins(proteins);
		food.setFats(fats);
		food.setServeSize(servesize);
		food.setRegyear(regyear);
		
		setLabel();
	}
	public void setLabel() {
		lbFoodName.setText(food.getName());
		lbKcalValue.setText(Integer.toString((int)(food.getCalories() * Double.parseDouble(tServe.getText()))) + " kcal");
		lbCarbValue.setText(Integer.toString((int)(food.getCarbs() * Double.parseDouble(tServe.getText()))) + " g");
		lbProValue.setText(Integer.toString((int)(food.getProteins() * Double.parseDouble(tServe.getText()))) + " g");
		lbFatValue.setText(Integer.toString((int)(food.getFats() * Double.parseDouble(tServe.getText()))) + " g");
		lbServe.setText("  인분(" + food.getServeSize() + " g)  ");
		lbYearValue.setText(food.getRegyear() + " 년");
	}

	public void clearPage() {
		
	}
}
