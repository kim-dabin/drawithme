package com.vo2;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int userNo;
	String ip;
	String nickname;
	String score;
	Date regdate;
	String word;
	String color; // Ư�̻��� �Ʒ� ������ ����
	double lineW;
	String challenger;
	String drawer;
	List<String> gameUserList;
	double startX;
	double startY;
	double oldX;
	double oldY;
	double lastX;
	double lastY;
	String message; // Data ��ü�� ���� �ִ� ��1
	Status status; // Data ��ü�� ���� �ִ� ��2
	GameStatus gameStatus; // Data ��ü�� ���� �ִ� ��3
	UserStatus userStatus; // Data ��ü�� ���� �ִ� ��4
	public Data() {
		super();
	}
	public Data(User user) {
		super();
		this.userNo = user.getNo();
		this.ip = user.getIp();
		this.nickname = user.getNickname();
		this.score = user.getScore();
		this.regdate = user.getRegdate();
	}
	public Data(Game game) {
		super();
		this.word = game.getWord();
		// javafx.scene.paint.Color�� toString()�ϸ� hex web value�� ���ڿ��� �޾ƿ� �� ����
		this.color = game.getColor().toString(); 
		this.lineW = game.getLineW();
		this.challenger = game.getChallenger();
		this.drawer = game.getDrawer();
		this.gameUserList = game.getGameUserList();
		this.startX = game.getStartX();
		this.startY = game.getStartY();
		this.oldX = game.getOldX();
		this.oldY = game.getOldY();
		this.lastX = game.getLastX();
		this.lastY = game.getLastY();
	}
	public Data(User user, Game game) {
		super();
		this.userNo = user.getNo();
		this.ip = user.getIp();
		this.nickname = user.getNickname();
		this.score = user.getScore();
		this.regdate = user.getRegdate();
		this.word = game.getWord();
		this.color = game.getColor().toString();
		this.lineW = game.getLineW();
		this.challenger = game.getChallenger();
		this.drawer = game.getDrawer();
		this.gameUserList = game.getGameUserList();
		this.startX = game.getStartX();
		this.startY = game.getStartY();
		this.oldX = game.getOldX();
		this.oldY = game.getOldY();
		this.lastX = game.getLastX();
		this.lastY = game.getLastY();
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getLineW() {
		return lineW;
	}
	public void setLineW(double lineW) {
		this.lineW = lineW;
	}
	public String getChallenger() {
		return challenger;
	}
	public void setChallenger(String challenger) {
		this.challenger = challenger;
	}
	public String getDrawer() {
		return drawer;
	}
	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}
	public List<String> getGameUserList() {
		return gameUserList;
	}
	public void setGameUserList(List<String> gameUserList) {
		this.gameUserList = gameUserList;
	}
	public double getStartX() {
		return startX;
	}
	public void setStartX(double startX) {
		this.startX = startX;
	}
	public double getStartY() {
		return startY;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public double getOldX() {
		return oldX;
	}
	public void setOldX(double oldX) {
		this.oldX = oldX;
	}
	public double getOldY() {
		return oldY;
	}
	public void setOldY(double oldY) {
		this.oldY = oldY;
	}
	public double getLastX() {
		return lastX;
	}
	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public double getLastY() {
		return lastY;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
