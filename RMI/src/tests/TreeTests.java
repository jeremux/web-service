package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import message.Message;

import org.junit.Before;
import org.junit.Test;

import serveur.TreeImpl;

public class TreeTests
{
	public static TreeImpl arbre;
	
	@Before
	public void setUp() throws Exception 
	{
		arbre = new TreeImpl("1");
	}
	
	@Test
	public void testNom() throws RemoteException
	{
		//On vérifie tout betement le nom
		assertEquals("1",arbre.affiche());
	}
	
	@Test
	public void testNbFils() throws RemoteException 
	{
		assertEquals(0,arbre.getEnfants().size());
	}
	
	@Test
	public void testAjout() throws RemoteException
	{
		arbre.ajouteFils(new TreeImpl("2"));
		
		//On verifie qu'il possede 1 fils
		assertEquals(1,arbre.getEnfants().size());
		
		//On verifie que c'est le bon fils (2)
		assertEquals("2",arbre.getEnfants().get(0).affiche());
		
		//On vérifie que 2 a bien 1 comme parent
		assertEquals("1", arbre.getEnfants().get(0).getParent().affiche());
	}
	
	@Test
	public void testArbreComplet() throws RemoteException
	{
		//arbre donnée en exemple du tp
		TreeImpl deux   = new TreeImpl("2");
		TreeImpl trois  = new TreeImpl("3");
		TreeImpl quatre = new TreeImpl("4");
		TreeImpl cinq   = new TreeImpl("5");
		TreeImpl six    = new TreeImpl("6");
		
		deux.ajouteFils(trois);
		deux.ajouteFils(quatre);
		
		cinq.ajouteFils(six);
		
		arbre.ajouteFils(deux);
		arbre.ajouteFils(cinq);
		
		//On verifie le nombre de fils
		assertEquals(2,arbre.getEnfants().size());
		assertEquals(2,deux.getEnfants().size());
		assertEquals(1,cinq.getEnfants().size());
		assertEquals(0,trois.getEnfants().size());
		assertEquals(0,quatre.getEnfants().size());
		assertEquals(0,six.getEnfants().size());
		
		//On verifie les fils
		assertEquals("2",arbre.getEnfants().get(0).affiche());
		assertEquals("5",arbre.getEnfants().get(1).affiche());
		
		assertEquals("3",deux.getEnfants().get(0).affiche());
		assertEquals("4",deux.getEnfants().get(1).affiche());
		
		assertEquals("6",cinq.getEnfants().get(0).affiche());
			
		//On vérifie les parents
		assertEquals("1", arbre.getParentByName("2").affiche());
		assertEquals("1", arbre.getParentByName("5").affiche());
		
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
		
		arbre.ajouteFils(deux);
		arbre.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 1",12);
		
		arbre.transfert(m);
		
		//test si tous les noeuds connecté reçoit les messages
		assertEquals(m,deux.getMessage());
		assertEquals(m,trois.getMessage());
		assertEquals(m,quatre.getMessage());
		assertEquals(m,cinq.getMessage());
		assertEquals(m,six.getMessage());
		
		assertNotEquals(m, noeudNonConnecte.getMessage());
		
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
		
		arbre.ajouteFils(deux);
		arbre.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 1",12);
		Message m1 = new Message("Envoie du noeud 1 bis",13);
		
		arbre.transfert(m);
		arbre.transfert(m1);
		
		//test si tous les noeuds connecté reçoit qu'une fois les messages
		assertEquals(1,arbre.getCptMessage(12));
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
		
		arbre.ajouteFils(deux);
		arbre.ajouteFils(cinq);
		
		Message m = new Message("Envoie du noeud 5",12);
		
		cinq.transfert(m);
		
		//On fait un sleep pour envoyer à tous les noeuds
		try {
		    Thread.sleep(1000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//test si tous les noeuds connecté reçoit les messages
		assertEquals(m,arbre.getMessage());
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
		
		arbre.ajouteFils(deux);
		arbre.ajouteFils(cinq);
		
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
		assertEquals(1,arbre.getCptMessage(12));
		assertEquals(1,deux.getCptMessage(12));
		assertEquals(1,trois.getCptMessage(12));
		assertEquals(1,quatre.getCptMessage(12));
		assertEquals(1,cinq.getCptMessage(12));
		assertEquals(1,six.getCptMessage(12));
	}
}
