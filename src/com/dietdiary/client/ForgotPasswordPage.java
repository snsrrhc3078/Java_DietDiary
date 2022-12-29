package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyInputForm;
import com.dietdiary.components.MyTitle;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.util.StringUtil;

public class ForgotPasswordPage extends Page implements ActionListener{

	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;
	
	String[] questions = { "당신이 좋아하는 음식은?", "당신이 졸업한 초등학교는?", "당신이 좋아하는 동물은?" };
	
	List<MyButton> buttons;
	List<JComponent> comps;
	List<JTextField> fields;
	
	public static final int ID = 0;
	public static final int QUESTION = 1;
	
	public static final int FIND = 0;
	public static final int BACK = 1;
	
	public ForgotPasswordPage(DietDiaryMain main) {
		super(main);
		
		lbTitle = new MyTitle("Finding Password", this, 3);
		pFormWrapper = new MyFormWrapper(this, 4 / 5.0, 0.5 * 1/2);
		pInputForm = new MyInputForm();
		pButtonForm = new MyButtonForm();
		
		// 폼 필드 작성
		pInputForm.addFormField("ID", 10, 20);
		pInputForm.addCombobox(questions, 10, 20);
		
		// 버튼 필드 작성
		pButtonForm.addMyButton("Find");
		pButtonForm.addMyButton("Back");
		
		pFormWrapper.add(pInputForm);
		pFormWrapper.add(pButtonForm, BorderLayout.SOUTH);
		
		add(lbTitle);
		add(pFormWrapper);
		
		// 컴포넌트들 가져오기
		buttons = pButtonForm.getButtons();
		comps = pInputForm.getComps(); //주제 컴포넌트 리스트 가져오기
		fields = pInputForm.getFields(); //텍스트필드 리스트 가져오기
		
		// 이벤트 추가
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).addActionListener(this);
		}
	}
	
	public void findPass() {
		DietDiaryMembers result = main.membersDAO.selectByID(fields.get(ID).getText());
		
		if(result == null) {
			JOptionPane.showMessageDialog(main, "해당하는 계정이 없습니다");
			clearForm();
		}else {
			int findingQuestion =  ((JComboBox<String>)comps.get(QUESTION)).getSelectedIndex();
			String findingAnswer = StringUtil.getConvertedPassword(fields.get(QUESTION).getText());
			
			if(result.getQuestion() == findingQuestion && findingAnswer.equals(result.getAnswer())) {
				JOptionPane.showMessageDialog(main, "성공입니다");
				clearForm();
				ChangePasswordPage page = (ChangePasswordPage)main.getPages().get(DietDiaryMain.CHANGE_PASSWORD_PAGE);
				page.getAccount(result);
				main.showHide(DietDiaryMain.CHANGE_PASSWORD_PAGE);
				page.getFields().get(ChangePasswordPage.PASS).grabFocus();
			}else {
				JOptionPane.showMessageDialog(main, "틀렸습니다");
			}
		}
	}

	public void clearForm() {
		((JComboBox<String>)comps.get(QUESTION)).setSelectedIndex(0);
		for(int i =0;i<fields.size();i++) {
			fields.get(i).setText("");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(buttons.get(FIND))) {
			findPass();
		} else if (obj.equals(buttons.get(BACK))) {
			SignInPage page = (SignInPage)main.getPages().get(DietDiaryMain.SIGN_IN_PAGE);
			page.clearForm();
			main.showHide(DietDiaryMain.SIGN_IN_PAGE);
			clearForm();
		}
	}
	public List<JTextField> getFields() {
		return fields;
	}

}
