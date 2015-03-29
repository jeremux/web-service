package message;

import java.io.Serializable;

import interf.SiteItf;

public class Message implements Serializable
{
	/**
	 * 
	 */
	private static  final    long serialVersionUID = 7731616112031726802L;
	private Object  contenu;
	private int     flag;
	private SiteItf origine;

	public Message(Object o, int flag)
	{
		this.setContenu(o);
		this.setFlag(flag);
		this.setOrigine(null);
	}

	public Message(Object o, int flag, SiteItf src)
	{
		this.setContenu(o);
		this.setFlag(flag);
		this.setOrigine(src);
	}

	/**
	 * @return the contenu
	 */
	public Object getContenu()
	{
		return contenu;
	}

	/**
	 * @param contenu
	 *            the contenu to set
	 */
	public void setContenu(Object contenu)
	{
		this.contenu = contenu;
	}

	/**
	 * @return the flag
	 */
	public int getFlag()
	{
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(int flag)
	{
		this.flag = flag;
	}

	/**
	 * @return the origine
	 */
	public SiteItf getOrigine()
	{
		return origine;
	}

	/**
	 * @param origine
	 *            the origine to set
	 */
	public void setOrigine(SiteItf origine)
	{
		this.origine = origine;
	}

}
