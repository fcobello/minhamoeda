package br.com.cobello.minhamoeda.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestService {

	// https://www2.confidencecambio.com.br/api/v1/resumo-cotacao
	// ecommerce.confidence|ECommerce|null|2760|MCwCFHR9v54Zh3NSStFTH6qZQNcjRZbwAhRBcNjWyU7ufGJw62q/uN8acQL+vg==
	
	public String send(String url)
	{
		return send(url, null);
	}
	
	public String send(String url, String authentication) 
	{
		return send(url, authentication, "GET");
	}

	public String send(String url, String authentication, String method) 
	{
		StringBuilder sb = new StringBuilder();
		
		try 
		{
			String output;
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod(method);
//			conn.setRequestProperty("Accept", "application/json");

			if (authentication != null) 
			{
				conn.setRequestProperty("auth", authentication);
			}
			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) 
			{
				sb.append(output);
			}

			conn.disconnect();
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
