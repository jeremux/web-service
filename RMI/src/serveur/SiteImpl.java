/**
 * 
 */
package serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import message.Message;
import interf.SiteItf;

/**
 * <b>SiteImpl est la classe représentant un noeud.</b>
 * <p>
 * Un objet SiteImpl est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un nom</li>
 * <li>Un flag</li>
 * <li>Un noeud parent</li>
 * <li>Des enfants, ensemble de noeud</li>
 * </ul>
 * 
 * @author jeremy FONTAINE
 * @version 1.0
 */
public class SiteImpl extends UnicastRemoteObject implements SiteItf
{

	private String             nom;
	private int                flag;
	private SiteItf            parent;
	private ArrayList<SiteItf> enfants;

	private static final long serialVersionUID = 6058676928968366096L;

	/**
	 * Créé un nouveau noeud sans nom
	 * @throws RemoteException
	 */
	protected SiteImpl() throws RemoteException
	{
		super();
		this.nom     = "sans nom";
		this.flag    = -1;
		this.enfants = new ArrayList<SiteItf>();
		this.setParent(null);

	}
	
	/**
	 * Créé un nouveau noeud
	 * @param nom nom du noeud créé
	 * @throws RemoteException
	 */
	protected SiteImpl(String nom) throws RemoteException
	{
		super();
		this.setParent(null);
		this.nom     = nom;
		this.flag    = -1;
		this.enfants = new ArrayList<SiteItf>();
		System.out.println("=============");
		System.out.println("|Noeud "+nom+"|");
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
	 * @see interf.SiteItf#ajouteFils(SiteItf)
	 */
	@Override
	public void ajouteFils(SiteItf noeud) throws RemoteException
	{
//		System.out.println("==========");
//		System.out.println("Je vais ajouter le fils "+noeud.affiche()+" a "+this.affiche());
		this.enfants.add(noeud);
		noeud.fixeParent(this);
//		System.out.println("Done...");
//		System.out.println("==========");
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
	 * @see interf.SiteItf#genereFlag(SiteItf)
	 */
	@Override
	public void fixeParent(SiteItf parent) throws RemoteException
	{
		this.setParent(parent);
		//System.out.println(parent.affiche()+" a pour fils "+this.affiche());
		//System.out.println(this.stringGraphe(parent));
	}

	/**
	 * Recupère le parent du noeud courant
	 * @return le noeud parent du noeud courant
	 */
	public SiteItf getParent()
	{
		return parent;
	}

	/**
	 * Met à jour le parent du noeud courant
	 * @param parent noeud parent à attribuer
	 */
	public void setParent(SiteItf parent)
	{
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see interf.SiteItf#genereFlag(Message)
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
		
		//Si parent non null et si pas recu on envoie au parent
		if (this.parent != null && !this.parent.aRecu(message.getFlag())) 
			(new TransfertThread(this.parent, message)).start();
		
		
		//Envoie aux fils
		for(int i=0; i<enfants.size() ; i++)
		{	
			if(!enfants.get(i).aRecu(message.getFlag()))
				(new TransfertThread(enfants.get(i), message)).run();
		}
	}
	
	public String stringGraphe(SiteItf site) throws RemoteException
	{
		String res = "";
		
		if(site.getEnfants().size()==0)
			res+="("+site.affiche()+")";
		else
			res += "(" + site.affiche();
			for(int i=0; i<site.getEnfants().size() ; i++)
			{	
				res+="("+site.getEnfants().get(i).affiche()+")";
			}
			res += ")";
		return res;
	}

	@Override
	public ArrayList<SiteItf> getEnfants() throws RemoteException
	{
		return this.enfants;
	}

	@Override
	public void ajouteVoisin(SiteItf noeud) throws RemoteException
	{
		if(!this.estVoisin(noeud))
		{
			this.ajouteFils(noeud);
		}
		
	}

	@Override
	public boolean estVoisin(SiteItf noeud) throws RemoteException
	{
		if(parent!=null)
			return (this.enfants.contains(noeud) || this.parent.equals(noeud));
		else
			return this.enfants.contains(noeud);
	}

}
