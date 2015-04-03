TP 3 RMI
========

(https://github.com/jeremux/web-service/tree/master/RMI)
# Auteurs

Jeremy FONTAINE

31 mars 2015


# Introduction

Ce projet utilise l'API RMI de Java pour
communiquer entre plusieurs sites de façon distribuée et asynchrone. 

Le projet gère les sites connectés avec une topologie d'arbre ou plus généralement
de graphe.

Avec une topologie en arbre un message envoyé sur un noeud est propagé à ses fils.

Avec une topologie en graphe un message envoyé à un noeud
propage celui-ci à tous ses voisins.

Le guide d'utilisation et des exemples se trouvent à la fin du README

# Architecture

Le projet est découpé en 3 paquets:
	*  client, pour gérer les clients qui communiquent avec les sites
	*  interf, regroupant l'ensemble des interfaces RMI
	*  message, pour gérer le message échangé
	*  serveur, l'ensemble des classes pour gérer les sites

# Exceptions

Toutes les exceptions sont gérées par un try/catch avec un message explicite,
en voici quelques une :

*  Lors d'une recherche dans l'annuaire (lookup) :

```java
	try
	{
		site = (SiteItf) reg.lookup("RMI");
	}
	catch (AccessException e)
	{
		System.err.println("Erreur d'acces, vous n'avez peut - être pas la permission");
		e.printStackTrace();
	}
	catch (RemoteException e)
	{
		System.err.println("Erreur RemoteException");
		e.printStackTrace();
	}
	catch (NotBoundException e)
	{
		System.err.println("Aucun assocation avec dans le registre");
		e.printStackTrace();
	}
```

* Lors de la création d'un registre local

```java
	try
	{
		reg = LocateRegistry.getRegistry(hote, port);
	}
	catch (RemoteException e)
	{
		System.err.println("Erreur récupération d'un objet Registry sur "+hote+":"+port);
		e.printStackTrace();
	}
```

* Lors de la récupération d'un registre

```java
	try
	{
		regP = LocateRegistry.getRegistry(hoteVoisin,portVoisin);
	}
	catch (RemoteException e)
	{
		System.err.println("Erreur recupération du registre du voisin");
		e.printStackTrace();
	}
```


# Exemples de code

## envoyer un message à partir d'un site
```Java
//flag généré
Message m = new Message(objet,flag,src);
noeud.transferMessage(m);
```

## ajouter un site à l'annuaire
```Java
TreeImpl site = new TreeImpl(nom);
registry.bind(site.affiche(), site);
```

## empêcher l'envoie en double du message 
```Java

	public boolean aRecu(int flagATester) throws RemoteException
	{
		return this.flag == flagATester;
	}
...
	if (this.parent != null && !this.parent.aRecu(message.getFlag())) 
			//envoie parent
		
		
	//Envoie aux fils
	for(int i=0; i<enfants.size() ; i++)
	{	
		if(!enfants.get(i).aRecu(message.getFlag()))
			//envoie fils i
	}
```

# Tests
En résumé les tests vérifient que les structures créées sont bien
des structures d'arbres ou de graphe. Ils vérifies également, le bon envoie vers tous les noeuds.
Et vérifient que chaque neoud ne reçoit aucun message dupliqué. 

Voir src/tests

# Exécuter le projet

* Pour une topologie en arbre

```bash
    java -jar ServeurTree.jar port nom [parentHote:parentPort]
```

* Pour une topologie en graphe

```bash
    java -jar ServeurGraph.jar port nom [voisinHote-1:voisinPort-1,...,voisinHote-N:voisinPort-N]
```

* Pour envoyer un message a un noeud 

```bash
    java -jar Client.jar port nom [message]
```

# Scripts 

```bash
	cd scripts
```

### TreeExample.sh
Ce script créé l'exemple d'arbre donnée dans le tp 

### GraphExample.sh
Ce script rajoute un arc entre les noeuds 4 et 6 dans l'arbre d'exemple

### EnvoieNoeudN.sh 0 < N < 7
Permet d'envoyer un message au noeud n
