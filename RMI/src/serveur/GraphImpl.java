/**
 * 
 */
package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import message.Message;
import interf.GraphItf;


/**
 * <b>GraphImpl est la classe représentant un Graphe.</b>
 * <p>
 * Un objet GraphImpl est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un nom</li>
 * <li>Un flag</li>
 * <li>Des voisins</li>
 * </ul>
 * 
 * @author Jeremy FONTAINE jeremy.fontaine@live.fr
 * @version 1.0
 */
public class GraphImpl extends UnicastRemoteObject implements GraphItf
{

	private static                   final       long serialVersionUID = -310287126277104232L;
	private String                   nom;
	private int                      flag;
	private ArrayList<GraphItf>      voisins;
	protected Message                  message;
	protected HashMap<Integer,Integer> cptMessage;
	
	/**
	 * Créé un nouveau noeud sans nom
	 * @throws RemoteException si l'allocation échoue
	 */
	public GraphImpl() throws RemoteException
	{
		super();
		this.nom     = "sans nom";
		this.flag    = -1;
		this.voisins = new ArrayList<GraphItf>();
		this.cptMessage = new HashMap<Integer, Integer>();
	}
	
	/**
	 * Créé un nouveau noeud
	 * @param nom nom du noeud créé
	 * @throws RemoteException si l'allocation échoue
	 */
	public GraphImpl(String nom) throws RemoteException
	{
		super();
		this.nom     = nom;
		this.flag    = -1;
		this.voisins = new ArrayList<GraphItf>();
		this.cptMessage = new HashMap<Integer, Integer>();
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
		
		this.message = message;
		incrementeCpt();
		// On met à jour l'origine pour envoyer ou noeud voisins...
		message.setOrigine(this);
		
		//Envoie aux voisins
		for(int i=0; i<this.voisins.size() ; i++)
		{	
			System.out.println("On doit envoyer à "+voisins.get(i).affiche());
			if(!voisins.get(i).aRecu(message.getFlag()))
			{
				System.out.println("On envoie...");
				(new TransfertThread(voisins.get(i), message)).run();
			}
			else
			{
				System.out.println("On n'envoie PAS car deja recu...");
			}
		}
	}

	/* (non-Javadoc)
	 * @see interf.GraphItf#ajouteVoisin(interf.GraphItf)
	 */
	@Override
	public void ajouteVoisin(GraphItf noeud) throws RemoteException
	{
		if(!this.estVoisin(noeud))
		{
			this.voisins.add(noeud);
			System.out.println(this.nom+" connecté avec "+noeud.affiche());
		}
		
		if(!noeud.estVoisin(this))
		{
			ArrayList<GraphItf> tmp = noeud.getVoisins();
			tmp.add(this);
			noeud.setVoisins(tmp);
			System.out.println(noeud.affiche()+" connecté avec "+this.nom);
		}
	}

	/* (non-Javadoc)
	 * @see interf.GraphItf#estVoisin(interf.GraphItf)
	 */
	@Override
	public boolean estVoisin(GraphItf noeud) throws RemoteException
	{
		return (this.voisins.contains(noeud));
	}
	
	public ArrayList<GraphItf> getVoisins() throws RemoteException
	{
		return this.voisins;
	}

	@Override
	public void setVoisins(ArrayList<GraphItf> voisins) throws RemoteException
	{
		this.voisins = new ArrayList<GraphItf>();
		for(GraphItf g: voisins)
			this.voisins.add(g);
	}
	
	
	public Message getMessage()
	{
		return this.message;
	}

	
	public int getCptMessage(int i)
	{
		return this.cptMessage.get(i);
	}
	
	private void incrementeCpt()
	{
		if(this.cptMessage.get(this.flag)==null)
		{
			this.cptMessage.put(this.flag, 1);
		}
		else
		{
			int tmp = this.cptMessage.get(this.flag)+1;
			this.cptMessage.put(this.flag,tmp);
		}
		
	}

}
