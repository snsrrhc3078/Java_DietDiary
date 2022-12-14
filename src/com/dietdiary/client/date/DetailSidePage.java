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
import com.dietdiary.model.repository.FoodDAO;
import com.dietdiary.util.DBManager;

public class DetailSidePage extends SidePage {
	Food food; // dto

	MyFormWrapper nameForm;
	MyLabel lbFoodName;

	MyFormWrapper nutritionForm;
	MyLabel lbCarb, lbPro, lbFat, lbKcal, lbBrand, lbYear;
	MyLabel lbCarbValue, lbProValue, lbFatValue, lbKcalValue, lbBrandValue, lbYearValue;
	JPanel[] labelWrapper = new JPanel[6];

	MyFormWrapper quantityForm;
	MyLabel lbServe;
	JFormattedTextField tServe;

	MyFormWrapper registButtonForm;
	MySideButton btReg, btEdit;

	MyFormWrapper backButtonForm;
	MySideButton btBack;

	public static final int REGIST_BUTTON = 0;
	public static final int EDIT_BUTTON = 1;

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
		nameForm = new MyFormWrapper(this, 9 / 10.0, 0.75 / 10.0, 25);
		lbFoodName = new MyLabel("????????????", 17);

		// searcheSidePage??? lbTitle??? preferredSide??? ???????????? lbFoodName??? ???????????? ??????
		SearchSidePage page = (SearchSidePage) infoFrame.getSidePages().get(DateInfoFrame.SEARCH_SIDE_PAGE);
		Dimension d = page.getLbTitle().getPreferredSize();
		lbFoodName.setPreferredSize(d);

		nameForm.setLayout(new FlowLayout());

		nameForm.add(lbFoodName);
		add(nameForm);
	}

	public void createNutritionForm() {
		nutritionForm = new MyFormWrapper(this, 9 / 10.0, 4.5 / 10.0);
		nutritionForm.setLayout(new GridLayout(6, 1));

		lbCarb = new MyLabel("?????????", fontsize);
		lbPro = new MyLabel("?????????", fontsize);
		lbFat = new MyLabel("??????", fontsize);
		lbKcal = new MyLabel("?????????", fontsize);
		lbBrand = new MyLabel("?????????", fontsize);
		lbYear = new MyLabel("????????????", fontsize);
		lbCarbValue = new MyLabel("NNN g ", fontsize);
		lbProValue = new MyLabel("NNN g ", fontsize);
		lbFatValue = new MyLabel("NNN g ", fontsize);
		lbKcalValue = new MyLabel("NNNN kcal", fontsize);
		lbBrandValue = new MyLabel("SSSSSSSSSS", fontsize);
		lbYearValue = new MyLabel("YYYY ???", fontsize);

		for (int i = 0; i < labelWrapper.length; i++) {
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
		for (int i = 0; i < labelWrapper.length; i++) {
			nutritionForm.add(labelWrapper[i]);
		}

		add(nutritionForm);
	}

	public void createQuantityForm() {
		quantityForm = new MyFormWrapper(this, 9 / 10.0, 1.6 / 10.0);
		quantityForm.setLayout(new FlowLayout());
		lbServe = new MyLabel("  ??????(NNN g)  ", fontsize);
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
						if (!text.matches("\\d*(\\.\\d{0,1})?")) {
							tServe.setText(text.substring(0, text.length() - 1));
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tServe.getText().equals("")) {
						if (tServe.getText().charAt(0) == '.') {
							tServe.setText("0" + tServe.getText());
						}
					} else {
						tServe.setText("0");
					}
					setLabel();
				}
			}
		});
		tServe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!tServe.getText().equals("")) {
					if (tServe.getText().charAt(0) == '.') {
						tServe.setText("0" + tServe.getText());
					}
				} else {
					tServe.setText("0");
				}
				setLabel();
			}
		});
	}

	public void createRegistButtonForm() {
		registButtonForm = new MyFormWrapper(this, 9 / 10.0, 0.5 / 10.0, 25);
		btReg = new MySideButton("OK");
		btEdit = new MySideButton("Update");
		setRegistButton(REGIST_BUTTON);

		add(registButtonForm);

		btReg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registFood();
				infoFrame.showHide(DateInfoFrame.HISTORY_SIDE_PAGE);
				infoFrame.clearAllPages();
			}
		});

		btEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFood();
				infoFrame.showHide(DateInfoFrame.HISTORY_SIDE_PAGE);
				infoFrame.clearAllPages();
			}
		});
	}

	public void setRegistButton(int btnNo) {
		registButtonForm.removeAll();

		if (btnNo == REGIST_BUTTON) {
			registButtonForm.add(btReg);
		} else if (btnNo == EDIT_BUTTON) {
			registButtonForm.add(btEdit);
		}
	}

	public void createBackButtonForm() {
		backButtonForm = new MyFormWrapper(this, 9 / 10.0, 0.5 / 10.0, 25);
		btBack = new MySideButton("Back");
		backButtonForm.add(btBack);

		btBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBack();
				clearPage();
			}
		});

		add(backButtonForm);
	}

	public void getBack() {
		if (registButtonForm.getComponent(0) == btReg) {
			infoFrame.showHide(DateInfoFrame.SEARCH_SIDE_PAGE);
		} else if (registButtonForm.getComponent(0) == btEdit) {
			infoFrame.showHide(DateInfoFrame.HISTORY_SIDE_PAGE);
		}
	}

	// ?????? ?????????
	public void getDetail(Food food) {
		this.food = food;
		double quantity = food.getQuantity();

		// ?????? quantity??? ?????? ????????? ??????????????????(ex 2.0) tServe??? ????????? ?????? ???????????? ????????? ??????
		if ((double) (int) quantity == quantity) {
			tServe.setText(Integer.toString((int) quantity));
		} else {
			tServe.setText(Double.toString(quantity));
		}

		setLabel();
	}

	public void setLabel() {
		lbFoodName.setText(food.getName());
		lbKcalValue
				.setText(Integer.toString((int) (food.getCalories() * Double.parseDouble(tServe.getText()))) + " kcal");
		lbCarbValue.setText(Integer.toString((int) (food.getCarbs() * Double.parseDouble(tServe.getText()))) + " g");
		lbProValue.setText(Integer.toString((int) (food.getProteins() * Double.parseDouble(tServe.getText()))) + " g");
		lbFatValue.setText(Integer.toString((int) (food.getFats() * Double.parseDouble(tServe.getText()))) + " g");
		lbServe.setText("  ??????(" + food.getServeSize() + " g)  ");
		lbBrandValue.setText(food.getBrand());
		lbYearValue.setText(food.getRegyear() + " ???");
	}

	public void clearPage() {
		food = null;
		lbCarbValue.setText("0 g ");
		lbProValue.setText("0 g ");
		lbFatValue.setText("0 g ");
		lbKcalValue.setText("0 kcal");
		lbBrandValue.setText("");
		lbYearValue.setText("N/A ???");
		lbServe.setText("  ??????(N/A g)  ");
		tServe.setText("1");
	}

	// DAO ??????
	public void registFood() {
		HistorySidePage page = (HistorySidePage) infoFrame.getSidePages().get(DateInfoFrame.HISTORY_SIDE_PAGE);
		DBManager dbManager = infoFrame.main.getDbManager();
		boolean isCommited = false;
		try {
			dbManager.getConnection().setAutoCommit(false);

			// registHistory ??????????????? ????????? DTO ??????
			History history = page.registHistory(food.getHistory());
			// food??? history??? ???????????? ???????????? registHistory?????? ????????? History?????????
			// ????????? History ????????? ????????? ?????? ????????? foodDTO?????? insert ?????? ????????????
			food.setHistory(history);

			food.setQuantity(Double.parseDouble(tServe.getText()));
			int result = infoFrame.getFoodDAO().insert(food);

			// foodDAO??? insert?????? ????????? ???????????? throw
			if (result == 0) {
				throw new SQLException();
			}
			isCommited = true;

			// ?????? ??????????????? ?????????????????? commit
			dbManager.getConnection().commit();
		} catch (SQLException e) {
			// ???????????? ?????? ???????????? rollback
			try {
				dbManager.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			isCommited = false;
			e.printStackTrace();
		} finally {
			// ???????????? ???????????? autoCommit ????????????
			try {
				dbManager.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (!isCommited) {
			JOptionPane.showMessageDialog(infoFrame, "?????? ??????");
		}
	}

	public void deleteFood(int food_idx) {
		FoodDAO foodDAO = infoFrame.getFoodDAO();

		Food food = foodDAO.selectByPKForUpdate(food_idx);
		int result = 0;

		if (food == null) {
			JOptionPane.showMessageDialog(infoFrame, "?????? ???????????? ?????? ???????????????");
			return;
		} else {
			result = foodDAO.delete(food_idx);
		}

		if (result == 0) {
			JOptionPane.showMessageDialog(infoFrame, "?????? ??????");
		}
	}

	public void updateFood() {
		FoodDAO foodDAO = infoFrame.getFoodDAO();
		food = foodDAO.selectByPKForUpdate(food.getFood_idx());
		int result = 0;

		if (food == null) {
			JOptionPane.showMessageDialog(infoFrame, "???????????? ?????? ???????????????");
			return;
		} else {
			food.setQuantity(Double.parseDouble(tServe.getText()));
			result = foodDAO.update(food);
		}

		if (result == 0) {
			JOptionPane.showMessageDialog(infoFrame, "?????? ??????");
		}
	}

	public JFormattedTextField gettServe() {
		return tServe;
	}
}
