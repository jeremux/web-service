package interf;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GraphItf extends SiteItf
{
	/**
	 * Ajoute un noeud voisin au noeud courant
	 * @param noeud noeud à ajouter au voisin courant
	 * @throws RemoteException
	 */
	public void ajouteVoisin(GraphItf noeud) throws RemoteException;
	
	/**
	 * Teste si le noeud courant et le noeud passé en paramètre
	 * sont voisins
	 * @param noeud noeud à tester
	 * @return true si les noeuds sont voisins false sinon 
	 * @throws RemoteException
	 */
	public boolean estVoisin(GraphItf noeud) throws RemoteException;

	public ArrayList<GraphItf> getVoisins() throws RemoteException ;

	public void setVoisins(ArrayList<GraphItf> voisins) throws RemoteException ;;
}
