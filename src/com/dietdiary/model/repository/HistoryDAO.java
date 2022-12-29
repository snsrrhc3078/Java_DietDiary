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
	 * selectByFKAndDates 메서드에서 동시성 문제를 해결하기 위해 for update구문을 추가한 메서드
	 * @param history 레코드를 탐색하기 위해 primary key와 1:1 대응되는 컬럼들을 가지고 있는 객체. DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY는 반드시 값이 존재햐아 함
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
	 * @param history 레코드를 탐색하기 위해 primary key와 1:1 대응되는 컬럼들을 가지고 있는 객체 DIET_DIARY_MEMBERS_IDX, YEAR, MONTH, DAY는 반드시 값이 존재햐아 함
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
	
	/**
	 * 
	 * @param history 레코드를 update하기 위해 탐색할 기준인 history_idx와 total_calories, total_carbs, total_proteins, total_fats값을 가진 DTO
	 * @return 쿼리 실행에 성공 했다면 1 이상의 정수, 실패했다면 정수 0을 반환
	 */
	public int update(History history) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE HISTORY SET TOTAL_CALORIES=?, TOTAL_CARBS=?, TOTAL_PROTEINS=?, TOTAL_FATS=?");
		sql.append(" WHERE HISTORY_IDX=?");
		PreparedStatement pst = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql.toString());
			/*
			 	1 int HISTORY SET TOTAL_CALORIES, 
			 	2 int TOTAL_CARBS, 
			 	3 int TOTAL_PROTEINS, 
			 	4 int TOTAL_FATS,
			 	5 int HISTORY_IDX	
			 */
			pst.setInt(1, history.getTotal_calories());
			pst.setInt(2, history.getTotal_carbs());
			pst.setInt(3, history.getTotal_proteins());
			pst.setInt(4, history.getTotal_fats());
			pst.setInt(5, history.getHistory_idx());
			
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst);
		}
		return result;
	}
	/**
	 * 
	 * @param history 레코드를 delete하기 위해 탐색할 기준인 history_idx를 가진 DTO
	 * @return 쿼리 실행에 성공 했다면 1 이상의 정수, 실패했다면 정수 0을 반환
	 */
	public int delete(History history) {
		int result = 0;
		String sql = "DELETE FROM HISTORY WHERE HISTORY_IDX=?";
		PreparedStatement pst = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			pst.setInt(1, history.getHistory_idx());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst);
		}
		return result;
	}

}
