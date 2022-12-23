package com.dietdiary.components.datecomponents;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class HistoryItem extends Item{
	JPanel pEast;
	MySideButton btDel;
	public HistoryItem(JComponent comp) {
		super(comp, 9/10.0, 1/10.0);
		
		pEast = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
			}
		};
		btDel = new MySideButton("X");
		pEast.add(btDel);
		add(pEast, BorderLayout.EAST);
	}

}
