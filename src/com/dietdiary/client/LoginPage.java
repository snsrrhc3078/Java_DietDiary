package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyInputForm;
import com.dietdiary.components.MyTitle;

//다이어리 로그인하는 페이지 클래스
public class LoginPage extends Page implements ActionListener{
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;
	
	List<MyButton> buttons;
	
	public static final int ID = 0;
	public static final int PASS = 1;
	public static final int LOG_IN = 0;
	public static final int SIGN_UP = 1;
	public static final int FORGOT_PASSWORD = 2;
	
	public LoginPage(DietDiaryMain main) {
		super(main);
		
		lbTitle = new MyTitle("Log In", this, 3);
		pFormWrapper = new MyFormWrapper(this, 4/5.0, 0.5 * 3/4);
		pInputForm = new MyInputForm();
		pButtonForm = new MyButtonForm();
		
		//폼 필드 작성
		pInputForm.addFormField("ID", 10, 20);
		pInputForm.addFormPasswordField("Pass", 10, 20);
		
		//버튼 필드 작성
		pButtonForm.addMyButton("Log In");
		pButtonForm.addMyButton("Sign Up");
		pButtonForm.addMyButton("Forgot Password");
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj.equals(buttons.get(LOG_IN))) {
			System.out.println("로그인 눌림");
		}else if(obj.equals(buttons.get(SIGN_UP))) {
			main.showHide(SIGN_UP);
		}else if(obj.equals(buttons.get(FORGOT_PASSWORD))) {
			System.out.println("비밀번호 찾기 눌림");
		}
	}
	
}
