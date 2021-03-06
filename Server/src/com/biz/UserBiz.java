package com.biz;

import static common.JDBCTemplate.*;

import java.sql.Connection;

import com.dao.UserDao;
import com.vo.User;

public class UserBiz {
	public int getSignUp(User user) {
		Connection conn = getConnection();
		int res = new UserDao(conn).getSignUp(user);
		Close(conn);
		return res;
	}
	
	public int getLogIn(User user) {
		Connection conn = getConnection();
		int res = new UserDao(conn).getLogIn(user);
		Close(conn);		
		return res;
	}
	
	public int ConfirmUserIP(String IP) {
		Connection conn = getConnection();
		
		int confirm = new UserDao(conn).getIP(IP);
		
		Close(conn);
		
		return confirm;
	}
	
	public String ConfirmUserNickname(String IP) {
		Connection conn = getConnection();
		
		String nickname = new UserDao(conn).getNickname(IP);
		
		Close(conn);
		
		return nickname;
	}
	
	public int getInsertAll(User user) {
		Connection conn = getConnection();
		int res = new UserDao(conn).getInsertAll(user);
		
		Close(conn);
		
		return res;
	}
	
	public static String getNickname(String ip) {
		Connection conn = getConnection();
		System.out.println("연결");
		String ret = new UserDao(conn).getNickname(ip);
		Close(conn);
		System.out.println("연결끝");
		return ret;
	}

	//

	// 닉네임, ip 모두 입력받아서 일치하는지 확인
	public static boolean getNickIp(User udata) {
		Connection conn = getConnection();
		User ret = new UserDao(conn).getNickIP(udata.getIp());
		if (ret.getNickname() == udata.getNickname()) {
			return true;
		} else {
			return false;
		}
	}


	
	public User getLoginUser(User user) {
		User loginUser = null;
		String ip = user.getIp();
		String nickname = user.getNickname();
		System.out.println("getLoginUser"+ip+" "+nickname);
		Connection conn = getConnection();
		int count = new UserDao(conn).getIP(ip);
		System.out.println("count : "+count);
		if (count > 0) {
			// 2. 있으면 닉네임 가져오기
			String nick = new UserDao(conn).getNickname(ip);
			System.out.println(nick);
			if (nick.equals(nickname)) {
				System.out.println("같다");
				// room list
				// 3. 있는데 닉네임 같으면 방리스트로
				 loginUser = new UserDao(conn).getNickIP(ip);
			} else {
				System.out.println("달라");
				// nickname
				// 4. 있는데 닉네임 다르면 다시로그인
				
			}
		} else {
			System.out.println("없을때");
			int cnt = new UserDao(conn).Insert_AllInfo(ip, nickname);
			System.out.println(cnt);
			// 5. 없으면 insert
			loginUser = new UserDao(conn).getNickIP(ip);
		}
		return loginUser;
		
	}
public static void CheckUser(String ip, String nickname) {
		Connection conn = getConnection();
		int count = new UserDao(conn).getIP(ip);
		if (count > 0) {
			String nick = new UserDao(conn).getNickname(ip);
			System.out.println(nick);
		} else {
			System.out.println("");
			int cnt = new UserDao(conn).Insert_AllInfo(ip, nickname);
			System.out.println(cnt);
			// 5. 쇰㈃ insert
		}

	}


	public static void main(String[] args) {
		String nickname = "쇰";
//		String nickname = "eunhye";
//		String ip = "192.168.0.249";
		String ip = "192.168.0.249";
//		String ip = "192.168.0.5";
		CheckUser(ip, nickname);
	}

	// ip 없으면 지금 입력한 닉네임, ip 입력

}
