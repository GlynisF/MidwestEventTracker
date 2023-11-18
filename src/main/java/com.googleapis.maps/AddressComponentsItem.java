package com.googleapis.maps;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressComponentsItem{

	@JsonProperty("types")
	private List<String> types;

	@JsonProperty("longText")
	private String longText;

	@JsonProperty("shortText")
	private String shortText;

	@JsonProperty("languageCode")
	private String languageCode;

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setLongText(String longText){
		this.longText = longText;
	}

	public String getLongText(){
		return longText;
	}

	public void setShortText(String shortText){
		this.shortText = shortText;
	}

	public String getShortText(){
		return shortText;
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
			"AddressComponentsItem{" + 
			"types = '" + types + '\'' + 
			",longText = '" + longText + '\'' + 
			",shortText = '" + shortText + '\'' + 
			",languageCode = '" + languageCode + '\'' + 
			"}";
		}
}