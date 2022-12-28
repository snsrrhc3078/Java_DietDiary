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
}
