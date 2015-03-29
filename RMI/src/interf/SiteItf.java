package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import message.Message;

/**
 * 
 * @author Jeremy FONTAINE (jeremy.fontaine@live.fr)
 *
 */

/**
 * Interface RMI pour les noeuds d'un graphe
 */
public interface SiteItf extends Remote 
{
	
	/**
	 * Affiche le nom du noeud courant
	 * @return nom du noeud courant
	 * @throws RemoteException
	 */
	public String  affiche() throws RemoteException;

	/**
	 * Permet de savoir si le noeud a 
	 * reçu un message avec le flag indiqué
	 * @param flag numéro du flag à tester
	 * @return true si le message a été reçu, false sinon
	 * @throws RemoteException
	 */
	public boolean aRecu(int flag) throws RemoteException;
	
	/**
	 * Ajoute au noeud courant un noeud fils
	 * @param enfant noeud à ajouter
	 * @throws RemoteException
	 */
	public void    ajouteFils(SiteItf enfant) throws RemoteException;
	
	/**
	 * Définit le noeud parent du noeud courant
	 * @param parent noeud à attribuer comme parent
	 * @throws RemoteException
	 */
	public void    fixeParent(SiteItf parent) throws RemoteException;
	
	/**
	 * Permet d'obtenir un flag aléatoire
	 * @return un entier aléatoire
	 * @throws RemoteException
	 */
	public int     genereFlag() throws RemoteException;
	
	/**
	 * Permet de transferet un message (objet) du noeud
	 * courant aux autres noeuds connectés
	 * @param message message à envoyer
	 * @throws RemoteException
	 */
	public void    transfert(Message message) throws RemoteException;
	
}
