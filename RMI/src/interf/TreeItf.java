package interf;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * 
 * @author Jeremy FONTAINE jeremy.fontaine@live.fr
 */
public interface TreeItf extends SiteItf
{
	/**
	 * Ajoute au noeud courant un noeud fils
	 * @param enfant noeud à ajouter
	 * @throws RemoteException si l'ajout échoue
	 */
	public void    ajouteFils(TreeItf enfant) throws RemoteException;
	
	/**
	 * Définit le noeud parent du noeud courant
	 * @param parent noeud à attribuer comme parent
	 * @throws RemoteException si l'ajout échoue
	 */
	public void    fixeParent(TreeItf parent) throws RemoteException;
	
	/**
	 * Permet de recupérer les enfants du noeud courant
	 * @return liste des noeuds enfants, définit avec la topologie arbre
	 * @throws RemoteException si la récupération échoue
	 */
	public ArrayList<TreeItf> getEnfants() throws RemoteException;
	
	/**
	 * Recupère le parent du noeud courant
	 * @return le noeud parent
	 * @throws RemoteException si la récupération échoue
	 */
	public TreeItf getParent() throws RemoteException;
}
