package com.dietdiary.client;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyInputForm;
import com.dietdiary.components.MyTitle;

public class JoinPage extends Page{
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;
	
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
		
		//버튼 필드 작성
		pButtonForm.addMyButton("Sign Up");
		pButtonForm.addMyButton("Back");
		
		pFormWrapper.add(pInputForm);
		pFormWrapper.add(pButtonForm, BorderLayout.SOUTH);		
		
		pFormWrapper.repaint();
		
		add(lbTitle);
		add(pFormWrapper);
	}
	
}
