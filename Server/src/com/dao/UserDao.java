package com.dao;

import static common.JDBCTemplate.Close;
import static common.JDBCTemplate.Commit;
import static common.JDBCTemplate.Rollback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vo.User;

public class UserDao implements UserSql {
	private Connection conn;

	public UserDao(Connection conn) {
		this.conn = conn;
	}

	// IP Confirmation
	public int getIP(String ip) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int cnt = 0;

		try {
			pstm = conn.prepareStatement(select_checkIP);
			pstm.setString(1, ip);
			System.out.println("Login IP : " + ip);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			Close(rs);
			Close(pstm);
		}
		return cnt;
	}

	// Nickname Confirmation
	public String getNickname(String ip) {
		PreparedStatement pstm = null;
		ResultSet res = null;
		String nickname = "";
		try {
			pstm = conn.prepareStatement(select_nickname);
			pstm.setString(1, ip);
			res = pstm.executeQuery();
			while (res.next()) {
				nickname = res.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			Close(res);
			Close(pstm);
		}
		return nickname;
	}

	// Insert User
	public int getInsertAll(User user) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			pstm = conn.prepareStatement(insertAll);

			pstm.setString(1, user.getIp());
			pstm.setString(2, user.getNickname());

			res = pstm.executeUpdate();

			if (res > 0) {
				Commit(conn);
			}

		} catch (Exception e) {
			Rollback(conn);

		} finally {
			Close(pstm);
		}

		return res;
	}

//	public User getNickIP(String ip) {
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		User usernick = null;
////		String vo = null;
//
//		try {
//			pstm = conn.prepareStatement(select_nickname);
//			pstm.setString(1, ip);
//
//			rs = pstm.executeQuery();
//			while (rs.next()) {
//				usernick = new User(rs.getString(1), rs.getString(2));
//			}
//		} catch (SQLException e) {
//			System.out.println(e.toString());
//		} finally {
//			Close(rs);
//			Close(pstm);
//		}
//
//		return usernick;
//	}
}