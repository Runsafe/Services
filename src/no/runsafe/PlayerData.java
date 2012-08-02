package no.runsafe;

import no.runsafe.framework.database.RunsafeEntity;

import java.sql.Date;

public class PlayerData extends RunsafeEntity
{

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getJoined()
	{
		return joined;
	}

	public void setJoined(Date joined)
	{
		this.joined = joined;
	}

	public Date getLogin()
	{
		return login;
	}

	public void setLogin(Date login)
	{
		this.login = login;
	}

	public Date getLogout()
	{
		return logout;
	}

	public void setLogout(Date logout)
	{
		this.logout = logout;
	}

	public Date getBanned()
	{
		return banned;
	}

	public void setBanned(Date banned)
	{
		this.banned = banned;
	}

	private String name;
	private Date joined;
	private Date login;
	private Date logout;
	private Date banned;
}
