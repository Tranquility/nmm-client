package com.boardgames.nmm;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class PostRequest {

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
