package net;

import protocols.PUserLogin;
import codes.Protocol;

public class ServerEventFactory {

	public static ServerEventAbstract getEvent(Protocol protocol) {

		if(protocol == Protocol.USER_LOGIN) return new PUserLogin();

		return null;
	}

	
	
}
