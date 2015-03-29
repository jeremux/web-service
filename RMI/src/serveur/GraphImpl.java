/**
 * 
 */
package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import message.Message;
import interf.GraphItf;
import interf.TreeItf;

/**
 * @author jeremux
 *
 */
public class GraphImpl extends UnicastRemoteObject implements GraphItf
{

	private static              final    long serialVersionUID = -310287126277104232L;
	private String              nom;
	private int                 flag;
	private ArrayList<GraphItf> voisins;
	
	/**
	 * Créé un nouveau noeud sans nom
	 * @throws RemoteException
	 */
	protected GraphImpl() throws RemoteException
	{
		super();
		this.nom     = "sans nom";
		this.flag    = -1;
		this.voisins = new ArrayList<GraphItf>();
	}
	
	/**
	 * Créé un nouveau noeud
	 * @param nom nom du noeud créé
	 * @throws RemoteException
	 */
	protected GraphImpl(String nom) throws RemoteException
	{
		super();
		this.nom     = nom;
		this.flag    = -1;
		this.voisins = new ArrayList<GraphItf>();
		System.out.println("=============");
		System.out.println("Noeud "+nom);
		System.out.println("=============");
	}
	/* (non-Javadoc)
	 * @see interf.SiteItf#affiche()
	 */
	@Override
	public String affiche() throws RemoteException
	{
		return this.nom;
	}

	/* (non-Javadoc)
	 * @see interf.SiteItf#aRecu(int)
	 */
	@Override
	public boolean aRecu(int flagATester) throws RemoteException
	{
		return this.flag == flagATester;
	}

	/* (non-Javadoc)
	 * @see interf.SiteItf#genereFlag()
	 */
	@Override
	public int genereFlag() throws RemoteException
	{
		int    res  = this.flag;
		Random rand = new Random();

		while (res == this.flag)
		{
			res = rand.nextInt();
		}

		return res;
	}

	/* (non-Javadoc)
	 * @see interf.SiteItf#transfert(message.Message)
	 */
	@Override
	public void transfert(Message message) throws RemoteException
	{
		this.flag = message.getFlag();

		if (message.getOrigine() != null) 
		{
			System.out.println(this.nom+ " reçoit: \""+message.getContenu().toString()+"\" [de "+message.getOrigine().affiche()+"]");
		} 
		else 
		{
			System.out.println(this.nom+" reçoit : \""+message.getContenu().toString()+"\" [de client]");
		}
		
		// On met à jour l'origine pour envoyer ou noeud voisins...
		message.setOrigine(this);
		
		//Envoie aux voisins
		for(int i=0; i<voisins.size() ; i++)
		{	
			if(!voisins.get(i).aRecu(message.getFlag()))
				(new TransfertThread(voisins.get(i), message)).run();
		}
	}

	/* (non-Javadoc)
	 * @see interf.GraphItf#ajouteVoisin(interf.GraphItf)
	 */
	@Override
	public void ajouteVoisin(GraphItf noeud) throws RemoteException
	{
		if(!this.estVoisin(noeud))
			this.voisins.add(noeud);

	}

	/* (non-Javadoc)
	 * @see interf.GraphItf#estVoisin(interf.GraphItf)
	 */
	@Override
	public boolean estVoisin(GraphItf noeud) throws RemoteException
	{
		return this.voisins.contains(noeud);
	}

}