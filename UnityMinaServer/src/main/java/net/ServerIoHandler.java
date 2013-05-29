package net;

import log.Log;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import codes.Protocol;

/**
 * Mina FrameWork IoHandler
 * Session creation, open, closed, send and receive messages, IDLE, and exception handling
 * Processing Flow : [ServerIoHandler] -> ServerEventListener -> ServerEventAbstract
 * @author k2d
 *
 */
public class ServerIoHandler extends IoHandlerAdapter {

	private ServerEventListener listener;

	/**
	 * event listener registration
	 * @param listener
	 */
	public void setServerEventListener(ServerEventListener listener){
		this.listener = listener;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		GameController.get().removeUser(session);
		Log.game.debug("Current Login Users : {}", GameController.get().getLoginUserCount());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		GameController.get().removeUser(session);
		Log.game.debug("Current Login Users : {}", GameController.get().getLoginUserCount());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		IoBuffer buffer = (IoBuffer)message;

		//Protocol Analysis
		Protocol protocol = Protocol.getProtocol(buffer.getShort());

		if(protocol == Protocol.NULL){
			Log.game.debug("Protocol that was passed is not suitable for.{}", protocol);
			session.close(false);
			return;
		}
		//for Session Check
		if(protocol == Protocol.CHECK){
			return;
		}

		listener.eventReceive(protocol, session, buffer);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub

	}

}
