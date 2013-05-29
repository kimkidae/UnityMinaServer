package net;

import java.util.Hashtable;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import type.User;
import codes.Protocol;


/**
 * GameServer Basic Controller 
 * 
 * GameController is ServerEventListener and event processing passed from IoHandler as
 * Processing Flow: ServerIoHandler -> [ServerEventListener] -> ServerEventAbstract
 * @author k2d
 */
public class GameController implements ServerEventListener{

	private ServerIoHandler handler = new ServerIoHandler();//ioHandler
	private static GameController controller = null;//singleton object
	//Login User Lists, hashtable is Thread-safe Object
	private final Hashtable<Integer, User> loginUsers = new Hashtable<Integer, User>();

	//singleton constructor
	private GameController(){
		handler.setServerEventListener(this);
	}

	/**
	 * singleton pattern instance
	 * @return controller instance
	 */
	public static GameController get(){
		if(controller == null){
			synchronized(GameController.class){
				if(controller == null){
					controller = new GameController();
				}
			}
		}
		return controller;
	}

	/**
	 * Returning IoHandler
	 * @return ServerIoHandler
	 */
	public ServerIoHandler getHandler(){
		return handler;
	}

	/**
	 * Returning Login User list
	 * @return LoginUsers
	 */
	public Hashtable<Integer, User> getLoginUsers(){
		return loginUsers;
	}

	/**
	 * Add a login user
	 * @param User Id
	 * @param User Object
	 */
	public void addLoginUser(int userId, User user){
		loginUsers.put(userId, user);
		user.getSession().setAttribute("UserInfo",user);
	}

	/**
	 * except Logout User
	 * @param Logout User Session
	 */
	public void removeUser(IoSession session){
		User removeUser = (User)session.getAttribute("UserInfo");
		if(removeUser != null)
			loginUsers.remove(removeUser.getUserId());
		if(session != null) session.close(true);
	}

	/**
	 * Returning LoginUsers Count
	 * @return LoginUsers Count
	 */
	public int getLoginUserCount(){
		return loginUsers.size();
	}

	@Override
	public void eventReceive(Protocol protocol, IoSession session, IoBuffer buffer) {

		ServerEventAbstract event = ServerEventFactory.getEvent(protocol);
		event.set(session, buffer);
	}

}
