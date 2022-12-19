package com.dietdiary.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MyButtonForm extends JPanel{
	ArrayList<MyButton> buttons = new ArrayList<>();
	
	public ArrayList<MyButton> getButtons() {
		return buttons;
	}
	
	public void addMyButton(String label) {
		MyButton bt = new MyButton(label);
		add(bt);
		buttons.add(bt);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	}
}
