import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameServer implements Runnable, Constants{
	// private String playerData;
	// private DatagramSocket serverSocket = null;
	// protected GameState game;
	// private int playerCount=0, gameStage=WAITING_FOR_PLAYERS;


	public GameServer(){

	// 	try{
	// 		serverSocket = new DatagramSocket(PORT);
	// 		serverSocket.setTimeout(100);
	// 	} catch (IOException e) {
 //            System.err.println("Could not listen on port: " + PORT);
 //            System.exit(-1);
	// 	}catch(Exception e){
	// 		e.printStackTrace();
	// 	}

	// 	game = new GameState();

	// 	t.start();
	}

	// public void broadcast(String msg){
		
	// }

	// public void send(Player player, String msg){
	// 	DatagramPacket packet;	
	// 	byte buf[] = msg.getBytes();		
	// 	packet = new DatagramPacket(buf, buf.length, player.getAddress(),player.getPort());
	// 	try{
	// 		serverSocket.send(packet);
	// 	}catch(IOException ioe){
	// 		ioe.printStackTrace();
	// 	}
	// }

	public void run(){
		// while(true){
		// 	byte[] buf = new byte[256];
		// 	DatagramPacket packet = new DatagramPacket(buf, buf.length);
		// 	try{
  //    			serverSocket.receive(packet);
		// 	}catch(Exception e){
		// 		e.printStackTrace();
		// 	}

		// 	playerData = new String(buf);
		// 	playerData = playerData.trim();

		// 	switch(gameStage){
		// 		case WAITING_FOR_PLAYERS:
		// 			break;
		// 		case GAME_START:
		// 			break;
		// 		case IN_PROGRESS:
		// 			break;
		// 		case GAME_END:
		// 			break;
		// 	}
		// }
	}

	// public static void main(String[] args) {
	// 	if(args.length !=1){
	// 		System.out.println("Usage: java -jar circlewars-server <number of players>");
	// 		System.exit(1);
	// 	}

	// 	new GameServer(Integer.parseInt(args[0]));
	// }
}