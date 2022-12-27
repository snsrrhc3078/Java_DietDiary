package com.dietdiary.domain;

public class Food {
	private int food_idx;
	private History history;
	private String name;
	private String brand;
	private int calories;
	private int carbs;
	private int proteins;
	private int fats;
	private String regyear;
	private String serveSize;
	private double quantity;
	public int getFood_idx() {
		return food_idx;
	}
	public void setFood_idx(int food_idx) {
		this.food_idx = food_idx;
	}
	public History getHistory() {
		return history;
	}
	public void setHistory(History history) {
		this.history = history;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}
	public int getCarbs() {
		return carbs;
	}
	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}
	public int getProteins() {
		return proteins;
	}
	public void setProteins(int proteins) {
		this.proteins = proteins;
	}
	public int getFats() {
		return fats;
	}
	public void setFats(int fats) {
		this.fats = fats;
	}
	public String getRegyear() {
		return regyear;
	}
	public void setRegyear(String regyear) {
		this.regyear = regyear;
	}
	public String getServeSize() {
		return serveSize;
	}
	public void setServeSize(String serveSize) {
		this.serveSize = serveSize;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	
	
}
