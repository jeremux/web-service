package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import message.Message;
import serveur.SiteImpl;

public interface SiteItf extends Remote 
{
	
	public String affiche() throws RemoteException;
	
	public boolean aRecu(int flag) throws RemoteException;
	
	public void ajouteFils(SiteItf enfant) throws RemoteException;
	
	public void fixeParent(SiteImpl parent) throws RemoteException;
	
	public int genereFlag() throws RemoteException;
	
	public void transfert(Message message) throws RemoteException;
	
}
