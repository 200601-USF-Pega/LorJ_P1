package models;

import java.util.StringJoiner;

public class Pokemon
{
	
	private String name;
	private String nickname;
	private int level;
	private String [] moveset;
	private String ability;
	private String item;
	private int [] evs;
	private Natures nature;
	private Genders gender;
	private boolean isShiny;
	private int ot;

	public int getOt()
	{
		return ot;
	}

	public void setOt(int ot)
	{
		this.ot = ot;
	}

	public int getP_id()
	{
		return p_id;
	}

	public void setP_id(int p_id)
	{
		this.p_id = p_id;
	}

	private int p_id;
	
	public Pokemon()
	{
		this.name = "MissingNo.";
		this.nickname = "";
		this.level = 100;
		String [] mN0Moveset = {"", "", "", ""};
		this.moveset = mN0Moveset;
		this.ability = "N/A";
		this.item = "";
		int [] zeroEV = {0, 0, 0, 0, 0, 0};
		this.evs = zeroEV;
		this.nature = Natures.Adamant;
		this.gender = Genders.Genderless;
		this.isShiny = false;
		this.ot = 0;
	}

	public Pokemon(String name, String nickname, int level, String[] moveset, String ability, String item, int[] evs,
			Natures nature, Genders gender, boolean isShiny, int ot)
	{
		this.name = name;
		this.nickname = nickname;
		this.level = level;
		this.moveset = moveset;
		this.ability = ability;
		this.item = item;
		this.evs = evs;
		this.nature = nature;
		this.gender = gender;
		this.isShiny = isShiny;
		this.ot = ot;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String[] getMoveset()
	{
		return moveset;
	}

	public void setMoveset(String[] moveset)
	{
		this.moveset = moveset;
	}

	public String getAbility()
	{
		return ability;
	}

	public void setAbility(String ability)
	{
		this.ability = ability;
	}

	public int[] getEvs()
	{
		return evs;
	}

	public void setEvs(int[] evs)
	{
		this.evs = evs;
	}

	public Natures getNature()
	{
		return nature;
	}

	public void setNature(Natures nature)
	{
		this.nature = nature;
	}

	public Genders getGender()
	{
		return gender;
	}

	public void setGender(Genders gender)
	{
		this.gender = gender;
	}

	public boolean isShiny()
	{
		return isShiny;
	}

	public void setShiny(boolean isShiny)
	{
		this.isShiny = isShiny;
	}

	public int getOT()
	{
		return ot;
	}

	public void setOT(int ot)
	{
		this.ot = ot;
	}

	@Override
	public String toString()
	{
		String n;
		if(nickname.isEmpty())
		{
			n = "";
		}
		else
		{
			n = " (" + nickname + ")";
		}
		return name + n + " Lvl. " + level;
	}

	public String exportSmogon()
	{
		String n;
		if(nickname.isEmpty())
		{
			n = name;
		}
		else
		{
			n = nickname + " (" + name + ")";
		}
		
		String g = "";
		switch(gender)
		{
		case Male:
			g = " (M)";
			break;
		case Female:
			g = " (F)";
			break;
		default:
			break;
		}
		String i = item.isEmpty() ? "" : " @ " + item;
		String s = isShiny ? "Yes" : "No";

		String [] evLabel = {" HP", " Atk", " Def", " SpA", " SpD", " Spe"};
		StringJoiner sj = new StringJoiner(" / ");
		for(int c = 0; c < evs.length; c++)
		{
			if(evs[c] > 0)
			{
				sj.add(evs[c] + evLabel[c]);
			}
		}
		
		return n + g + i + "\nAbility: " + ability + "\nLevel: " + level + "\nShiny: " + s
				+ "\nEVs: " + sj.toString() + "\n" + nature + " Nature\n - " + moveset[0] + "\n - "
				+ moveset[1] + "\n - " + moveset[2] + "\n - " + moveset[3];
	}
	
}
