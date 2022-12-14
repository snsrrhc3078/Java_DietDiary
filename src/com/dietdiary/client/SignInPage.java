package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

//다이어리 로그인하는 페이지 클래스
public class SignInPage extends Page implements ActionListener {
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;

	List<MyButton> buttons;
	List<JComponent> comps;
	List<JTextField> fields;

	public static final int ID = 0;
	public static final int PASS = 1;
	public static final int SIGN_IN = 0;
	public static final int SIGN_UP = 1;
	public static final int FORGOT_PASSWORD = 2;

	public SignInPage(DietDiaryMain main) {
		super(main);

		lbTitle = new MyTitle("Sign In", this, 3);
		pFormWrapper = new MyFormWrapper(this, 4 / 5.0, 0.5 * 3 / 4);
		pInputForm = new MyInputForm();
		pButtonForm = new MyButtonForm();

		// 폼 필드 작성
		pInputForm.addFormField("ID", 10, 20);
		pInputForm.addFormPasswordField("Pass", 10, 20);

		// 버튼 필드 작성
		pButtonForm.addMyButton("Sign In");
		pButtonForm.addMyButton("Sign Up");
		pButtonForm.addMyButton("Forgot Password");

		pFormWrapper.add(pInputForm);
		pFormWrapper.add(pButtonForm, BorderLayout.SOUTH);

		add(lbTitle);
		add(pFormWrapper);

		// 컴포넌트들 가져오기
		buttons = pButtonForm.getButtons();
		comps = pInputForm.getComps(); // 주제 컴포넌트 리스트 가져오기
		fields = pInputForm.getFields(); // 텍스트필드 리스트 가져오기

		// 이벤트 추가
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).addActionListener(this);
		}

	}

	public void signIn() {
		
		if(emptyWarning()) return;
		
		DietDiaryMembers member = new DietDiaryMembers();
		member.setId(fields.get(ID).getText());
		String encordedPass =  StringUtil.getConvertedPassword((String.valueOf(((JPasswordField)fields.get(PASS)).getPassword())));
		member.setPass(encordedPass);
		
		
		member = main.membersDAO.selectByIDAndPass(member);
		//만약 유저가 존재한다면
		if(member!=null) {
			JOptionPane.showMessageDialog(main, "환영합니다 " + member.getName() + " 님!");
			clearForm();
			main.setSignIn(true);
			main.setLoginedUserInfo(member);
			DiaryPage page = (DiaryPage)main.getPages().get(DietDiaryMain.DIARY_PAGE);
			page.getSignIn();;
			main.showHide(DietDiaryMain.DIARY_PAGE);
		}else {
			JOptionPane.showMessageDialog(main, "존재하거나 일치하지 않는 아이디 혹은 패스워드 입니다");
			fields.get(PASS).setText("");
			fields.get(PASS).grabFocus();
		}
	}
	
	public boolean emptyWarning() {
		if(fields.get(SIGN_IN).getText().equals("")) {
			JOptionPane.showMessageDialog(main, "아이디를 입력해주세요");
			fields.get(SIGN_IN).grabFocus();
			return true;
		}else if(fields.get(PASS).getText().equals("")) {
			JOptionPane.showMessageDialog(main, "비밀번호를 입력해주세요");
			fields.get(PASS).grabFocus();
			return true;
		}
		
		return false;
	}

	public void clearForm() {
		for(int i =0;i<fields.size();i++) {
			fields.get(i).setText("");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj.equals(buttons.get(SIGN_IN))) {
			signIn();
		} else if (obj.equals(buttons.get(SIGN_UP))) {
			SignUpPage page = (SignUpPage)main.getPages().get(DietDiaryMain.SIGN_UP_PAGE);
			main.showHide(SIGN_UP);
			page.getFields().get(SignUpPage.ID).grabFocus();
		} else if (obj.equals(buttons.get(FORGOT_PASSWORD))) {
			ForgotPasswordPage page = (ForgotPasswordPage)main.getPages().get(DietDiaryMain.FORGOT_PASSWORD_PAGE);
			main.showHide(DietDiaryMain.FORGOT_PASSWORD_PAGE);
			page.getFields().get(ForgotPasswordPage.ID).grabFocus();
		}
	}

}
