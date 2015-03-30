package client;

import interf.SiteItf;

import java.rmi.AccessException;
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
	 * 		<p>	args[0] nom de l'hote destinataire
	 * 		<p>	args[1] port destinataire
	 * 		<p>	[args[2]] message
	 */
	public static void main(String[] args) 
	{
		String  hote    = args[0];
		int     port    = Integer.parseInt(args[1]);
		String  message = "Hello world !";
		
		if(args.length > 2)
			message = args[2];
		
		Registry reg = null;
		try
		{
			reg = LocateRegistry.getRegistry(hote, port);
		}
		catch (RemoteException e)
		{
			System.err.println("Erreur récupération d'un objet Registry sur "+hote+":"+port);
			e.printStackTrace();
		}
		
		SiteItf site = null;
		try
		{
			site = (SiteItf) reg.lookup("RMI");
		}
		catch (AccessException e)
		{
			System.err.println("Erreur d'acces, vous n'avez peut - être pas la permission");
			e.printStackTrace();
		}
		catch (RemoteException e)
		{
			System.err.println("Erreur RemoteException");
			e.printStackTrace();
		}
		catch (NotBoundException e)
		{
			System.err.println("Aucun assocation avec dans le registre");
			e.printStackTrace();
		}
		
		int flag = -1;
		try
		{
			flag = site.genereFlag();
		}
		catch (RemoteException e)
		{
			System.err.println("Problème pour generer flag");
			e.printStackTrace();
		}
	
		Message  msg  = new Message(message, flag);
	
		try
		{
			site.transfert(msg);
		}
		catch (RemoteException e)
		{
			System.err.println("Problème transfert de message");
			e.printStackTrace();
		}
	}
}
