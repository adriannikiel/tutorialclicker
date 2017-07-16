package pl.javadevmatt.tutorialclicker.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.javadevmatt.tutorialclicker.IRequestCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class FeatureFlagService {

	public static final String REQUEST_URL = "http://adriannikiel.pythonanywhere.com/tutorialclicker/api/v1.0/features";
	public static final String FEATURE_SHOP = "FEATURE_SHOP";

	private Map<String, Boolean> featuresMap;

	public FeatureFlagService() {
		featuresMap = new HashMap<String, Boolean>();
	}

	public void makeFeatureFlagRequest(final IRequestCallback requestCallback) {
		HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
		HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url(REQUEST_URL).build();
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				parseResponse(httpResponse.getResultAsString());
				requestCallback.onSucceed();
			}

			@Override
			public void failed(Throwable t) {
				System.out.println(t.getMessage());
				requestCallback.onError();
			}

			@Override
			public void cancelled() {
				requestCallback.onError();
			}
		});

	}

	protected void parseResponse(String resultAsString) {
		System.out.println("Response: " + resultAsString);

		try {
			JSONObject obj = new JSONObject(resultAsString);
			JSONArray jsonArray = obj.getJSONArray("features");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject innerObj = jsonArray.getJSONObject(i);
				featuresMap.put((String) innerObj.get("name"), (Boolean) innerObj.get("active"));
			}
			System.out.println("Parsed map: " + featuresMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean hasFeature(String s) {
		if (featuresMap.containsKey(s)) {
			return featuresMap.get(s);
		} else {
			return false;
		}
	}
}
