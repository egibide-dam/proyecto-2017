package controlador;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControlOpenhabian {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
		public static void encenderEnchufe() {
			try {
				sendPost("ON");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public static void apagarEnchufe() {
			try {
				sendPost("OFF");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	// HTTP POST request
		private static void sendPost(String onOff) throws Exception {

			String url = "http://10.1.3.14:8080/rest/items/Enchufe";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "text/plain");

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(onOff);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + onOff);
//			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
//			System.out.println(response.toString());

		}
}
