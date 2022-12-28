package com.dietdiary.domain;

public class History {
	private int history_idx;
	private DietDiaryMembers dietDiaryMembers;
	private int year;
	private int month;
	private int day;
	private int total_calories;
	private int total_carbs;
	private int total_proteins;
	private int total_fats;
	public int getHistory_idx() {
		return history_idx;
	}
	public void setHistory_idx(int history_idx) {
		this.history_idx = history_idx;
	}
	public DietDiaryMembers getDietDiaryMembers() {
		return dietDiaryMembers;
	}
	public void setDietDiaryMembers(DietDiaryMembers dietDiaryMembers) {
		this.dietDiaryMembers = dietDiaryMembers;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getTotal_calories() {
		return total_calories;
	}
	public void setTotal_calories(int total_calories) {
		this.total_calories = total_calories;
	}
	public int getTotal_carbs() {
		return total_carbs;
	}
	public void setTotal_carbs(int total_carbs) {
		this.total_carbs = total_carbs;
	}
	public int getTotal_proteins() {
		return total_proteins;
	}
	public void setTotal_proteins(int total_proteins) {
		this.total_proteins = total_proteins;
	}
	public int getTotal_fats() {
		return total_fats;
	}
	public void setTotal_fats(int total_fats) {
		this.total_fats = total_fats;
	}
	
	
}
