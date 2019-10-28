package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import GameClasses.Item;

public class CrimeAndDimeService {
	public ArrayList<Item> loadItems() throws JsonParseException, JsonMappingException, IOException
	{
		ArrayList<Item> items;
		String result = "";
		try {
			String url = "http://coms-309-tc-3.misc.iastate.edu:8080/inventory";

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			String USER_AGENT = "Mozilla/5.0";

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			result = response.toString();

		}
		catch(Exception e)	{
			System.out.print(e);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		items = objectMapper.readValue(result, new TypeReference<ArrayList<Item>>(){});
		for (Item i : items)
		{
			System.out.println(i.getName());
		}
		System.out.println("***");

		for (Item item : items)
		{
			item.setRetailCost(item.getWholesaleCost() + item.getWholesaleCost() * 0.1);
		}
		
		return items;
	}
}
