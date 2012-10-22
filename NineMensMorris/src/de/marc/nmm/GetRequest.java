package de.marc.nmm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

public class GetRequest {

	/**
	 * Executes a GET request to a given URL and converts the InputStream to a
	 * JSONArray.
	 * 
	 * @param url
	 *            The URL where the client wants to get JSON Strings
	 * @return A JSONArray, containing the received JSONObjects
	 */
	public static JSONArray getJson(String url) {
		JSONArray result = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);

			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();

			InputStream content = entity.getContent();
			InputStreamReader streamReader = new InputStreamReader(content);
			BufferedReader bufferedReader = new BufferedReader(streamReader);

			String line = bufferedReader.readLine();
			result = new JSONArray(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
