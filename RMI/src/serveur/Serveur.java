package serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interf.SiteItf;
import interf.TreeItf;

public class Serveur
{

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException, UnknownHostException
	{
		
		System.out.println(InetAddress.getLocalHost().toString());
		SiteItf site       = null;
		String  nom        = "RMI";
		String  hoteParent = "";
		int     portParent = -1;
		
		int port = Integer.parseInt(args[0]);
		
		if(args.length > 1)
			site = new SiteImpl(args[1]);
		else
			site = new SiteImpl();

		Registry reg = LocateRegistry.createRegistry(port);
		reg.bind(nom, site);

		if(args.length > 3)
		{
			
			hoteParent += args[2];
			portParent  = Integer.parseInt(args[3]);
			System.out.println("On va chercher dans "+hoteParent+":"+portParent);
			Registry regP = LocateRegistry.getRegistry(hoteParent,portParent);
			TreeItf parent = (TreeItf) regP.lookup(nom);
			parent.ajouteFils((TreeItf) site);
		}
		
	}
}
