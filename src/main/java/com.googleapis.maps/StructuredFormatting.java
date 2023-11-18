package com.googleapis.maps;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StructuredFormatting{

	@JsonProperty("main_text_matched_substrings")
	private List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings;

	@JsonProperty("secondary_text_matched_substrings")
	private List<SecondaryTextMatchedSubstringsItem> secondaryTextMatchedSubstrings;

	@JsonProperty("secondary_text")
	private String secondaryText;

	@JsonProperty("main_text")
	private String mainText;

	public void setMainTextMatchedSubstrings(List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings){
		this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
	}

	public List<MainTextMatchedSubstringsItem> getMainTextMatchedSubstrings(){
		return mainTextMatchedSubstrings;
	}

	public void setSecondaryTextMatchedSubstrings(List<SecondaryTextMatchedSubstringsItem> secondaryTextMatchedSubstrings){
		this.secondaryTextMatchedSubstrings = secondaryTextMatchedSubstrings;
	}

	public List<SecondaryTextMatchedSubstringsItem> getSecondaryTextMatchedSubstrings(){
		return secondaryTextMatchedSubstrings;
	}

	public void setSecondaryText(String secondaryText){
		this.secondaryText = secondaryText;
	}

	public String getSecondaryText(){
		return secondaryText;
	}

	public void setMainText(String mainText){
		this.mainText = mainText;
	}

	public String getMainText(){
		return mainText;
	}

	@Override
 	public String toString(){
		return 
			"StructuredFormatting{" + 
			"main_text_matched_substrings = '" + mainTextMatchedSubstrings + '\'' + 
			",secondary_text_matched_substrings = '" + secondaryTextMatchedSubstrings + '\'' + 
			",secondary_text = '" + secondaryText + '\'' + 
			",main_text = '" + mainText + '\'' + 
			"}";
		}
}