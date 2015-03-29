package serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interf.GraphItf;
import interf.SiteItf;

public class ServeurGraph
{

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException, UnknownHostException
	{
		
		System.out.println(InetAddress.getLocalHost().toString());
		SiteItf site       = null;
		String  nom        = "RMI";
		String  hoteVoisin = "";
		int     portVoisin = -1;
		
		int port = Integer.parseInt(args[0]);
		
		if(args.length > 1)
			site = new GraphImpl(args[1]);
		else
			site = new GraphImpl();

		Registry reg = LocateRegistry.createRegistry(port);
		reg.bind(nom, site);

		if(args.length > 2)
		{
			String[] lesVoisins = args[2].split(",");
			for(String voisin: lesVoisins)
			{
				hoteVoisin = voisin.split(":")[0];
				portVoisin = Integer.parseInt(voisin.split(":")[1]);
				Registry regP = LocateRegistry.getRegistry(hoteVoisin,portVoisin);
				GraphItf v = (GraphItf) regP.lookup(nom);
				v.ajouteVoisin((GraphItf) site);
			}
				
		}
		
	}
}
