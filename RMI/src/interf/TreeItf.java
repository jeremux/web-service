package interf;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TreeItf extends SiteItf
{
	/**
	 * Ajoute au noeud courant un noeud fils
	 * @param enfant noeud à ajouter
	 * @throws RemoteException
	 */
	public void    ajouteFils(TreeItf enfant) throws RemoteException;
	
	/**
	 * Définit le noeud parent du noeud courant
	 * @param parent noeud à attribuer comme parent
	 * @throws RemoteException
	 */
	public void    fixeParent(TreeItf parent) throws RemoteException;
	
	/**
	 * Permet de recupérer les enfants du noeud courant
	 * @return liste des noeuds enfants, définit avec la topologie arbre
	 * @throws RemoteException
	 */
	public ArrayList<TreeItf> getEnfants() throws RemoteException;

	public TreeItf getParent() throws RemoteException;
}
