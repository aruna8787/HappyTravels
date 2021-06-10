package com.aruna.jaxws.resources;

import org.json.JSONObject;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CurrencyConverter {

	 String fromCurrency;
	 String toCurrency;
	 String value;
	public CurrencyConverter(String fromCurrency,String toCurrency,String value) {
		this.fromCurrency=fromCurrency;
		this.toCurrency=toCurrency;
		this.value=value;
	}
	
	public  String getCurrencyRate() throws UnirestException {
		String convertedvalue="";
		String url="https://currency-converter5.p.rapidapi.com/currency/convert?format=json&to="+toCurrency+"&from="+fromCurrency+"&amount="+value;
		HttpResponse<String> response = Unirest.get(url)
				.header("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
				.header("x-rapidapi-key", "475141e306mshf214b3827709162p13c0edjsn5b0f92549d49")
				.asString();
		JSONObject mainObject = new JSONObject(response.getBody());
		JSONObject temp=mainObject.getJSONObject("rates");
		JSONObject exchangerate=temp.getJSONObject(toCurrency);
		convertedvalue=exchangerate.getString("rate_for_amount");
		
		System.out.println(convertedvalue);
		return convertedvalue;
	}public static void main(String[] args) throws UnirestException {
		CurrencyConverter crt=new CurrencyConverter("USD","INR","10");
		String output= crt.getCurrencyRate().toString();
		
	}

}
