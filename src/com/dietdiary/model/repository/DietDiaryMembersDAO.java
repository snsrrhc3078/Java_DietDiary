package com.dietdiary.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dietdiary.domain.DietDiaryMembers;
import com.dietdiary.util.DBManager;

//Diet_Diary_Members 테이블의 DAO
public class DietDiaryMembersDAO {
	DBManager dbManager = DBManager.getInstance();
	public int insert(DietDiaryMembers member) {
		int result = 0;
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO DIET_DIARY_MEMBERS(DIET_DIARY_MEMBERS_IDX, ID, PASS, NAME, QUESTION, ANSWER)");
		sql.append(" values(SEQ_DIET_DIARY_MEMBERS.NEXTVAL, ?, ?, ?, ?, ?)");
		
		try {
			pst = dbManager.getConnection().prepareStatement(sql.toString());
			pst.setString(1, member.getId());
			pst.setString(2, member.getPass());
			pst.setString(3, member.getName());
			pst.setInt(4, member.getQuestion());
			pst.setString(5, member.getAnswer());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst);
		}
		return result;
	}
	public DietDiaryMembers selectByIDAndPass(DietDiaryMembers member) {
		DietDiaryMembers result = null;
		String sql = "SELECT * FROM DIET_DIARY_MEMBERS";
		sql += " WHERE ID=? AND PASS=?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			pst.setString(1, member.getId());
			pst.setString(2, member.getPass());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				result = new DietDiaryMembers();
				result.setDiet_diary_members_idx(rs.getInt("DIET_DIARY_MEMBERS_IDX"));
				result.setId(rs.getString("ID"));
				result.setPass(rs.getString("PASS"));
				result.setName(rs.getString("NAME"));
				result.setRegdate(rs.getString("REGDATE"));
				result.setQuestion(rs.getInt("QUESTION"));
				result.setAnswer(rs.getString("ANSWER"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst, rs);
		}
		
		return result;
	}
	public DietDiaryMembers selectByID(String ID) {
		DietDiaryMembers result = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM DIET_DIARY_MEMBERS WHERE ID=?";
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			pst.setString(1, ID);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				result = new DietDiaryMembers();
				result.setDiet_diary_members_idx(rs.getInt("DIET_DIARY_MEMBERS_IDX"));
				result.setId(rs.getString("ID"));
				result.setPass(rs.getString("PASS"));
				result.setName(rs.getString("NAME"));
				result.setRegdate(rs.getString("REGDATE"));
				result.setQuestion(rs.getInt("QUESTION"));
				result.setAnswer(rs.getString("ANSWER"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst, rs);
		}
		return result;
	}
	public int updatePassword(DietDiaryMembers dietDiaryMembers) {
		int result = 0;
		String sql = "UPDATE DIET_DIARY_MEMBERS SET PASS=?, NAME=?, QUESTION=?, ANSWER=? WHERE DIET_DIARY_MEMBERS_IDX=?";
		PreparedStatement pst = null;
		try {
			pst = dbManager.getConnection().prepareStatement(sql);
			/*
			 	1 String PASS
			 	2 String NAME
			 	3 int QUESTION
			 	4 String ANMSWER
			 	5 int DIET_DIARY_MEMBERS_IDX
			 */
			pst.setString(1, dietDiaryMembers.getPass());
			pst.setString(2, dietDiaryMembers.getName());
			pst.setInt(3, dietDiaryMembers.getQuestion());
			pst.setString(4, dietDiaryMembers.getAnswer());
			pst.setInt(5, dietDiaryMembers.getDiet_diary_members_idx());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pst);
		}
		return result;
	}
}
