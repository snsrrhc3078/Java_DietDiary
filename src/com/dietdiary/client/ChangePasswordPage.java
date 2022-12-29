package com.dietdiary.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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

public class ChangePasswordPage extends Page implements ActionListener{
	
	JLabel lbTitle;
	JPanel pFormWrapper;
	MyInputForm pInputForm;
	MyButtonForm pButtonForm;
	
	List<MyButton> buttons;
	List<JComponent> comps;
	List<JTextField> fields;
	
	public static final int PASS = 0;
	public static final int PASS_CONFIRM = 1;
	public static final int OK = 0;
	public static final int BACK = 1;
	
	DietDiaryMembers members;
	
	public ChangePasswordPage(DietDiaryMain main) {
		super(main);
		
		lbTitle = new MyTitle("Change Password", this, 3);
		pFormWrapper = new MyFormWrapper(this, 4 / 5.0, 0.5 * 1/2);
		pInputForm = new MyInputForm();
		pButtonForm = new MyButtonForm();
		
		// 폼 필드 작성
		pInputForm.addFormPasswordField("Pass", 10, 20);
		pInputForm.addFormPasswordField("Pass Comfirm", 10, 20);
		
		// 버튼 필드 작성
		pButtonForm.addMyButton("OK");
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
	
	public void changePass() {
		if(fields.get(PASS).getText().equals(fields.get(PASS_CONFIRM).getText())) {
			String encodedPass =  StringUtil.getConvertedPassword((String.valueOf(((JPasswordField)fields.get(PASS)).getPassword())));
			members.setPass(encodedPass);
			
			SignUpPage page = (SignUpPage)main.getPages().get(DietDiaryMain.SIGN_UP_PAGE);
			int result = page.update(members);
			
			if(result>0) {
				JOptionPane.showMessageDialog(main, "비밀번호 변경 완료");
				clearForm();
			}else {
				JOptionPane.showMessageDialog(main, "비밀번호 변경 실패");
				clearForm();
			}
			main.showHide(DietDiaryMain.SIGN_IN_PAGE);
		}else {
			JOptionPane.showMessageDialog(main, "비밀번호와 비밀번화 확인이 동일하지 않습니다");
		}
	}
	
	public void clearForm() {
		for(int i =0;i<fields.size();i++) {
			fields.get(i).setText("");
		}
		members = null;
	}
	
	public void getAccount(DietDiaryMembers members) {
		this.members = members;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(buttons.get(OK))) {
			changePass();
		} else if (obj.equals(buttons.get(BACK))) {
			main.showHide(DietDiaryMain.SIGN_IN_PAGE);
			clearForm();
		}
	}
	
	public List<JTextField> getFields() {
		return fields;
	}

}
