package client;

import interf.SiteItf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import message.Message;

public  class   Client
{
	public static void main(String[] args) throws RemoteException, NotBoundException
	{
		String  hote    = args[0];
		int     port    = Integer.parseInt(args[1]);
		String  message = "toto";
		
		if(args.length > 2)
			message = args[2];
		
		Registry reg  = LocateRegistry.getRegistry(hote, port);
		SiteItf  site = (SiteItf) reg.lookup("RMI");
		int      flag = site.genereFlag();
	
		Message  msg  = new Message(message, flag);
		
		site.transfert(msg);
	}
}
