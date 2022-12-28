package com.dietdiary.client.date;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.simple.JSONObject;

import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyLabel;
import com.dietdiary.components.datecomponents.MySideButton;
import com.dietdiary.components.datecomponents.MyTextField;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.domain.Food;
import com.dietdiary.domain.History;
import com.dietdiary.util.DBManager;

public class DetailSidePage extends SidePage{
	Food food; //dto

	MyFormWrapper nameForm;
	MyLabel lbFoodName;
	
	MyFormWrapper nutritionForm;
	MyLabel lbCarb, lbPro, lbFat, lbKcal, lbBrand, lbYear;
	MyLabel lbCarbValue, lbProValue, lbFatValue, lbKcalValue, lbBrandValue,lbYearValue;
	JPanel[] labelWrapper = new JPanel[6];
	
	MyFormWrapper quantityForm;
	MyLabel lbServe;
	JFormattedTextField tServe;
	
	MyFormWrapper registButtonForm;
	MySideButton btReg;
	
	MyFormWrapper backButtonForm;
	MySideButton btBack;
	
	
	
	int fontsize = 20;
	
	public DetailSidePage(DateInfoFrame infoFrame) {
		super(infoFrame);
		createNameForm();
		createNutritionForm();
		createQuantityForm();
		createRegistButtonForm();
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
		nutritionForm.setLayout(new GridLayout(6, 1));
		
		
		lbCarb = new MyLabel("순탄수", fontsize);
		lbPro = new MyLabel("단백질", fontsize);
		lbFat = new MyLabel("지방", fontsize);
		lbKcal = new MyLabel("칼로리", fontsize);
		lbBrand = new MyLabel("제조원", fontsize);
		lbYear = new MyLabel("등록년도", fontsize);
		lbCarbValue = new MyLabel("NNN g ", fontsize);
		lbProValue = new MyLabel("NNN g ", fontsize);
		lbFatValue = new MyLabel("NNN g ", fontsize);
		lbKcalValue = new MyLabel("NNNN kcal", fontsize);
		lbBrandValue = new MyLabel("SSSSSSSSSS", fontsize);
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
		labelWrapper[4].add(lbBrand);
		labelWrapper[4].add(lbBrandValue);
		labelWrapper[5].add(lbYear);
		labelWrapper[5].add(lbYearValue);
		for(int i =0;i<labelWrapper.length;i++) {
			nutritionForm.add(labelWrapper[i]);
		}
		
		add(nutritionForm);
	}
	public void createQuantityForm() {
		quantityForm = new MyFormWrapper(this, 9/10.0, 1.6/10.0);
		quantityForm.setLayout(new FlowLayout());
		lbServe = new MyLabel("  인분(NNN g)  ", fontsize);
		tServe = new JFormattedTextField();
		tServe.setHorizontalAlignment(JTextField.CENTER);
		tServe.setText("1");
		tServe.setPreferredSize(lbServe.getPreferredSize());
		
		quantityForm.add(lbServe);
		quantityForm.add(tServe);
		add(quantityForm);
		
		tServe.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        Runnable format = new Runnable() {
		            @Override
		            public void run() {
		                String text = tServe.getText();
		                if(!text.matches("\\d*(\\.\\d{0,1})?")){
		                    tServe.setText(text.substring(0,text.length()-1));
		                }
		            }
		        };
		        SwingUtilities.invokeLater(format);
		    }
		    @Override
		    public void removeUpdate(DocumentEvent e) {

		    }
		    @Override
		    public void changedUpdate(DocumentEvent e) {
		    }
		});
		tServe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (tServe.getText().charAt(0) == '.') {
						tServe.setText("0" + tServe.getText());
					}
					setLabel();
				}
			}
		});
		tServe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (tServe.getText().charAt(0) == '.') {
					tServe.setText("0" + tServe.getText());
				}
				setLabel();
			}
		});
	}
	public void createRegistButtonForm() {
		registButtonForm = new MyFormWrapper(this, 9/10.0, 0.5/10.0, 25);
		btReg = new MySideButton("OK");
		registButtonForm.add(btReg);
		
		
		btReg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registFood();
				infoFrame.showHide(DateInfoFrame.HISTORY_SIDE_PAGE);
			}
		});
		
		add(registButtonForm);
	}
	public void createBackButtonForm() {
		backButtonForm = new MyFormWrapper(this, 9/10.0, 0.5/10.0, 25);
		btBack = new MySideButton("Back");
		backButtonForm.add(btBack);
		
		btBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.showHide(DateInfoFrame.SEARCH_SIDE_PAGE);
				clearPage();
			}
		});
		
		add(backButtonForm);
	}
	public void getDetail(JSONObject item) {
		Calendar selectedTime = infoFrame.getSelectedTime();
		
		DietDiaryMembers dietDiaryMembers = new DietDiaryMembers();
		dietDiaryMembers.setDiet_diary_members_idx(infoFrame.main.getLoginedUserInfo().getDiet_diary_members_idx());
		
		History history = new History(); //외래키를 위해 생성
		history.setDietDiaryMembers(dietDiaryMembers);
		history.setYear(selectedTime.get(Calendar.YEAR));
		history.setMonth(selectedTime.get(Calendar.MONTH)+1);
		history.setDay(selectedTime.get(Calendar.DATE));
		
		food = new Food();
		
		String foodName = (String)item.get("DESC_KOR");
		String brand = (String)item.get("ANIMAL_PLANT");
		int cal, carbs, proteins, fats;
		String servesize, regyear;
		cal = (int)Double.parseDouble((String)item.get("NUTR_CONT1"));
		carbs = (int)Double.parseDouble((String)item.get("NUTR_CONT2"));
		proteins = (int)Double.parseDouble((String)item.get("NUTR_CONT3"));
		fats = (int)Double.parseDouble((String)item.get("NUTR_CONT4"));
		servesize = (String)item.get("SERVING_WT");
		regyear = (String)item.get("BGN_YEAR");
		//이름, 브랜드, 칼로리, 탄수, 단백질, 지방, 서빙사이즈, 연도
		food.setHistory(history);
		food.setName(foodName);
		food.setBrand(brand);
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
		lbBrandValue.setText(food.getBrand());
		lbYearValue.setText(food.getRegyear() + " 년");
	}
	public void registFood() {
		HistorySidePage page = (HistorySidePage)infoFrame.getSidePages().get(DateInfoFrame.HISTORY_SIDE_PAGE);
		DBManager dbManager = infoFrame.main.getDbManager();
		boolean isCommited = false;
		try {
			dbManager.getConnection().setAutoCommit(false);
			
			//registHistory 메서드에서 처리된 DTO 받기
			History history = page.registHistory(food.getHistory());
			//food의 history에 덮어쓴다 왜냐하면 registHistory에서 받아온 History객체는
			//완전한 History 정보를 가지고 있기 때문에 foodDTO에서 insert 할떄 필요하다
			food.setHistory(history);
			
			food.setQuantity(Double.parseDouble(tServe.getText()));
			int result = infoFrame.getFoodDAO().insert(food);
			
			//foodDAO의 insert에서 문제가 생겼으면 throw
			if(result == 0) {
				throw new SQLException();
			}
			isCommited=true;
			
			//모두 성공적으로 완료되었으면 commit
			dbManager.getConnection().commit();
		} catch (SQLException e) {
			//하나라도 문제 생겼으면 rollback
			try {
				dbManager.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			isCommited = false;
			e.printStackTrace();
		}finally {
			//트랜잭션 끝났으니 autoCommit 원상복귀
			try {
				dbManager.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if(isCommited) {
			JOptionPane.showMessageDialog(infoFrame, "등록 성공");
		}else {
			JOptionPane.showMessageDialog(infoFrame, "등록 실패");
		}
		infoFrame.clearAllPages();
	}

	public void clearPage() {
		food = null;
		lbCarbValue.setText("0 g ");
		lbProValue.setText("0 g ");
		lbFatValue.setText("0 g ");
		lbKcalValue.setText("0 kcal");
		lbBrandValue.setText("");
		lbYearValue.setText("N/A 년");
		lbServe.setText("  인분(N/A g)  ");
		tServe.setText("1");
	}
}
