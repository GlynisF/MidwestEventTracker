package com.googleapis.maps;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PlacesSearch{

	@JsonProperty("predictions")
	private List<PredictionsItem> predictions;

	@JsonProperty("status")
	private String status;

	public void setPredictions(List<PredictionsItem> predictions){
		this.predictions = predictions;
	}

	public List<PredictionsItem> getPredictions(){
		return predictions;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"PlacesSearch{" + 
			"predictions = '" + predictions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}