package message;

import java.io.Serializable;

import interf.SiteItf;

/**
 * @author Jeremy FONTAINE (jeremy.fontaine@live.fr)
 */

/**
 * <b>Message est la classe représentant un message à transferer.</b>
 * <p>
 * Un objet Message est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un contenu</li>
 * <li>Un flag, pour identifier le message</li>
 * <li>Une origine</li>
 * </ul>
 * 
 * @author jeremy FONTAINE
 * @version 1.0
 */
public class Message implements Serializable
{

	private static  final    long serialVersionUID = 7731616112031726802L;
	private Object  contenu;
	private int     flag;
	private SiteItf origine;
	
	/**
	 * Créé un nouveau Message
	 * @param o contenu du message
	 * @param flag identifiant du message
	 */
	public Message(Object o, int flag)
	{
		this.setContenu(o);
		this.setFlag(flag);
		/* Le message vient d'un client */
		this.setOrigine(null);
	}
	
	/**
	 * Créé un nouveau Message avec une source
	 * @param o contenu du message
	 * @param flag identifiant du message
	 * @param src source du message
	 */
	public Message(Object o, int flag, SiteItf src)
	{
		this.setContenu(o);
		this.setFlag(flag);
		this.setOrigine(src);
	}

	/**
	 * Recupère le contenu du message
	 * @return le contenu du message
	 */
	public Object getContenu()
	{
		return contenu;
	}

	/**
	 * Met à jour le contenu du message
	 * @param contenu contenu à attribuer
	 */
	public void setContenu(Object contenu)
	{
		this.contenu = contenu;
	}

	/**
	 * Recupère le flag du message courant
	 * @return le flag du message courant
	 */
	public int getFlag()
	{
		return flag;
	}

	/**
	 * Met à jour le flag du message
	 * @param flag flag à attribuer
	 */
	public void setFlag(int flag)
	{
		this.flag = flag;
	}

	/**
	 * Recupère la source (SiteItf) du message
	 * @return l'origine du message
	 */
	public SiteItf getOrigine()
	{
		return origine;
	}

	/**
	 * Met à jour la source du message
	 * @param origine source à affecter au message courant
	 */
	public void setOrigine(SiteItf origine)
	{
		this.origine = origine;
	}

}
