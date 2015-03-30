package serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interf.GraphItf;
import interf.SiteItf;

/**
 * Cette classe permet de créer un graphe
 * @author Jeremy FONTAINE jeremy.fontaine@live.fr
 *
 */
public class ServeurGraph
{
	/**
	 * Créé un noeud 
	 * @param args 
	 * 		<p>	args[0] port du noeud
	 * 		<p>	args[1] nom du noeud (optionel)
	 * 		<p>	args[2] liste des noeuds voisins sous la forme: hote1:port1,hote2:port2,...,hoteN:portN (optionel)
	 */
	public static void main(String[] args) 
	{
		
		try
		{
			System.out.println("Adresse: "+InetAddress.getLocalHost().toString());
		}
		catch (UnknownHostException e)
		{
			System.err.println("Erreur récupération adresse locale");
			e.printStackTrace();
		}
		SiteItf site       = null;
		String  nom        = "RMI";
		String  hoteVoisin = "";
		int     portVoisin = -1;
		
		int port = Integer.parseInt(args[0]);
		
		if(args.length > 1)
			try
			{
				site = new GraphImpl(args[1]);
			}
			catch (RemoteException e)
			{
				System.err.println("Erreur création nouvel objet GraphImpl");
				e.printStackTrace();
			}
		else
			try
			{
				site = new GraphImpl();
			}
			catch (RemoteException e)
			{
				System.err.println("Erreur création nouvel objet GraphImpl");
				e.printStackTrace();
			}

		Registry reg = null;
		try
		{
			reg = LocateRegistry.createRegistry(port);
		}
		catch (RemoteException e)
		{
			System.err.println("Erreur création d'un nouveau registre");
			e.printStackTrace();
		}
		try
		{
			reg.bind(nom, site);
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
		catch (AlreadyBoundException e)
		{
			System.err.println("L'assocation "+nom+" existe déjà");
			e.printStackTrace();
		}

		if(args.length > 2)
		{
			String[] lesVoisins = args[2].split(",");
			for(String voisin: lesVoisins)
			{
				hoteVoisin = voisin.split(":")[0];
				portVoisin = Integer.parseInt(voisin.split(":")[1]);
				
				Registry regP = null;
				try
				{
					regP = LocateRegistry.getRegistry(hoteVoisin,portVoisin);
				}
				catch (RemoteException e)
				{
					System.err.println("Erreur recupération du registre du voisin");
					e.printStackTrace();
				}
				
				GraphItf v = null;
				try
				{
					v = (GraphItf) regP.lookup(nom);
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
					System.err.println("Aucune assocation trouvé pour "+nom+" dans le registre");
					e.printStackTrace();
				}
				
				try
				{
					v.ajouteVoisin((GraphItf) site);
				}
				catch (RemoteException e)
				{
					System.err.println("Erreur ajout voisin");
					e.printStackTrace();
				}
			}
				
		}
		
	}
}
