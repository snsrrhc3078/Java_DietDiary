package com.dietdiary.components;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class DayCell extends MyCell{

	public DayCell(double width, double height, String label) {
		super(width, height);
		JLabel lb = new JLabel(label);
		lb.setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, 20));
		lb.setHorizontalAlignment(JLabel.CENTER);
		setLayout(new BorderLayout());
		add(lb);
	}

}
