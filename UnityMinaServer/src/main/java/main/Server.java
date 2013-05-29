package main;

import java.io.IOException;
import java.net.InetSocketAddress;

import log.Log;
import net.GameController;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * GameServer
 * 
 * @author kkd
 */
public class Server {
	private SocketAcceptor acceptor = null;
	private GameController controller = null;
	private int port= 9090;

	public void start() throws IOException{
		start(port);
	}

	public void start(int port) {
		try{
			this.port = port;
	
			controller = GameController.get();
	
			//mina Asynchronous SocketAcceptor
			acceptor = new NioSocketAcceptor();
	
			//IoHandler 
			acceptor.setHandler(controller.getHandler());
	
			//logging filter
			acceptor.getFilterChain().addLast("com.kkd.game", new LoggingFilter());
	
			//send, receive Buffer setting
			acceptor.getSessionConfig().setSendBufferSize(8*1024);
			acceptor.getSessionConfig().setReceiveBufferSize(8*1024);
			//The session idle 30 seconds, specify the delay time longer than 30 seconds to log out.
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
	
			//binding
			acceptor.bind(new InetSocketAddress(port));

			Log.game.info("Server started on {} port",port);
		}catch(IOException e){
			Log.game.debug("Server start fail : {} ",e.toString());
		}
	}

	public void stop(){
		acceptor.dispose();
		Log.game.info("Server stoped ");
	}

//	public static void main(String args[]){
//		try{
//			new Server().start();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
}