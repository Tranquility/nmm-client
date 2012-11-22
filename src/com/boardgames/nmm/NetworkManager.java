package com.boardgames.nmm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
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
	public static JSONObject getJson(String url) {
		JSONObject result = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);

			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();

			InputStream content = entity.getContent();
			InputStreamReader streamReader = new InputStreamReader(content);
			BufferedReader bufferedReader = new BufferedReader(streamReader);

			String line = bufferedReader.readLine();
			System.out.println(line);
			if (!line.equals("null"))
				result = new JSONObject(line);
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
	 *            The JSON object
	 * @return JSONObject with the response from the server
	 */
	public static JSONObject postJson(String url, JSONObject object) {
		JSONObject result = new JSONObject();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(object.toString());

			post.setEntity(entity);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");

			HttpResponse response = client.execute(post);

			InputStream content = response.getEntity().getContent();
			InputStreamReader streamReader = new InputStreamReader(content);
			BufferedReader bufferedReader = new BufferedReader(streamReader);

			String line = bufferedReader.readLine();
			try {
				result = new JSONObject(line);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}