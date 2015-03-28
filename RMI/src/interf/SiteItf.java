package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SiteItf extends Remote 
{
	public void affiche() throws RemoteException;
	
	public boolean aRecu(int flag) throws RemoteException;
	
	public void ajouteFils() throws RemoteException;
	
	public void fixeParent() throws RemoteException;
	
	public void genereFlag() throws RemoteException;
	
}
