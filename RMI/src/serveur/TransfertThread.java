package serveur;

import java.rmi.RemoteException;

import message.Message;
import interf.SiteItf;

/**
 * <b>TransfertThread est la classe représentant un transfert.</b>
 * <p>
 * Un objet TransfertThread est caractérisé par les informations suivantes :
 * <ul>
 * <li>Une destination</li>
 * <li>Un message</li>
 * </ul>
 * 
 * @author jeremy FONTAINE
 * @version 1.0
 */
public class TransfertThread extends Thread
{
	private SiteItf destination;
	private Message msg;
	
	/**
	 * Créé un nouvel objet TransfertThread
	 * @param d destination du transfert
	 * @param m message à transférer
	 */
	public TransfertThread(SiteItf d,Message m)
	{
		msg         = m;
		destination = d;
	}
	
	public void run()
	{
		try
		{
			System.out.println("On doit envoyer: \""+this.msg.getContenu()+"\" [a "+this.destination.affiche()+"]");
			destination.transfert(msg);
		}
		catch (RemoteException e)
		{
			System.err.println("Erreur transfert.");
			e.printStackTrace();
		}
	}
}
