package com.scurab.gwt.rlw.client.controls;

import com.google.gwt.user.client.ui.Hyperlink;

public class MainMenuLink extends Hyperlink
{

	private final static String STYLE = "mainmenu-item";
	private  String mCommand = null;
	private boolean isSelected = false;

	public MainMenuLink()
	{
		setStyleName(STYLE);
	}

	public String getCommand()
	{
		return mCommand;
	}

	public void setCommand(String value)
	{
		mCommand = value;
		setTargetHistoryToken(value);
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}
}
