package net;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import codes.Protocol;

/**
 * EventListener packet processing IoHandler, is passed.
 * @author k2d
 */
public interface ServerEventListener {
	
	/**
	 * Receive event protocols
	 * @param protocol
	 * @param session
	 * @param message
	 */
	public void eventReceive(Protocol protocol, IoSession session, IoBuffer message);
}
