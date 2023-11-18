package com.googleapis.maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecondaryTextMatchedSubstringsItem{

	@JsonProperty("offset")
	private int offset;

	@JsonProperty("length")
	private int length;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setLength(int length){
		this.length = length;
	}

	public int getLength(){
		return length;
	}

	@Override
 	public String toString(){
		return 
			"SecondaryTextMatchedSubstringsItem{" + 
			"offset = '" + offset + '\'' + 
			",length = '" + length + '\'' + 
			"}";
		}
}