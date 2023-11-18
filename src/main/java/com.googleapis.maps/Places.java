package com.googleapis.maps;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Places{

	@JsonProperty("displayName")
	private DisplayName displayName;

	@JsonProperty("name")
	private String name;

	@JsonProperty("addressComponents")
	private List<AddressComponentsItem> addressComponents;

	@JsonProperty("id")
	private String id;

	public void setDisplayName(DisplayName displayName){
		this.displayName = displayName;
	}

	public DisplayName getDisplayName(){
		return displayName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAddressComponents(List<AddressComponentsItem> addressComponents){
		this.addressComponents = addressComponents;
	}

	public List<AddressComponentsItem> getAddressComponents(){
		return addressComponents;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Places{" + 
			"displayName = '" + displayName + '\'' + 
			",name = '" + name + '\'' + 
			",addressComponents = '" + addressComponents + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}