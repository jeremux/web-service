package serveur;

import java.rmi.RemoteException;

import message.Message;
import interf.SiteItf;

public class TransferThread extends Thread
{
	private SiteItf destination;
	private Message msg;
	
	public TransferThread(SiteItf d,Message m)
	{
		msg         = m;
		destination = d;
	}
	
	public void run()
	{
		try
		{
			System.out.println("Envoyer: \""+this.msg.getContenu()+"\" [a "+this.destination.affiche()+"]");
			destination.transfert(msg);
		}
		catch (RemoteException e)
		{
			System.err.println("Erreur transfert.");
			e.printStackTrace();
		}
	}
}
