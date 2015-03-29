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
 * @author jeremux
 *
 */
public class SiteImpl extends UnicastRemoteObject implements SiteItf
{

	private String             nom;
	private int                flag;
	private SiteItf            parent;
	private ArrayList<SiteItf> enfants;


	/**
	 * 
	 */
	private static final long serialVersionUID = 6058676928968366096L;


	protected SiteImpl() throws RemoteException
	{
		super();
		this.nom     = "sans nom";
		this.flag    = -1;
		this.enfants = new ArrayList<SiteItf>();
		this.setParent(null);

	}

	protected SiteImpl(String nom) throws RemoteException
	{
		super();
		this.setParent(null);
		this.nom     = nom;
		this.flag    = -1;
		this.enfants = new ArrayList<SiteItf>();

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
	 * @see interf.SiteItf#ajouteFils()
	 */
	@Override
	public void ajouteFils(SiteItf noeud) throws RemoteException
	{
		System.out.println("==========");
		System.out.println("Je vais ajouter le fils "+noeud.affiche()+" a "+this.affiche());
		this.enfants.add(noeud);
		noeud.fixeParent(this);
		System.out.println("Done...");
		System.out.println("==========");
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

	@Override
	public void fixeParent(SiteItf parent) throws RemoteException
	{
		this.setParent(parent);
		System.out.println(parent.affiche()+" a pour fils "+this.affiche());

	}

	/**
	 * @return the parent
	 */
	public SiteItf getParent()
	{
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SiteItf parent)
	{
		this.parent = parent;
	}

	@Override
	public void transfert(Message message) throws RemoteException
	{
		this.flag = message.getFlag();

		if (message.getOrigine() != null) 
		{
			System.out.println("Recu: \""+message.getContenu().toString()+"\" [de "+message.getOrigine().affiche()+"]");
		} 
		else 
		{
			System.out.println("Recu : \""+message.getContenu().toString()+"\" [de client]");
		}

		message.setOrigine(this);
		
		//Si parent non null et si pas recu on envoie au parent
		if (this.parent != null && !this.parent.aRecu(message.getFlag())) 
			(new TransferThread(this.parent, message)).start();
		
		
		//Envoie aux fils
		for(int i=0; i<enfants.size() ; i++)
		{	
			if(!enfants.get(i).aRecu(message.getFlag()))
				(new TransferThread(enfants.get(i), message)).run();
		}
	}

}
