package com.googleapis.maps.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Places {

	@JsonProperty("result")
	private Result result;

	@JsonProperty("status")
	private String status;

	@JsonProperty("address_components")
	private List<AddressComponentsItem> addressComponents;

	public Places() {

	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AddressComponentsItem> getAddressComponents() {
		return addressComponents;
	}

	public void setAddressComponents(List<AddressComponentsItem> addressComponents) {
		this.addressComponents = addressComponents;
	}

	@Override
	public String toString() {
		return "Places{" +
				"result=" + result +
				", status='" + status + '\'' +
				'}';
	}
}