package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import message.Message;

public interface SiteItf extends Remote 
{
	
	public String  affiche() throws RemoteException;

	public boolean aRecu(int flag) throws RemoteException;

	public void    ajouteFils(SiteItf enfant) throws RemoteException;

	public void    fixeParent(SiteItf parent) throws RemoteException;

	public int     genereFlag() throws RemoteException;

	public void    transfert(Message message) throws RemoteException;
	
}
