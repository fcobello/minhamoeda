package br.com.cobello.minhamoeda.util;

public enum Flag 
{
	USD("USD","http://icons.iconarchive.com/icons/custom-icon-design/all-country-flag/24/United-States-Flag-icon.png"),
	BRL("BRL", "http://icons.iconarchive.com/icons/custom-icon-design/all-country-flag/24/Brazil-Flag-icon.png"),
	ARS("ARS", "http://icons.iconarchive.com/icons/custom-icon-design/all-country-flag/24/Argentina-Flag-icon.png");
	
	private String name;
	private String icon;
	
	Flag(String name, String icon)
	{
		this.name = name;
		this.icon = icon;
	}
	
	public String icon()
	{
		return icon;
	}
}
