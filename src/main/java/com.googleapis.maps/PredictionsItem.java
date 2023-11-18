package com.googleapis.maps;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictionsItem{

	@JsonProperty("reference")
	private String reference;

	@JsonProperty("types")
	private List<String> types;

	@JsonProperty("matched_substrings")
	private List<MatchedSubstringsItem> matchedSubstrings;

	@JsonProperty("terms")
	private List<TermsItem> terms;

	@JsonProperty("structured_formatting")
	private StructuredFormatting structuredFormatting;

	@JsonProperty("description")
	private String description;

	@JsonProperty("place_id")
	private String placeId;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setMatchedSubstrings(List<MatchedSubstringsItem> matchedSubstrings){
		this.matchedSubstrings = matchedSubstrings;
	}

	public List<MatchedSubstringsItem> getMatchedSubstrings(){
		return matchedSubstrings;
	}

	public void setTerms(List<TermsItem> terms){
		this.terms = terms;
	}

	public List<TermsItem> getTerms(){
		return terms;
	}

	public void setStructuredFormatting(StructuredFormatting structuredFormatting){
		this.structuredFormatting = structuredFormatting;
	}

	public StructuredFormatting getStructuredFormatting(){
		return structuredFormatting;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"PredictionsItem{" + 
			"reference = '" + reference + '\'' + 
			",types = '" + types + '\'' + 
			",matched_substrings = '" + matchedSubstrings + '\'' + 
			",terms = '" + terms + '\'' + 
			",structured_formatting = '" + structuredFormatting + '\'' + 
			",description = '" + description + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}