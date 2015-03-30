/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import message.Message;

import org.junit.Before;
import org.junit.Test;

import serveur.GraphImpl;

/**
 * @author jeremux
 *
 */
public class GraphTests
{
	public static GraphImpl un;

	@Before
	public void setUp() throws Exception 
	{
		un = new GraphImpl("1");
	}

	@Test
	public void testNom() throws RemoteException
	{
		//On vérifie tout betement le nom
		assertEquals("1",un.affiche());
	}

	@Test
	public void testNbVoisins() throws RemoteException 
	{
		assertEquals(0,un.getVoisins().size());
	}

	@Test
	public void testAjout() throws RemoteException
	{
		GraphImpl deux = new GraphImpl("2");
		un.ajouteVoisin(deux);

		//On verifie qu'il possede 1 voisin nommé 2
		assertEquals(1,un.getVoisins().size());
		assertEquals("2",un.getVoisins().get(0).affiche());
		
		//On verifie que 2 possède aussi 1 voisin nommé 1
		assertEquals(1,deux.getVoisins().size());
		assertEquals("1",deux.getVoisins().get(0).affiche());
	}
	
	@Test
	public void GrapheComplet() throws RemoteException
	{
		//un donnée en exemple du tp avec 4 et 6 connecté
		GraphImpl deux   = new GraphImpl("2");
		GraphImpl trois  = new GraphImpl("3");
		GraphImpl quatre = new GraphImpl("4");
		GraphImpl cinq   = new GraphImpl("5");
		GraphImpl six    = new GraphImpl("6");
		
		deux.ajouteVoisin(trois);
		deux.ajouteVoisin(quatre);
		
		cinq.ajouteVoisin(six);
		
		//6 <-> 4
		six.ajouteVoisin(quatre);
		
		un.ajouteVoisin(deux);
		un.ajouteVoisin(cinq);
		
		//On verifie le nombre de voisins
		assertEquals(2,un.getVoisins().size());
		assertEquals(3,deux.getVoisins().size());
		assertEquals(2,cinq.getVoisins().size());
		assertEquals(1,trois.getVoisins().size());
		assertEquals(2,quatre.getVoisins().size());
		assertEquals(2,six.getVoisins().size());
		
		//On verifie les fils que ce sont les bons voisins
		
		assertEquals(false,un.getVoisins().contains(un));
		assertEquals(true,un.getVoisins().contains(deux));
		assertEquals(false,un.getVoisins().contains(trois));
		assertEquals(false,un.getVoisins().contains(quatre));
		assertEquals(true,un.getVoisins().contains(cinq));
		assertEquals(false,un.getVoisins().contains(six));
		
		assertEquals(true,deux.getVoisins().contains(un));
		assertEquals(false,deux.getVoisins().contains(deux));
		assertEquals(true,deux.getVoisins().contains(trois));
		assertEquals(true,deux.getVoisins().contains(quatre));
		assertEquals(false,deux.getVoisins().contains(cinq));
		assertEquals(false,deux.getVoisins().contains(six));
		
		assertEquals(false,trois.getVoisins().contains(un));
		assertEquals(true,trois.getVoisins().contains(deux));
		assertEquals(false,trois.getVoisins().contains(trois));
		assertEquals(false,trois.getVoisins().contains(quatre));
		assertEquals(false,trois.getVoisins().contains(cinq));
		assertEquals(false,trois.getVoisins().contains(six));
		
		assertEquals(false,quatre.getVoisins().contains(un));
		assertEquals(true,quatre.getVoisins().contains(deux));
		assertEquals(false,quatre.getVoisins().contains(trois));
		assertEquals(false,quatre.getVoisins().contains(quatre));
		assertEquals(false,quatre.getVoisins().contains(cinq));
		assertEquals(true,quatre.getVoisins().contains(six));
		
		assertEquals(true,cinq.getVoisins().contains(un));
		assertEquals(false,cinq.getVoisins().contains(deux));
		assertEquals(false,cinq.getVoisins().contains(trois));
		assertEquals(false,cinq.getVoisins().contains(quatre));
		assertEquals(false,cinq.getVoisins().contains(cinq));
		assertEquals(true,cinq.getVoisins().contains(six));
		
		assertEquals(false,six.getVoisins().contains(un));
		assertEquals(false,six.getVoisins().contains(deux));
		assertEquals(false,six.getVoisins().contains(trois));
		assertEquals(true,six.getVoisins().contains(quatre));
		assertEquals(true,six.getVoisins().contains(cinq));
		assertEquals(false,six.getVoisins().contains(six));
				
	}
	
	@Test
	public void envoiMessageNoeudSix() throws RemoteException
	{
		GraphImpl deux   = new GraphImpl("2");
		GraphImpl trois  = new GraphImpl("3");
		GraphImpl quatre = new GraphImpl("4");
		GraphImpl cinq   = new GraphImpl("5");
		GraphImpl six    = new GraphImpl("6");
		
		deux.ajouteVoisin(trois);
		deux.ajouteVoisin(quatre);
		
		cinq.ajouteVoisin(six);
		
		//6 <-> 4
		six.ajouteVoisin(quatre);
		
		un.ajouteVoisin(deux);
		un.ajouteVoisin(cinq);
		
		Message m = new Message("Envoie du noeud 6",12);
		
		six.transfert(m);
		
		//On fait un sleep pour envoyer à tous les noeuds
		try {
		    Thread.sleep(1000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//test si tous les noeuds connecté reçoit les messages
		assertEquals(m,un.getMessage());
		assertEquals(m,deux.getMessage());
		assertEquals(m,trois.getMessage());
		assertEquals(m,quatre.getMessage());
		assertEquals(m,cinq.getMessage());
		assertEquals(m,six.getMessage());
		
			
	}
	
	@Test
	public void recoisQUneFois() throws RemoteException
	{
		GraphImpl deux   = new GraphImpl("2");
		GraphImpl trois  = new GraphImpl("3");
		GraphImpl quatre = new GraphImpl("4");
		GraphImpl cinq   = new GraphImpl("5");
		GraphImpl six    = new GraphImpl("6");
		
		deux.ajouteVoisin(trois);
		deux.ajouteVoisin(quatre);
		
		cinq.ajouteVoisin(six);
		
		//6 <-> 4
		six.ajouteVoisin(quatre);
		
		un.ajouteVoisin(deux);
		un.ajouteVoisin(cinq);
		
		Message m = new Message("Envoie du noeud 1",12);
		Message m1 = new Message("Envoie du noeud 1 bis",13);
		
		six.transfert(m);
		
		//On fait un sleep pour envoyer à tous les noeuds
		try {
		    Thread.sleep(1000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		six.transfert(m1);
		
		//test si tous les noeuds connecté reçoit qu'une fois les messages
		assertEquals(1,un.getCptMessage(12));
		assertEquals(1,deux.getCptMessage(12));
		assertEquals(1,trois.getCptMessage(12));
		assertEquals(1,quatre.getCptMessage(12));
		assertEquals(1,cinq.getCptMessage(12));
		assertEquals(1,six.getCptMessage(12));
	}
}
