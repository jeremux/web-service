package client;

import interf.SiteItf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import message.Message;

/**
 * Classe utilisée pour envoyer un
 * message à un noeud d'arbre/graphe
 * @author Jeremy FONTAINE jeremy.fontaine@live.fr
 */
public class Client
{
	/**
	 * Envoie de message client
	 * @param args 
	 * 			args[0] nom de l'hote destinataire
	 * 			args[1] port destinataire
	 * 			[args[2]] message 
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException
	{
		String  hote    = args[0];
		int     port    = Integer.parseInt(args[1]);
		String  message = "Hello world !";
		
		if(args.length > 2)
			message = args[2];
		
		Registry reg  = LocateRegistry.getRegistry(hote, port);
		SiteItf  site = (SiteItf) reg.lookup("RMI");
		int      flag = site.genereFlag();
	
		Message  msg  = new Message(message, flag);
	
		site.transfert(msg);
	}
}
