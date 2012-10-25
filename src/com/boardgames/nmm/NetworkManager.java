package com.boardgames.nmm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class NetworkManager {

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
	
	/**
	 * Executes a POST request to a given URL with a given JSONObject.
	 * 
	 * @param url
	 *            The URL where the client wants to post JSON Strings
	 * @param object
	 *            The JSON String
	 * @return True if the request was successful, false otherwise
	 */
	public static boolean postJson(String url, JSONObject object) {
		boolean result = false;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(object.toString());

			post.setEntity(entity);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");

			HttpResponse response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			result = status == HttpStatus.SC_OK;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}