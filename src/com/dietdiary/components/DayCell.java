package com.dietdiary.components;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class DayCell extends MyCell{
	JLabel label;
	public DayCell(double width, double height, String label) {
		super(width, height);
		this.label = new JLabel(label);
		this.label.setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, 20));
		this.label.setHorizontalAlignment(JLabel.CENTER);
		setLayout(new BorderLayout());
		add(this.label);
	}

	public JLabel getLabel() {
		return label;
	}
}
