package Services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LobbyScreenService {

    public String getUsernames(int lobbyId) {
        try {
            String url = "http://coms-309-tc-3.misc.iastate.edu:8080/individualLobby?lobbyID=" + lobbyId;

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

            String result = response.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failure";
    }

    public String callAPIGet(int lobbyId) {
        String result = "";
        try {
            String url = "http://coms-309-tc-3.misc.iastate.edu:8080/lobbyInfo?lobbyID=" + lobbyId;

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
            return result;
        }
        catch(Exception e)	{
            System.out.print(e);
        }
        return "failure";
    }

    public String APIDelete(String username) {
        try {
            String url = "http://coms-309-tc-3.misc.iastate.edu:8080/deleteUser2?username=" + username;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            String USER_AGENT = "Mozilla/5.0";

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return "success";
        }
        catch(Exception e)	{
            System.out.print(e);
        }
        return "failure";
    }
    
    public String APIDeleteLobby(int lobbyId) {
    	try {
            String url = "http://coms-309-tc-3.misc.iastate.edu:8080/deleteLobby?lobbyID=" + lobbyId;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            String USER_AGENT = "Mozilla/5.0";

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return "success";
        }
        catch(Exception e)	{
            System.out.print(e);
        }
        return "failure";
    }
}
