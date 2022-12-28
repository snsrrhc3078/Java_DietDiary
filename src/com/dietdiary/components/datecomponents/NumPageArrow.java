package com.dietdiary.components.datecomponents;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.dietdiary.client.date.SearchSidePage;

public class NumPageArrow extends JLabel {

	int direction;
	public static final int PREV = 0;
	public static final int NEXT = 1;
	
	/**
	 * 
	 * @param label 라벨의 문자열
	 * @param searchSidePage 부모페이지를 참조하기 위한 객체
	 * @param direction PREV, NEXT
	 */
	public NumPageArrow(String label, int direction) {
		super(label);
		this.direction = direction;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(Color.BLACK);
			}
		});
	}
}
