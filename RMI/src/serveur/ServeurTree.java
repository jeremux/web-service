package serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interf.SiteItf;
import interf.TreeItf;

/**
 * Cette classe permet de créer un arbre
 * @author Jeremy FONTAINE jeremy.fontaine@live.fr
 *
 */
public class ServeurTree
{
	
	/**
	 * Créé un noeud
	 * @param args
	 * 			args[0] port du noeud
	 * 			args[1] nom du noeud (optionel)
	 * 			args[2] noeud parent sous la forme nomHote:numeroPort (optionel)
	 */
	public static void main(String[] args) 
	{
		
		try
		{
			System.out.println(InetAddress.getLocalHost().toString());
		}
		catch (UnknownHostException e)
		{
			System.err.println("Erreur récupération adresse locale");
			e.printStackTrace();
		}
		SiteItf site       = null;
		String  nom        = "RMI";
		String  hoteParent = "";
		int     portParent = -1;
		
		int port = Integer.parseInt(args[0]);
		
		if(args.length > 1)
			try
			{
				site = new TreeImpl(args[1]);
			}
			catch (RemoteException e)
			{
				System.err.println("Erreur création nouvel objet TreeImpl");
				e.printStackTrace();
			}
		else
			try
			{
				site = new TreeImpl();
			}
			catch (RemoteException e)
			{
				System.err.println("Erreur création nouvel objet TreeImpl");
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

		if(args.length > 3)
		{
			
			hoteParent += args[2].split(":")[0];
			portParent  = Integer.parseInt(args[2].split(":")[1]);
			System.out.println("On va chercher dans "+hoteParent+":"+portParent);
			
			Registry regP = null;
			try
			{
				regP = LocateRegistry.getRegistry(hoteParent,portParent);
			}
			catch (RemoteException e)
			{
				System.err.println("Erreur recupération du registre du parent");
				e.printStackTrace();
			}
			
			TreeItf parent = null;
			try
			{
				parent = (TreeItf) regP.lookup(nom);
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
				parent.ajouteFils((TreeItf) site);
			}
			catch (RemoteException e)
			{
				System.err.println("Erreur ajout noeud fils");
				e.printStackTrace();
			}
		}
		
	}
}
