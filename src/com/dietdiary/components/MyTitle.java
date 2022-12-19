package com.dietdiary.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

//DietDiary의 title을 정의할 클래스
public class MyTitle extends JLabel{
	
	/**
	 * @param label 타이틀에 들어갈 문자열
	 * @param comp 타이틀의 들어갈 컴포넌트의 크기를 구할 컴포넌트 객체
	 * @param ratio 타이틀이 차지할 컴포넌트에 대한 크기 비율
	 */
	public MyTitle(String label, JComponent comp, int ratio) {

		setText(label);
		int compWidth = comp.getPreferredSize().width;
		int compHeight = comp.getPreferredSize().height;
		setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, 45));
		setPreferredSize(new Dimension(compWidth, compHeight/ratio));
		setHorizontalAlignment(JLabel.CENTER);
	}
}
