package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import com.main.MainApp;
import com.view.LoginController;
import com.view.WaitingRoomController;
import com.vo.Data;
import com.vo.GameStatus;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;

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
					
					break;
				case GAME_CHAR:
					break;
					
				case LOBBY_CHAT:
					break;
				case RANKING:
					break;
				default:
					System.out.println("error");
					break;

				}	
				
			}//while end 
			
			
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


	public void sendData(Data requestData) {
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