package com.dietdiary.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JComboBox;

public class StringUtil {
	//넘겨받은 숫자가 1자리 수 이면 앞에 0을 붙이기
	
	/**
	 *  @param n 문자열로 바꿀 정수
	 *  @return 매개변수의 값이 10 미만의 정수일경우 앞에 "0"을 추가하여 문자열 형태로 반환한다
	 * */
	public static String getNumString(int n) {
		String result;
		if(n<10) {
			result = "0" + n;
		}else {
			result = Integer.toString(n);
		}
		return result;
	}
	
	public static String getExtends(String fileName) {
		String result = fileName.substring(fileName.lastIndexOf(".")+1);
		return result;
	}
	
	/*
	 * 비밀번호 암호화
	 * 자바의 보안과 관련된 기능을 지원하는 api들이 모여있는 패키지가
	 * java.security 이다
	 */
	public static String getConvertedPassword(String pass) {
		StringBuffer hexString = null;
		try {
			//64글자의 hash를 출력하는 암호방식을 사용허눈 인스턴스를 가져온다
			MessageDigest digest= MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8"));
			
			//String은 불변이다 따라서 그 값이 변경될 수 없다
			//따라서 String 객체는 반복문 횟수가 클때는 절대
			//누적식을 사용해서는 안된다
			//해결책) 변경가능한 문자열 객체를 지원하는 StringBuffer
			//StringBuilder등을 활용하자
			
			hexString = new StringBuffer();
			for(int i =0;i<hash.length;i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) {					
					hexString.append("0");
				}
				hexString.append(hex);
			}
//			System.out.println(hexString);
//			System.out.println(hexString.length());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	
	/**
	 * 파일명 반환하기
	 * 
	 * @param String 확장자를 가져올 파일경로
	 * @return 현재 시스템 시간(ms단위)과 매개변수에 들어있는 파일경로의 확장자를
	 *             결합한 문자열 형태의 파일 경로를 반환한다
	 */
	public static String createFileName(String url) {
		//파일명 만들기
		long time = System.currentTimeMillis();
		String ext = StringUtil.getExtends(url.toString());
		String result = time + "." + ext;
		return result;
	}
	
	/**
	 * box에서 매개변수 item과 동일한 문자열을 가진 box의 item을 box에서
	 * selected 상태로 만드는 메서드 
	 * 
	 * @param box - 검색되어질 ComboBox 인스턴스
	 * @param item - 목표 String
	 */
	public static <E> void selectComboItem(JComboBox<E> box, String item) {
//		System.out.println(box.getItemAt(1));
		for(int i =1;i<box.getItemCount();i++) {
			String currentItem = (String)box.getItemAt(i);
			if(currentItem.equals(item)) {
				box.setSelectedIndex(i);
			}
		}
	}
	
	/*
	 * public static void main(String[] args) { //
	 * System.out.println(StringUtil.getExtends());
	 * //System.out.println("aa132".replaceAll("[1-9]", "")); }
	 * getConvertedPassword("minzino"); }
	 */
}
