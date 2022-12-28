package com.dietdiary.domain;

//DiatDiaryMembers 테이블의 DTO
public class DietDiaryMembers {
	private int diet_diary_members_idx;
	private String id;
	private String pass;
	private String name;
	private String regdate;
	private int question;
	private String answer;
	public int getDiet_diary_members_idx() {
		return diet_diary_members_idx;
	}
	public void setDiet_diary_members_idx(int diet_diary_members_idx) {
		this.diet_diary_members_idx = diet_diary_members_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
