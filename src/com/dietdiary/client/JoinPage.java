package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dietdiary.components.MyButton;
import com.dietdiary.components.MyButtonForm;
import com.dietdiary.components.MyFormWrapper;
import com.dietdiary.components.MyInputForm;
import com.dietdiary.components.MyTitle;
import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.util.StringUtil;

public class JoinPage extends Page implements ActionListener {
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;

	List<MyButton> buttons;
	List<JComponent> comps;
	List<JTextField> fields;

	String[] questions = { "당신이 좋아하는 음식은?", "당신이 졸업한 초등학교는?", "당신이 좋아하는 동물은?" };

	public static final int ID = 0;
	public static final int PASS = 1;
	public static final int NAME = 2;
	public static final int QUESTION = 3;
	public static final int SIGN_UP = 0;
	public static final int BACK = 1;

	public JoinPage(DietDiaryMain main) {
		super(main);

		lbTitle = new MyTitle("Sign Up", this, 3);
		pFormWrapper = new MyFormWrapper(this, 4 / 5.0, 0.5);
		pInputForm = new MyInputForm();
		pButtonForm = new MyButtonForm();

		// 폼 필드 작성
		pInputForm.addFormField("ID", 10, 20);
		pInputForm.addFormPasswordField("Pass", 10, 20);
		pInputForm.addFormField("Name", 10, 20);
		pInputForm.addCombobox(questions, 10, 20);

		// 버튼 필드 작성
		pButtonForm.addMyButton("Sign Up");
		pButtonForm.addMyButton("Back");

		pFormWrapper.add(pInputForm);
		pFormWrapper.add(pButtonForm, BorderLayout.SOUTH);

		add(lbTitle);
		add(pFormWrapper);

		// 컴포넌트들 가져오기
		buttons = pButtonForm.getButtons();
		comps = pInputForm.getComps(); //주제 컴포넌트 리스트 가져오기
		fields = pInputForm.getFields(); //텍스트필드 리스트 가져오기
		
		// 이벤트 추가
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).addActionListener(this);
		}
	}

	public void regist() {
		DietDiaryMembers members = new DietDiaryMembers();
		members.setId(fields.get(ID).getText());
		String encordedPass =  StringUtil.getConvertedPassword((String.valueOf(((JPasswordField)fields.get(PASS)).getPassword())));
		members.setPass(encordedPass);
		members.setName(fields.get(NAME).getText());
		members.setQuestion(((JComboBox<String>)comps.get(QUESTION)).getSelectedIndex());
		String encordedAnswer =  StringUtil.getConvertedPassword(fields.get(QUESTION).getText());		
		members.setAnswer(encordedAnswer);
		int result = main.membersDAO.insert(members);
		if(result > 0) {
			JOptionPane.showMessageDialog(main, "회원가입 완료");
			clearForm();
			main.showHide(DietDiaryMain.LOGIN_PAGE);
		}else {
			JOptionPane.showMessageDialog(main, "회원가입 실패");
		}
	}

	//회원가입 했으면 폼 초기화시키는 메서드
	public void clearForm() {
		((JComboBox<String>)comps.get(QUESTION)).setSelectedIndex(0);
		for(int i =0;i<fields.size();i++) {
			fields.get(i).setText("");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(buttons.get(SIGN_UP))) {
			regist();
		} else if (obj.equals(buttons.get(BACK))) {
			main.showHide(DietDiaryMain.LOGIN_PAGE);
		}
	}
}
