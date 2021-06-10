package com.aruna.jaxws.resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Airline {

	public static String getWebsite(String iata_code) throws UnirestException {
		String url="https://iata-and-icao-codes.p.rapidapi.com/airline?iata_code=";
		
		HttpResponse<String> response = Unirest.get(url+iata_code)
				.header("x-rapidapi-host", "iata-and-icao-codes.p.rapidapi.com")
				.header("x-rapidapi-key", "475141e306mshf214b3827709162p13c0edjsn5b0f92549d49")
				.asString();
		JSONArray array=new JSONArray(response.getBody());
		JSONObject json=array.getJSONObject(0);
		String website=null;
		try {
		website=json.getString("website");
		}catch(JSONException e) {}
		System.out.println(response.getBody());
		return website;
	}
	 


}
