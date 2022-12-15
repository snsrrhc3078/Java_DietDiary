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

//다이어리 로그인하는 페이지 클래스
public class LoginPage extends Page{
	JLabel lbTitle, lbId, lbPass;
	JTextField tId;
	JPasswordField tPass;
	DiaryDefaultButton btLogin, btRegist, btFindPass;
	
	Page pWrapperForm;
	JPanel pInputForm, pbuttonForm;
	DiaryDefaultButton test;
	
	public LoginPage(DietDiaryMain main) {
		super(main);
		lbTitle = new JLabel("Login");
		lbId = new JLabel("ID");
		lbPass = new JLabel("Pass");
		tId = new JTextField(10);
		tPass = new JPasswordField(10);
		btLogin = new DiaryDefaultButton("Sign In");
		btRegist = new DiaryDefaultButton("sign Up");
		btFindPass = new DiaryDefaultButton("Fogot Password");

		pWrapperForm = new Page(main) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(new Color(255, 255, 128));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(new Color(224,184, 138));
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
			}
		};
		pInputForm = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
			}
		};
		pbuttonForm = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
			}
		};
		
		//디자인
		int parentWidth = getPreferredSize().width;
		
		lbTitle.setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, 45));
		lbTitle.setPreferredSize(new Dimension(parentWidth, 450/3));
		lbTitle.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font(tId.getFont().getFontName(), Font.BOLD, 20);
		lbId.setFont(font);
		lbPass.setFont(font);
		tId.setFont(font);
		tPass.setFont(font);
		lbId.setPreferredSize(tId.getPreferredSize());
		lbPass.setPreferredSize(tPass.getPreferredSize());
		
		pWrapperForm.setPreferredSize(new Dimension(parentWidth/5*4, 450/2/4*3));
		
		pWrapperForm.setLayout(new BorderLayout());
		
		
		//테스트
		test = new DiaryDefaultButton("Test");
		pbuttonForm.add(test);
		//조립
		add(lbTitle);
		pInputForm.add(lbId);
		pInputForm.add(tId);
		pInputForm.add(lbPass);
		pInputForm.add(tPass);
		pWrapperForm.add(pInputForm);
		
		pbuttonForm.add(btLogin);
		pbuttonForm.add(btRegist);
		pbuttonForm.add(btFindPass);
		
		pWrapperForm.add(pbuttonForm, BorderLayout.SOUTH);

		add(pWrapperForm);
	}
	
}
