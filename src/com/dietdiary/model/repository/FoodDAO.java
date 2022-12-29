package com.dietdiary.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dietdiary.domain.Food;
import com.dietdiary.domain.History;
import com.dietdiary.util.DBManager;

// Food의 CRUD를 담당하는 DAO
public class FoodDAO {
	DBManager dbManager = DBManager.getInstance();
	public int insert(Food food) throws SQLException{
		int result = 0;
		PreparedStatement pst = null;
		StringBuilder sql = new StringBuilder(); 
		sql.append("INSERT INTO FOOD(FOOD_IDX, HISTORY_IDX, NAME, BRAND, CALORIES, CARBS, PROTEINS, FATS, REGYEAR, SERVESIZE, QUANTITY)");
		sql.append(" VALUES (SEQ_FOOD.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		pst = dbManager.getConnection().prepareStatement(sql.toString());
		/*
			1 int HISTORY_IDX,
		 	2 String NAME, 
		 	3 String BRAND, 
		 	4 int CALORIES, 
		 	5 int CARBS, 
		 	6 int PROTEINS, 
		 	7 int FATS, 
		 	8 String REGYEAR, 
		 	9 String SERVESIZE, 
		 	10 double QUANTITY
		 */
		pst.setInt(1, food.getHistory().getHistory_idx());
		pst.setString(2, food.getName());
		pst.setString(3, food.getBrand());
		pst.setInt(4, food.getCalories());
		pst.setInt(5, food.getCarbs());
		pst.setInt(6, food.getProteins());
		pst.setInt(7, food.getFats());
		pst.setString(8, food.getRegyear());
		pst.setString(9, food.getServeSize());
		pst.setDouble(10, food.getQuantity());
		result = pst.executeUpdate();
		dbManager.release(pst);

		return result;
	}
	public List<Food> selectAllByFK(int history_idx){
		List<Food> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM FOOD");
		sql.append(" WHERE HISTORY_IDX=? ORDER BY FOOD_IDX ASC");
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql.toString());
			pst.setInt(1, history_idx);
			rs = pst.executeQuery();
			while(rs.next()) {
				History history = new History();
				history.setHistory_idx(rs.getInt("HISTORY_IDX"));
				
				Food food = new Food();
				food.setFood_idx(rs.getInt("FOOD_IDX"));
				food.setHistory(history);
				food.setName(rs.getString("NAME"));
				food.setBrand(rs.getString("BRAND"));
				food.setCalories(rs.getInt("CALORIES"));
				food.setCarbs(rs.getInt("CARBS"));
				food.setProteins(rs.getInt("PROTEINS"));
				food.setFats(rs.getInt("FATS"));
				food.setRegyear(rs.getString("REGYEAR"));
				food.setServeSize(rs.getString("SERVESIZE"));
				food.setQuantity(rs.getDouble("QUANTITY"));
				list.add(food);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst, rs);
		}
		return list;
	}
	public Food selectByPKForUpdate(int food_idx) {
		Food food = null;
		String sql = "SELECT * FROM FOOD WHERE FOOD_IDX=? FOR UPDATE WAIT 5";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			pst.setInt(1, food_idx);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				History history = new History();
				history.setHistory_idx(rs.getInt("HISTORY_IDX"));
				
				food = new Food();
				food.setFood_idx(rs.getInt("FOOD_IDX"));
				food.setHistory(history);
				food.setName(rs.getString("NAME"));
				food.setBrand(rs.getString("BRAND"));
				food.setCalories(rs.getInt("CALORIES"));
				food.setCarbs(rs.getInt("CARBS"));
				food.setProteins(rs.getInt("PROTEINS"));
				food.setFats(rs.getInt("FATS"));
				food.setRegyear(rs.getString("REGYEAR"));
				food.setServeSize(rs.getString("SERVESIZE"));
				food.setQuantity(rs.getDouble("QUANTITY"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst, rs);
		}
		return food;
	}
	public int delete(int food_idx) {
		int result = 0;
		String sql = "DELETE FROM FOOD WHERE FOOD_IDX=?";
		PreparedStatement pst = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			pst.setInt(1, food_idx);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public int update(Food food) {
		int result = 0;
		String sql = "UPDATE FOOD SET QUANTITY=? WHERE FOOD_IDX=?";
		PreparedStatement pst = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			pst.setDouble(1, food.getQuantity());
			pst.setInt(2, food.getFood_idx());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst);
		}
		return result;
	}

}
