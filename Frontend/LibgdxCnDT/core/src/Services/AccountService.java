package Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccountService {

    public boolean login(String username, String password) {
        try {
            String url = "http://coms-309-tc-3.misc.iastate.edu:8080/login?username=" + username + "&password=" + password;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            String USER_AGENT = "Mozilla/5.0";

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String result = response.toString();
            if(result.toLowerCase().equals("accepted")) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e)	{
            System.out.print(e);
            return false;
        }
    }

    public boolean register(String username, String password, String email, String phoneNumber) {
        try {
            String url = "http://coms-309-tc-3.misc.iastate.edu:8080/register?username=" + username + "&password=" + password;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("POST");

            String USER_AGENT = "Mozilla/5.0";

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String result = response.toString();
            if(result.toLowerCase().equals("accepted")) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception e)	{
            System.out.print(e);
            return false;
        }
    }
}
