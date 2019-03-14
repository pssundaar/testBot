package com.stock.beans;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class mainm {

	public mainm() {}
	  
	  public static void main(String[] args)
	  {
	    try
	    {
	      Client client = Client.create();
	      



	      WebResource webResource = client.resource("http://localhost:8080/get");
	      
	      byte[] encoded = Files.readAllBytes(Paths.get("/Users/745453/Desktop/apiaijson1.txt", new String[0]));
	      
	      String input = new String(encoded, StandardCharsets.UTF_8);
	      

	      ClientResponse response = (ClientResponse)webResource.type("application/json").post(ClientResponse.class, input);
	      





	      System.out.println("Output from Server .... \n");
	      String output = (String)response.getEntity(String.class);
	      System.out.println(output);


	    }
	    catch (Exception e)
	    {


	      e.printStackTrace();
	    }
	  }
	
}
