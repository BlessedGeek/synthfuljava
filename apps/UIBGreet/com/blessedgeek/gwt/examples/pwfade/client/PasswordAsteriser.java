package com.blessedgeek.gwt.examples.pwfade.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PasswordAsteriser
	implements EntryPoint
{

	private static PasswordAsteriserUiBinder uiBinder =
		GWT.create(PasswordAsteriserUiBinder.class);

	interface PasswordAsteriserUiBinder
		extends UiBinder<Widget, PasswordAsteriser>
	{}
	
	@UiField
	VerticalPanel allFields;
	@UiField
	TextBox name, password;

	private String passwordString;
	
	@UiHandler ("password")
	public void asteriseOnMouseOut(MouseOutEvent event){
		this.passwordString = password.getText();
		String masked = "*****";
		for(int i=5; i<this.passwordString.length(); i++)
			masked += '*';
		password.setText(masked);
	}
	@UiHandler ("password")
	public void deasteriseOnMouseOver(MouseOverEvent event){
		password.setText(this.passwordString);
	}

	@UiHandler("logIn")
	void onClick(
		ClickEvent e)
	{
		Window.alert("Name:"+name.getText() + "\nPassword:" + this.passwordString);
	}
	
	@Override
	public void onModuleLoad()
	{
		uiBinder.createAndBindUi(this);
		RootPanel.get("allFields").add(allFields);		
	}

	static public class UIGrid
		extends Grid
	{
		public @UiConstructor UIGrid(int rowCount, int columnCount){
			this.resize(rowCount, columnCount);
		}

		public void add(Widget w){
			int row = this.count/this.numColumns;
			int col = this.count - row*this.numColumns ;
			this.count++;
			if (this.numRows<row)
				this.numRows = row;
			this.setWidget(row, col, w);
		}
		
		public void add(String t){
			int row = this.count/this.numColumns;
			int col = this.count - row*this.numColumns;
			this.count++;
			this.setText(row, col, t);
		}
		protected int count=0;
	}
}