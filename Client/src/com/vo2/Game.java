package com.vo2;

import java.util.List;

import javafx.scene.paint.Color;

public class Game {
	String word; // ���þ�
	Color color; // Colorpicker.getValue() �� ���Ϲ޴� ��
	double lineW; // Slider.getValue()�� ���Ϲ���
	String challenger; // ���ߴ� ���. �� ǥ��
	String drawer; // �׸� �׸��� ���. �� ǥ��
	List<String> gameUserList; // ��������Ʈ
	double startX;
	double startY;
	double oldX;
	double oldY;
	double lastX;
	double lastY;
	public Game() {
		super();
	}
public Game(String word, List<String> gameUserList) {
	super();
	this.word = word;
	this.gameUserList = gameUserList;
}
	public Game(String word, String challenger, String drawer, List<String> gameUserList) {
		super();
		this.word = word;
		this.challenger = challenger;
		this.drawer = drawer;
		this.gameUserList = gameUserList;
	}
	public Game(Color color, double lineW, double startX, double startY) {
		super();
		this.color = color;
		this.lineW = lineW;
		this.startX = startX;
		this.startY = startY;
	}
	public Game(double oldX, double oldY, double lastX, double lastY) {
		super();
		this.oldX = oldX;
		this.oldY = oldY;
		this.lastX = lastX;
		this.lastY = lastY;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
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
}
