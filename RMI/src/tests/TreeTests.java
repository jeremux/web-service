package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import message.Message;

import org.junit.Before;
import org.junit.Test;

import serveur.TreeImpl;

public class TreeTests
{
	public static TreeImpl un;
	
	@Before
	public void setUp() throws Exception 
	{
		un = new TreeImpl("1");
	}
	
	@Test
	public void testNom() throws RemoteException
	{
		//On vérifie tout betement le nom
		assertEquals("1",un.affiche());
	}
	
	@Test
	public void testNbFils() throws RemoteException 
	{
		assertEquals(0,un.getEnfants().size());
	}
	
	@Test
	public void testAjout() throws RemoteException
	{
		un.ajouteFils(new TreeImpl("2"));
		
		//On verifie qu'il possede 1 fils
		assertEquals(1,un.getEnfants().size());
		
		//On verifie que c'est le bon fils (2)
		assertEquals("2",un.getEnfants().get(0).affiche());
		
		//On vérifie que 2 a bien 1 comme parent
		assertEquals("1", un.getEnfants().get(0).getParent().affiche());
	}
	
	@Test
	public void testArbreComplet() throws RemoteException
	{
		//un donnée en exemple du tp
		TreeImpl deux   = new TreeImpl("2");
		TreeImpl trois  = new TreeImpl("3");
		TreeImpl quatre = new TreeImpl("4");
		TreeImpl cinq   = new TreeImpl("5");
		TreeImpl six    = new TreeImpl("6");
		
		deux.ajouteFils(trois);
		deux.ajouteFils(quatre);
		
		cinq.ajouteFils(six);
		
		un.ajouteFils(deux);
		un.ajouteFils(cinq);
		
		//On verifie le nombre de fils
		assertEquals(2,un.getEnfants().size());
		assertEquals(2,deux.getEnfants().size());
		assertEquals(1,cinq.getEnfants().size());
		assertEquals(0,trois.getEnfants().size());
		assertEquals(0,quatre.getEnfants().size());
		assertEquals(0,six.getEnfants().size());
		
		//On verifie les fils
		assertEquals("2",un.getEnfants().get(0).affiche());
		assertEquals("5",un.getEnfants().get(1).affiche());
		
		assertEquals("3",deux.getEnfants().get(0).affiche());
		assertEquals("4",deux.getEnfants().get(1).affiche());
		
		assertEquals("6",cinq.getEnfants().get(0).affiche());
			
		//On vérifie les parents
		assertEquals("1", un.getParentByName("2").affiche());
		assertEquals("1", un.getParentByName("5").affiche());
		
		assertEquals("2", deux.getParentByName("3").affiche());
		assertEquals("2", deux.getParentByName("4").affiche());
		
		assertEquals("5", cinq.getParentByName("6").affiche());
	}
	
	@Test
	public void envoiMessageDepuisRacine() throws RemoteException
	{
		TreeImpl deux   = new TreeImpl("2");
		TreeImpl trois  = new TreeImpl("3");
		TreeImpl quatre = new TreeImpl("4");
		TreeImpl cinq   = new TreeImpl("5");
		TreeImpl six    = new TreeImpl("6");
		
		TreeImpl noeudNonConnecte = new TreeImpl("-1");
		
		deux.ajouteFils(trois);
		deux.ajouteFils(quatre);
		
		cinq.ajouteFils(six);
		
		un.ajouteFils(deux);
		un.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 1",12);
		
		un.transfert(m);
		
		//test si tous les noeuds connecté reçoit les messages
		assertEquals(m,deux.getMessage());
		assertEquals(m,trois.getMessage());
		assertEquals(m,quatre.getMessage());
		assertEquals(m,cinq.getMessage());
		assertEquals(m,six.getMessage());
		
		boolean tmp = m.equals(noeudNonConnecte.getMessage());
		assertEquals(false,tmp);
		
	}
	
	@Test
	public void recoisQUneFoisRacine() throws RemoteException
	{
		TreeImpl deux   = new TreeImpl("2");
		TreeImpl trois  = new TreeImpl("3");
		TreeImpl quatre = new TreeImpl("4");
		TreeImpl cinq   = new TreeImpl("5");
		TreeImpl six    = new TreeImpl("6");
				
		deux.ajouteFils(trois);
		deux.ajouteFils(quatre);
		
		cinq.ajouteFils(six);
		
		un.ajouteFils(deux);
		un.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 1",12);
		Message m1 = new Message("Envoie du noeud 1 bis",13);
		
		un.transfert(m);
		un.transfert(m1);
		
		//test si tous les noeuds connecté reçoit qu'une fois les messages
		assertEquals(1,un.getCptMessage(12));
		assertEquals(1,deux.getCptMessage(12));
		assertEquals(1,trois.getCptMessage(12));
		assertEquals(1,quatre.getCptMessage(12));
		assertEquals(1,cinq.getCptMessage(12));
		assertEquals(1,six.getCptMessage(12));
				
	}
	
	@Test
	public void envoiMessageNoeudCinq() throws RemoteException
	{
		TreeImpl deux   = new TreeImpl("2");
		TreeImpl trois  = new TreeImpl("3");
		TreeImpl quatre = new TreeImpl("4");
		TreeImpl cinq   = new TreeImpl("5");
		TreeImpl six    = new TreeImpl("6");
		
		deux.ajouteFils(trois);
		deux.ajouteFils(quatre);
		
		cinq.ajouteFils(six);
		
		un.ajouteFils(deux);
		un.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 5",12);
		
		cinq.transfert(m);
		
		//On fait un sleep pour envoyer à tous les noeuds
		try {
		    Thread.sleep(1000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//test si tous les noeuds connecté reçoit les messages
		assertEquals(m,un.getMessage());
		assertEquals(m,six.getMessage());
		assertEquals(m,deux.getMessage());
		assertEquals(m,trois.getMessage());
		assertEquals(m,quatre.getMessage());
			
	}
	
	@Test
	public void recoisQUneFois() throws RemoteException
	{
		TreeImpl deux   = new TreeImpl("2");
		TreeImpl trois  = new TreeImpl("3");
		TreeImpl quatre = new TreeImpl("4");
		TreeImpl cinq   = new TreeImpl("5");
		TreeImpl six    = new TreeImpl("6");
		
		deux.ajouteFils(trois);
		deux.ajouteFils(quatre);
		
		cinq.ajouteFils(six);
		
		un.ajouteFils(deux);
		un.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 1",12);
		Message m1 = new Message("Envoie du noeud 1 bis",13);
		
		cinq.transfert(m);
		
		//On fait un sleep pour envoyer à tous les noeuds
		try {
		    Thread.sleep(1000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		cinq.transfert(m1);
		
		//test si tous les noeuds connecté reçoit qu'une fois les messages
		assertEquals(1,un.getCptMessage(12));
		assertEquals(1,deux.getCptMessage(12));
		assertEquals(1,trois.getCptMessage(12));
		assertEquals(1,quatre.getCptMessage(12));
		assertEquals(1,cinq.getCptMessage(12));
		assertEquals(1,six.getCptMessage(12));
	}
}
