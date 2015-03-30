package interf;

import java.rmi.RemoteException;
import java.util.ArrayList;
/**
 * 
 * @author Jeremy FONTAINE jeremy.fontaine@live.fr
 */
public interface GraphItf extends SiteItf
{
	/**
	 * Ajoute un noeud voisin au noeud courant
	 * @param noeud noeud à ajouter au voisin courant
	 * @throws RemoteException si ajout échoue
	 */
	public void ajouteVoisin(GraphItf noeud) throws RemoteException;
	
	/**
	 * Teste si le noeud courant et le noeud passé en paramètre
	 * sont voisins
	 * @param noeud noeud à tester
	 * @return true si les noeuds sont voisins false sinon 
	 * @throws RemoteException si le test echoue
	 */
	public boolean estVoisin(GraphItf noeud) throws RemoteException;

	/**
	 * Recupère la liste des voisins du noeud courant
	 * @return liste des voisins
	 * @throws RemoteException si la recupération échoue
	 */
	public ArrayList<GraphItf> getVoisins() throws RemoteException ;
	
	/**
	 * Met à jour la liste des voisins du noeud courant
	 * @param voisins nouvelle liste à affecter
	 * @throws RemoteException si l'affectation échoue
	 */
	public void setVoisins(ArrayList<GraphItf> voisins) throws RemoteException ;;
}
