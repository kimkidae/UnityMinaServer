package net;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import codes.Protocol;
import codes.RCode;

/**
 * Top abstract class that handles the packet received from the network
 * Should be used when generating server protocol unconditionally inherit.
 * @author k2d
 */
public abstract class ServerEventAbstract {
	protected IoSession session;
	protected RCode hResult = RCode.SUCESS;

	//Processing Flow  : ServerIoHandler -> ServerEventListener -> [ServerEventAbstract] 
	public void set(IoSession session, IoBuffer buffer){
		this.session = session; 
		receivePacket(buffer);
		eventProcess();
	}

	//Transfer protocol to the client
	public void send(){
		session.write(makePacket());
	}

	//Transfer protocol to the session received
	public void send(IoSession session){
		session.write(makePacket());
	}

	//protocol settings
	public abstract Protocol getProtocol();
	//Processing of the received packet
	public abstract void receivePacket(IoBuffer buffer);
	//event processing
	public abstract void eventProcess();
	//return the packet to be sent to the client
	public abstract IoBuffer makePacket();
}