package com.dietdiary.components.datecomponents;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class MyTextField extends JTextField{
	int arcSize;
	public MyTextField(JComponent comp, double widthRatio, double heightRatio) {
		int compWidth = comp.getPreferredSize().width;
		int compHeight = comp.getPreferredSize().height;
		setPreferredSize(new Dimension((int) (compWidth * widthRatio), (int) (compHeight * heightRatio)));
		arcSize = 25;
		setFont(new Font(getFont().getFontName(), Font.PLAIN, 15));
		setBorder(null);
//		addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				System.out.println(getText());
//			}
//		});
	}
}