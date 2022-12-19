package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyInputForm;
import com.dietdiary.components.MyTitle;

public class JoinPage extends Page implements ActionListener{
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;
	
	List<MyButton> buttons;
	
	String[] questions = {
			"당신이 좋아하는 음식은?",
			"당신이 졸업한 초등학교는?",
			"당신이 좋아하는 동물은?"
	};
	
	public static final int ID = 0;
	public static final int PASS = 1;
	public static final int Name = 2;
	public static final int SIGN_UP = 0;
	public static final int BACK = 1;
	
	public JoinPage(DietDiaryMain main) {
		super(main);
		
		lbTitle = new MyTitle("Sign Up", this, 3);
		pFormWrapper = new MyFormWrapper(this, 4/5.0, 0.5);
		pInputForm = new MyInputForm();
		pButtonForm = new MyButtonForm();
		
		//폼 필드 작성
		pInputForm.addFormField("ID", 10, 20);
		pInputForm.addFormPasswordField("Pass", 10, 20);
		pInputForm.addFormField("Name", 10, 20);
		pInputForm.addCombobox(questions, 10, 20);
		
		//버튼 필드 작성
		pButtonForm.addMyButton("Sign Up");
		pButtonForm.addMyButton("Back");
		
		pFormWrapper.add(pInputForm);
		pFormWrapper.add(pButtonForm, BorderLayout.SOUTH);		
		
		add(lbTitle);
		add(pFormWrapper);
		
		//버튼들 가져와서 이벤트 추가
		 buttons = pButtonForm.getButtons();
		
		 for(int i =0;i<buttons.size();i++) {
			 buttons.get(i).addActionListener(this);
		 }
	}

	public void regist() {
		System.out.println("regist");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(buttons.get(SIGN_UP))) {
			regist();
		}else if(obj.equals(buttons.get(BACK))) {
			main.showHide(DietDiaryMain.LOGIN_PAGE);
		}
	}
}
