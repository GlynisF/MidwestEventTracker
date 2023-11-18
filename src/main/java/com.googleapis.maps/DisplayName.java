package com.googleapis.maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisplayName{

	@JsonProperty("text")
	private String text;

	@JsonProperty("languageCode")
	private String languageCode;

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setLanguageCode(String languageCode){
		this.languageCode = languageCode;
	}

	public String getLanguageCode(){
		return languageCode;
	}

	@Override
 	public String toString(){
		return 
			"DisplayName{" + 
			"text = '" + text + '\'' + 
			",languageCode = '" + languageCode + '\'' + 
			"}";
		}
}