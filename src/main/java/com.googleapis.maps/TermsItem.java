package com.googleapis.maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TermsItem{

	@JsonProperty("offset")
	private int offset;

	@JsonProperty("value")
	private String value;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"TermsItem{" + 
			"offset = '" + offset + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}