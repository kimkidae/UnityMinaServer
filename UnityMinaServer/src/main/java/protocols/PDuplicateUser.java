package protocols;

import java.util.Timer;
import java.util.TimerTask;

import net.GameController;
import net.Packet;
import net.ServerEventAbstract;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import type.User;
import codes.Protocol;
import codes.RCode;


/**
 * Handling Duplication login user
 * Pass the error message to pop out 
 * all the logged in user and user login later.
 * 
 * (Server send only protocol that occurs only on the server.)
 * 
 * @author k2d
 */
public class PDuplicateUser extends ServerEventAbstract {
	@Override
	public Protocol getProtocol() {
		return Protocol.DUPLICATE_LOGIN;
	}

	@Override
	public void receivePacket(IoBuffer buffer) {
	}

	@Override
	public void eventProcess() {
	}

	@Override
	public IoBuffer makePacket() {
		Packet p = new Packet();
		p.addCode(getProtocol());
		p.addCode(hResult);
		return p.getData();
	}

	/**
	 * Kick handle Duplication login user
	 * 
	 * @param User Id
	 * @param User Object
	 */
	public void duplicateProcess(int userId, User user){
		//Code of Duplication Login 
		hResult = RCode.DUPLICATE_LOGIN;

		//Kick the first logged-in user
		User preUser = GameController.get().getLoginUsers().remove(userId);
		if(preUser != null){
			IoSession preSession = preUser.getSession();
			//Duplicate login protocol transmission
			if(preSession != null) send(preSession);
			closeDuplicateUser(preSession);
		}

		//Kick users who log in later
		IoSession session = user.getSession();
		if(session != null) send(session);//중복로그인 프로토콜 전송
		closeDuplicateUser(session);
	}

	/**
	 * Close time intervals after the delivery of messages
	 * 
	 * @param Session to connect
	 */
	public void closeDuplicateUser(final IoSession session){
		Timer dupTimer = new Timer();
		dupTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(session != null) session.close(true);
			}
		}, 1000);
	}
	
}
