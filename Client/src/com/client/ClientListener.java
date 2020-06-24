package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.main.MainApp;
import com.view.DrawController;
import com.view.LoginController;
import com.view.SideAnswerController;
import com.view.SideColorPickerController;
import com.view.WaitingRoomController;
import com.vo.Data;
import com.vo.Game;
import com.vo.GameStatus;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;
import com.vo.UserStatus;

import javafx.application.Platform;
import javafx.scene.paint.Color;

public class ClientListener implements Runnable {
	private String serverIP;
	private String clientIP;
	private int serverPORT;
	private String nickname;
	private static Socket socket;
	private ArrayList<User> userList;
//	private ArrayList<Room> roomList;
	private boolean flag;
	private Thread listener;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;
	private LoginController login;
	private static ClientListener instance;
	private static MainApp mainApp;
	private static Room room;
	

	public ClientListener() {
		instance = this;

	}

	public static ClientListener getInstance() {
		return instance;
	}

	public void createConnect(String serverIP, int serverPORT, String nickname, MainApp mainApp) {
		try {
			socket = new Socket(serverIP, serverPORT); // create client's socket
			oos = new ObjectOutputStream(socket.getOutputStream()); // send data to server socket
			ois = new ObjectInputStream(socket.getInputStream()); // receive data from server socket

			clientIP = socket.getLocalAddress().toString();

			User user = new User(clientIP, nickname);
			Data client = new Data(user);
			client.setStatus(Status.CONNECTED);

			oos.writeObject(client);
			System.out.println("is connected the server socket");

		} catch (IOException e) {
			e.printStackTrace();
		}

//		//thread 
		listener = new Thread(this);
		listener.start();
	}

	public void startHandler() {
		// start a network handler thread
		Thread t = new Thread(() -> networkHandler(socket));
		t.start();
	}

	public void networkHandler(Socket s) {

		try {
			Data response;
			Status status;
			boolean visited = false;
			while (!flag) {

				response = (Data) ois.readObject();

				status = response.getStatus();

				switch (status) {
				case CONNECTED:
					// 현재 접속 유저
					// List<User> nowUserList = user.getUserList();
					System.out.println("WaitingRoomController - login!! ");

					break;
				case INCORRECT:
					System.out.println("loginController - try again ");
					break;

				case DISCONNECTION:

					endConnect();
					flag = true;
					break;

				case PLAYING: // game view update
					System.out.println("game playing GameController");
					System.out.println("Turn : "+DrawController.getInstance().getTurnOver());
					if (response.getChallenger() == null&&response.getDrawer()==null) {
						visited = true;
						sendData(response);
					} else {
						String loginUser = LoginController.getInstance().getPlayerName();
						System.out.println("[ login nickname : " + loginUser + " , challenger : "
								+ response.getChallenger() + ", drawer : " + response.getDrawer() + " ] ");
						
						Game game = new Game(response.getWord(), response.getGameUserList());
						game.setChallenger(response.getChallenger());
						game.setDrawer(response.getDrawer());
						
						//UI update - 공통 
						MainApp.switchToGame(game);	
						
					}

					break;
					
				case PRESSED:
					System.out.println("PRESSED");
					System.out.println(response.getColor());
		
					DrawController.getInstance().setPressedData(response);
					
					break;
					
				case DRAGGED:
					System.out.println("DRAGGED");
					System.out.println(response.getColor());
			
					DrawController.getInstance().setDraggedData(response);
					DrawController.getInstance().freeDrawing();
					
//					Data requestData = new Data();
//			        requestData.setStatus(Status.DRAWING);
//			        requestData.setOldX(response.getLastX());
//			        requestData.setOldY(response.getLastY());
//			        
//			        ClientListener.getInstance().sendData(requestData);
					
					break;
					
				case DRAWING:
					System.out.println("DRAWING : "+response.getLineW());
					DrawController.getInstance().setGc();
					DrawController.getInstance().freeDrawing(response);
					break;
					
				case GAME_CHAT:
					break;

				case LOBBY_CHAT:
					break;
				case RANKING:
					break;
				default:
					System.out.println("error");
					break;

				}

			} // while end

		} catch (IOException | ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			endConnect();
		}
	}

	// update UI according to the message from server
	@Override
	public void run() {
	}

	public void endConnect() {
		try {
			socket.close();
			oos.close();
			ois.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void sendData(Data requestData) {
		try {

			System.out.println();
			oos.writeObject(requestData);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}