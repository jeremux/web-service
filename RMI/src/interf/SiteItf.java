package interf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
	
	/**
	 * Permet de recupérer les enfants du noeud courant
	 * @return liste des noeuds enfants, définit avec la topologie arbre
	 * @throws RemoteException
	 */
	public ArrayList<SiteItf> getEnfants() throws RemoteException;
	
	/**
	 * Ajoute un noeud voisin au noeud courant
	 * @param noeud noeud à ajouter au voisin courant
	 * @throws RemoteException
	 */
	public void ajouteVoisin(SiteItf noeud) throws RemoteException;
	
	/**
	 * Teste si le noeud courant et le noeud passé en paramètre
	 * sont voisins
	 * @param noeud noeud à tester
	 * @return true si les noeuds sont voisins false sinon 
	 * @throws RemoteException
	 */
	public boolean estVoisin(SiteItf noeud) throws RemoteException;
	
}
