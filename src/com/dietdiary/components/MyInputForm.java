package com.dietdiary.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MyInputForm extends JPanel{
	
	//혹시 나중에 접근하게 될 경우를 위한 ArrayList
	ArrayList<JLabel> labels = new ArrayList<>();
	ArrayList<JTextField> fields = new ArrayList<>();
	
	/**
	 * MyInputForm에 JLabel과 JTextField를 추가한다
	 * 
	 * @param label FormField 의 라벨
	 * @param textFieldSize FormField의 가로 길이
	 * @param fontSize FormField의 폰트 크기
	 * 
	 */
	public void addFormField(String label, int textFieldSize,int fontSize) {
		JLabel lb = new JLabel(label);
		JTextField text = new JTextField(textFieldSize);
		Font font = new Font(text.getFont().getFontName(), Font.BOLD, fontSize);
		
		lb.setFont(font);
		text.setFont(font);
		lb.setPreferredSize(text.getPreferredSize());
		
		add(lb);
		add(text);
	}
	public void addFormPasswordField(String label, int passwordFieldSize,int fontSize) {
		JLabel lb = new JLabel(label);
		JPasswordField text = new JPasswordField(passwordFieldSize);
		Font font = new Font(text.getFont().getFontName(), Font.BOLD, fontSize);
		
		lb.setFont(font);
		text.setFont(font);
		lb.setPreferredSize(text.getPreferredSize());
		
		add(lb);
		add(text);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
	}

}
