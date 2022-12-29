package com.dietdiary.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.domain.History;
import com.dietdiary.util.DBManager;

//History의 CRUD를 담당하는 DAO
public class HistoryDAO {
	DBManager dbManager = DBManager.getInstance();
	
	/**
	 * 
	 * @param history 탐색할 primary key 를 가지고 있는 객체 DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY는 반드시 값이 존재햐아 함
	 * @return 값이 존재할경우 해당하는 History 객체 반환, 존재하지 않을경우 null
	 */
	public History selectByFKAndDatesForUpdate(History history) {
		History result = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM HISTORY WHERE DIET_DIARY_MEMBERS_IDX=? AND YEAR=? AND MONTH=? AND DAY=? FOR UPDATE WAIT 5";
		
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			/*
			 * 	1 int DIET_DIARY_MEMBERS_IDX
			 	2 int YEAR
			 	3 int MONTH
			 	4 int DAY
			 */
			pst.setInt(1, history.getDietDiaryMembers().getDiet_diary_members_idx());
			pst.setInt(2, history.getYear());
			pst.setInt(3, history.getMonth());
			pst.setInt(4, history.getDay());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				result = new History();
				
				DietDiaryMembers diaryMembers = new DietDiaryMembers();
				diaryMembers.setDiet_diary_members_idx(rs.getInt("DIET_DIARY_MEMBERS_IDX"));
				
				result.setHistory_idx(rs.getInt("HISTORY_IDX"));
				result.setDietDiaryMembers(diaryMembers);
				result.setYear(rs.getInt("YEAR"));
				result.setMonth(rs.getInt("MONTH"));
				result.setDay(rs.getInt("DAY"));
				result.setTotal_calories(rs.getInt("TOTAL_CALORIES"));
				result.setTotal_carbs(rs.getInt("TOTAL_CARBS"));
				result.setTotal_proteins(rs.getInt("TOTAL_PROTEINS"));
				result.setTotal_fats(rs.getInt("TOTAL_FATS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst, rs);
		}
		return result;
	}
	/**
	 * 
	 * @param history 탐색할 primary key 를 가지고 있는 객체 DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY는 반드시 값이 존재햐아 함
	 * @return 값이 존재할경우 해당하는 History 객체 반환, 존재하지 않을경우 null
	 */
	public History selectByFKAndDates(History history) {
		History result = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM HISTORY WHERE DIET_DIARY_MEMBERS_IDX=? AND YEAR=? AND MONTH=? AND DAY=?";
		
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			/*
			 * 	1 int DIET_DIARY_MEMBERS_IDX
			 	2 int YEAR
			 	3 int MONTH
			 	4 int DAY
			 */
			pst.setInt(1, history.getDietDiaryMembers().getDiet_diary_members_idx());
			pst.setInt(2, history.getYear());
			pst.setInt(3, history.getMonth());
			pst.setInt(4, history.getDay());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				result = new History();
				
				DietDiaryMembers diaryMembers = new DietDiaryMembers();
				diaryMembers.setDiet_diary_members_idx(rs.getInt("DIET_DIARY_MEMBERS_IDX"));
				
				result.setHistory_idx(rs.getInt("HISTORY_IDX"));
				result.setDietDiaryMembers(diaryMembers);
				result.setYear(rs.getInt("YEAR"));
				result.setMonth(rs.getInt("MONTH"));
				result.setDay(rs.getInt("DAY"));
				result.setTotal_calories(rs.getInt("TOTAL_CALORIES"));
				result.setTotal_carbs(rs.getInt("TOTAL_CARBS"));
				result.setTotal_proteins(rs.getInt("TOTAL_PROTEINS"));
				result.setTotal_fats(rs.getInt("TOTAL_FATS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst, rs);
		}
		return result;
	}
	
	public int insert(History history) throws SQLException{
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO HISTORY(HISTORY_IDX, DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY)");
		sql.append(" VALUES (SEQ_HISTORY.NEXTVAL, ?, ?, ?, ?)");
		PreparedStatement pst = null;
		pst = dbManager.getConnection().prepareStatement(sql.toString());
		/*
		 	1 int DIET_DIARY_MEMBERS_IDX, 
		 	2 int YEAR, 
		 	3 int MONTH, 
		 	4 int DAY
		 */
		pst.setInt(1, history.getDietDiaryMembers().getDiet_diary_members_idx());
		pst.setInt(2, history.getYear());
		pst.setInt(3, history.getMonth());
		pst.setInt(4, history.getDay());
		result = pst.executeUpdate();
		dbManager.release(pst);
		
		return result;
	}
}
