package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vo.Data;
import com.vo.Game;
import com.vo.GameStatus;
import com.vo.Status;
import com.vo.User;

public class Server {
	private boolean serverStarted = false;
	private static List<User> userList; // 소켓 킨 유저들 목록

	boolean exit = false;
	String userip = "";
	private static List<ClientHandler> clientList;
	private static Map<ClientHandler, String> clientMap;
	private static Map<String, ObjectOutputStream> nowPlayers;
	private static List<Data> buffer;
	private static Queue<Game> que;
	private static final int THREAD_CNT = 9; // 최대 스레드 개수
	private static ExecutorService threadPool;// 스레드풀
	private static ServerSocket serverSocket;
	private static int gameCount;
	private static int turnCount;
	private static Data startGameData;
	private static Map<String, Integer> scoreMap;
	private static GameSession gameSession;

	public static void init() {
//		threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		threadPool = Executors.newFixedThreadPool(THREAD_CNT);
		userList = new ArrayList<User>();
		clientList = new Vector<Server.ClientHandler>();
		clientMap = new LinkedHashMap<Server.ClientHandler, String>();
		buffer = new Vector<Data>();
		nowPlayers = new LinkedHashMap<String, ObjectOutputStream>();
		que = null;
		scoreMap = new HashMap<String, Integer>();
		gameCount = 0;
		turnCount = 0;

	}

	public static void Start(int port) {
//		if(clientList.size()==8) serverStarted = true; // 방에 8명 있으면 서버 시작 안함 

//		if (serverStarted) {
//			System.out.println("Server has already started!");
//			return;
//		}
		init();

//		clientList = new Vector<Server.ClientHandler>();
		try {
			serverSocket = new ServerSocket(port);

		} catch (IOException e1) {
			stopServer();
			return;
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				System.out.println("Server Start!!");
				while (true) {
					try {
						Socket socket = serverSocket.accept();// Client 수락
						String userip = socket.getInetAddress().toString().replace("/", "");
						System.out.println("[ " + userip + "가 접속했습니다 : " + Thread.currentThread().getName() + " ]");

						// 여기서 로그인

						// 로그인 성공시, 클라이언트 스레드 생성
						ClientHandler clientHandler = new ClientHandler(userip, socket);
						clientList.add(clientHandler);

						System.out.println("Client 개수 " + clientList.size());
						threadPool.submit(clientHandler);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if (!serverSocket.isClosed())
							stopServer();
						break;
					} // try~catch end
				} // while end
			}
		}; // runnable
//		
		threadPool.submit(runnable);

	}// Start() end

	public static void stopServer() { // 서버 종료 시 호출
		try {
			// 모든 소켓 닫기
			Iterator<ClientHandler> iterator = clientList.iterator();
			while (iterator.hasNext()) {
				ClientHandler client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			// 서버 소켓 닫기
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			// 스레드풀 종료
			if (threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
			System.out.println("[서버 멈춤]");
		} catch (Exception e) {
		}
	}// stopServer() end

	public static class ClientHandler implements Runnable {
		String userName;
		Socket socket;
		String ip;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Data data;
		List<String> playerList;
		Queue<String> drawers;

		boolean exit = false;
		int check = 0;

		public ClientHandler() {

		}

//		public ClientHandler(String ip, Socket socket, ObjectOutputStream oos ) {
//			System.out.println("[ First to get in/out stream of client socket. ]");
//			this.socket = socket;
//			this.ip = ip;
//			try {
//				ois = new ObjectInputStream(socket.getInputStream());
//				oos = new ObjectOutputStream(socket.getOutputStream());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("[ Failed to get in/out stream of client socket. ]");
//			} // try~catch end
//		}

		public ClientHandler(String ip, Socket socket) {
			System.out.println("[ First to get in/out stream of client socket. ]");
			this.socket = socket;
			this.ip = ip;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("[ Failed to get in/out stream of client socket. ]");
			} // try~catch end
		}

		@Override
		public void run() { // 클라이언트 요청이 서버에게 오는 곳입니다.

			while (!exit) {

				try {
					System.out.println("[Client's request: " + socket.getRemoteSocketAddress() + ": "
							+ Thread.currentThread().getName() + "]");
					data = (Data) ois.readObject();
					Status state = data.getStatus();
					String nowNickname = data.getNickname();
					System.out.println(state);
					switch (state) {
					case CONNECTED:
						System.out.println("Lobby");
//						System.out.println("check : "+(gameCount++));
						playerList = new Vector<String>();

						if (clientList.size() != clientMap.size()) {
							clientMap.put(this, data.getNickname());
						}
						for (Map.Entry<ClientHandler, String> enty : clientMap.entrySet()) {
							System.out.println("client nickname : " + enty.getValue());
							playerList.add(enty.getValue());
						}
						data.setGameUserList(playerList);
						broadCasting();
						break;

					case READY:
						gameCount++;

						if (clientList.size() > 1 && gameCount == clientList.size()) {
							// 게임 시작
							gameCount = 0;
							playerList = new Vector<String>();
							for (Map.Entry<ClientHandler, String> enty : clientMap.entrySet()) {
								System.out.println("client nickname : " + enty.getValue());
								playerList.add(enty.getValue());
							}
//							que = new LinkedList<Game>();
//							que.addAll(new GameSession().createGameQue(playerList)); // 전체 게임 큐 생성

							gameSession = new GameSession(playerList);
							gameSession.createGameQue();
							data.setStatus(Status.PLAYING); // 모두 플레잉
							data.setGameUserList(playerList);// 현재 접속한 유저 리스트

							broadCasting();
						}

						break;

					case PLAYING:

						GameStatus gameStatus = data.getGameStatus();

						if (!gameSession.getTurn()) {
							System.out.println("[ 게임 끝 ]");
							
							// 랭킹 구현
						} else {

							if (!gameSession.getDrawerQue().isEmpty()) { //한 turn 
						
								if (gameStatus == null)	gameStatus = GameStatus.START;
								switch (gameStatus) {
								case TURN:
									System.out.println("Turn");
									gameSession.getDrawerQue().poll();
									if(gameSession.getDrawerQue().isEmpty()) {
										//유저가 맞출 수 있는 시간을 더 줘야함 
										gameSession.getGameQue().poll();
										gameSession.getTurn();
										System.out.println("[ 턴 하나 끝 ]");
									}
									data = gameSession.getGameData();
									broadCasting();
									break;
							

								case START:
									data = gameSession.getGameData();
									sendToClient();
									break;

								default:
									break;
								}//switch
								
							}//else end 

							

						}

						break;
					case PRESSED:

						broadCasting();
						break;
						
					case DRAGGED:
			
						broadCasting();
						break;
						
					case DRAWING:
						startGameData.setOldX(data.getOldX());
						startGameData.setOldY(data.getOldY());
						startGameData.setStatus(Status.DRAWING);
						broadCasting(startGameData);
						break;

					case LOBBY_CHAT:

						break;

					case RANKING:

						break;

					case DISCONNECTION:
						System.out.println("DISCONNECTED");
						clientList.remove(ClientHandler.this);
						broadCasting();

						try {
							stopServer();
							exit = true;
						} catch (Exception e) {
							e.printStackTrace();
						} // try~catch end

						break;

					default:
						System.out.println("error");
						break;

					}

				} catch (Exception e) {
					try {
						exit = true;
						e.printStackTrace();
						clientList.remove(ClientHandler.this);
						socket.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} // try~catch end

			} // while end

		}// run() end

		public void progressGameTurn(String challenger, String word) {

		}

		public void sendToClient() {
			for (Map.Entry<ClientHandler, String> entry : clientMap.entrySet()) {
				if (entry.getKey().equals(this)) {
					try {
						System.out.println("[ Server send data to " + entry.getValue() + " ]");
						entry.getKey().oos.writeObject(data);
						entry.getKey().oos.flush();
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} // for end

		}// sendToClient end
		
		public void broadCasting(Data data) {// 전체 데이터를 보내줌
			// 전체 채팅 회원에게 내용 출력
			for (ClientHandler client : clientList) {
				try {
					System.out.println("[ Server send data to " + clientMap.get(client) + " ]");
					client.oos.writeObject(data);
					client.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} // for end
		}// broadCasting() end

		public void broadCasting() {// 전체 데이터를 보내줌
			// 전체 채팅 회원에게 내용 출력
			for (ClientHandler client : clientList) {
				try {
					System.out.println("[ Server send data to " + clientMap.get(client) + " ]");
					client.oos.writeObject(data);
					client.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} // for end
		}// broadCasting() end

	}// ClientHandler end
}// Server end
