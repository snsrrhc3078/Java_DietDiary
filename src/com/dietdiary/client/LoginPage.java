package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

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
public class LoginPage extends Page{
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;
	
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
		
		pFormWrapper.repaint();
		
		add(lbTitle);
		add(pFormWrapper);
	}
	
}
