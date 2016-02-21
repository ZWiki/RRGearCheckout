package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;

import com.google.gson.Gson;

public class RidgeRoamersAPI {
	private static final String ENDPOINT = "http://climb.students.mtu.edu/api/";
	private static final String APP_UUID = "==TSLY==";
	
	public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	public static final String ACCEPT_ENCODING = "gzip, deflate, sdch";
	public static final String ACCEPT_LANGUAGE = "en-US,en;q=0.8";
	public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";
	public static final String ENCODING = "UTF-8";
	
	
	public static String basicRequest(String method, HashMap<String, Object> data) {
		try {
			String urlParams = null;
			
			// If data was not provided then the method does not take parameters,
			// i.e. get_co_list
			if (data == null) {
				urlParams = String.format("UUID=%s&method=%s",
						URLEncoder.encode(APP_UUID, ENCODING),
						URLEncoder.encode(method, ENCODING));
			} else {
				String jsonData = new Gson().toJson(data); // Parse map into json
				urlParams = String.format("UUID=%s&method=%s&data=%s",
						URLEncoder.encode(APP_UUID, ENCODING),
						URLEncoder.encode(method, ENCODING),
						URLEncoder.encode(jsonData, ENCODING));
			}
			
			byte[] postData = urlParams.getBytes(Charset.forName(ENCODING));
			
			URL url = new URL(ENDPOINT);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Set POST and basic request header information
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept", ACCEPT);
			conn.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
			conn.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			
			conn.connect(); // Connect with above settings
			
			// Write the POST data to the server
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(postData);
			out.flush();
			out.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			return "Response" + (((line = in.readLine()) != null) ? line : "");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public static String basicRequest(String method) {
		return basicRequest(method, null);
	}
}
