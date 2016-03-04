package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import com.google.gson.Gson;

public class RidgeRoamersAPI {
	private static final String ENDPOINT = "http://climb.students.mtu.edu/api/";
	private static final String APP_UUID = "==TSLY==";
	private static URL location = RidgeRoamersAPI.class.getResource("RidgeRoamersAPI.class");
	
	public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	public static final String ACCEPT_ENCODING = "gzip, deflate, sdch";
	public static final String ACCEPT_LANGUAGE = "en-US,en;q=0.8";
	public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";
	public static final String ENCODING = "UTF-8";
	
	private static final HashMap<String, Object> add_climber = new HashMap<String, Object>() {
		{
			put("gender", "NONE");
			put("spring_membership", "F"); put("fall_membership", "F"); put("summer_membership", "F");
			put("certified", "F"); put("cert_officer", "F");
		}
	};
	
	
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
				HashMap<String, String> map = new HashMap<>();
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					map.put(entry.getKey(), (String)entry.getValue());
				}
				String jsonData = new Gson().toJson(map); // Parse map into json
				urlParams = String.format("UUID=%s&method=%s&data=%s",
						URLEncoder.encode(APP_UUID, ENCODING),
						URLEncoder.encode(method, ENCODING),
						URLEncoder.encode(jsonData, ENCODING));
				System.out.println(jsonData);
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
			if ((line = in.readLine()) != null) {
				return line;
			} else {
				return "{}";
			}
			
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
	
	public static String add_climber(String first_name, String last_name, String password, HashMap<String, Object> additionalData) {
		StackTraceElement[] ele = Thread.currentThread().getStackTrace();
		String methodName = ele[1].getMethodName();
		HashMap<String, Object> data = basicReflectCall(methodName,
				additionalData,
				new Object[] {first_name, last_name, password});
		System.out.println(data);
		return basicRequest(methodName, data);
	}
	
	public static String view_available_items() {
		StackTraceElement[] ele = Thread.currentThread().getStackTrace();
		String methodName = ele[1].getMethodName();
		return basicRequest(methodName);
	}
	
	private static HashMap<String, Object> buildDataMap(String methodName) {
		HashMap<String, Object> data = null;
		try {
			Class<?> c = RidgeRoamersAPI.class;
			Field f = c.getDeclaredField(methodName);
			data = (HashMap<String, Object>) f.get(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private static HashMap<String, Object> basicReflectCall(String methodName, HashMap<String, Object> additionalData, Object... objects) {
		// Get the current ".class" location and create a ClassParser to be able to
		// perform reflection on the method parameters from the requested method
		ClassParser parser = null;
		try {
			parser = new ClassParser(location.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JavaClass jc = null;
		try {
			jc = parser.parse();
		} catch (ClassFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Find out if we actually have that method
		Method method = null;
		for (Method m : jc.getMethods()) {
			if (m.getName().equals(methodName)) {
				method = m;
				break;
			}
		}
		if (method == null) {
			throw new NullPointerException(String.format("No method '%s' found", methodName));
		}
		
		ArrayList<String> variables = new ArrayList<String>();
		
		// Loop through all parameter names and add them to the list
		LocalVariableTable table = method.getLocalVariableTable();
		for (int i = 0; i < method.getArgumentTypes().length-1; i++) {
			LocalVariable variable = table.getLocalVariable(i);
			variables.add(variable.getName());
		}
		
		HashMap<String, Object> dataToAdd = new HashMap<String, Object>();
		Type[] types = method.getArgumentTypes();
		
		// Add values to the hashmap
		for (int i = 0; i < objects.length; i++) {
			dataToAdd.put(variables.get(i), objects[i]);
		}
		
		// Add the data to the original
		HashMap<String, Object> originalData = buildDataMap(methodName);
		for (Map.Entry<String, Object> entry : dataToAdd.entrySet()) {
			originalData.put(entry.getKey(), entry.getValue());
		}
		
		// If additional data is given then add/replace here
		if (additionalData != null) {
			for (Map.Entry<String, Object> entry : additionalData.entrySet()) {
				originalData.put(entry.getKey(), entry.getValue());
			}
		}
		
		System.out.println(originalData);
		
		return originalData;
		
	}
}
