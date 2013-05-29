package protocols;

import log.Log;
import net.GameController;
import net.Packet;
import net.ServerEventAbstract;

import org.apache.mina.core.buffer.IoBuffer;

import type.User;
import codes.Protocol;
import codes.RCode;

/**
 * User login protocol
 * 
 * @author k2d
 */
public class PUserLogin extends ServerEventAbstract {

	private int userId;
	private String email;
	private String password;

	@Override
	public Protocol getProtocol() {
		return Protocol.USER_LOGIN;
	}

	@Override
	public void receivePacket(IoBuffer buffer) {
		email = Packet.getString(buffer);
		password = Packet.getString(buffer);
		
		Log.game.debug("receive : email={}, password={}", email, password);
	}

	@Override
	public void eventProcess() {
		//Login temporary processing
		if(email.matches("test[0-9]") && password.equals("1234")){
			
			Log.game.debug("mached");
			userId = Integer.parseInt(email.substring(4));

			User user = new User();
			user.setUserId(userId);
			user.setEmail(email);
			user.setSession(session);

			//Duplicate login process.
			if(GameController.get().getLoginUsers().containsKey(userId)){
				Log.game.debug("Duplicate login User!! {}", userId, email);
				PDuplicateUser dupUser= new PDuplicateUser();
				dupUser.duplicateProcess(userId, user);

				return;
			}

			//User login process
			GameController.get().addLoginUser(userId, user);

			Log.game.debug("login users : {}", GameController.get().getLoginUserCount());
		}else {
			hResult = RCode.LOGIN_FAIL;
		}

		send();
	}

	@Override
	public IoBuffer makePacket() {
		Packet p = new Packet();
		p.addCode(getProtocol());
		p.addCode(hResult);

		return p.getData();
	}
}
