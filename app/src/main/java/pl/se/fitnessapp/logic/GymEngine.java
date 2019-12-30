package pl.se.fitnessapp.logic;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.se.fitnessapp.model.Gym;

public class GymEngine extends AsyncTask<Object, String, String> implements IGyms {


	//GetNearbyPlaces Data
	String googlePlacesData;
	GoogleMap mMap;
	String url;

	@Override
	protected String doInBackground(Object... params) {
		try {
			Log.d("GetNearbyPlacesData", "doInBackground entered");
			mMap = (GoogleMap) params[0];
			url = (String) params[1];
			DownloadUrl downloadUrl = new DownloadUrl();
			googlePlacesData = downloadUrl.readUrl(url);
			Log.d("GooglePlacesReadTask", "doInBackground Exit");
		} catch (Exception e) {
			Log.d("GooglePlacesReadTask", e.toString());
		}
		return googlePlacesData;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.d("GooglePlacesReadTask", "onPostExecute Entered");
		List<HashMap<String, String>> nearbyPlacesList = null;
		DataParser dataParser = new DataParser();
		nearbyPlacesList =  dataParser.parse(result);
		ShowNearbyPlaces(nearbyPlacesList);
		Log.d("GooglePlacesReadTask", "onPostExecute Exit");
	}

	private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
		for (int i = 0; i < nearbyPlacesList.size(); i++) {
			Log.d("onPostExecute","Entered into showing locations");
			MarkerOptions markerOptions = new MarkerOptions();
			HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
			double lat = Double.parseDouble(googlePlace.get("lat"));
			double lng = Double.parseDouble(googlePlace.get("lng"));
			String placeName = googlePlace.get("place_name");
			String vicinity = googlePlace.get("vicinity");
			LatLng latLng = new LatLng(lat, lng);
			markerOptions.position(latLng);
			markerOptions.title(placeName + " : " + vicinity);
			mMap.addMarker(markerOptions);
			markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			//move map camera
			//mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
		}
	}



	public GymEngine() {
		// TODO - implement GymEngine.GymEngine
		//throw new UnsupportedOperationException();
	}

	@Override
	public List<Gym> getGyms() {
		return null;
	}

	@Override
	public Location getUserLocation() {
		return null;
	}

	//DataParser internal class
	private class DataParser {
		public List<HashMap<String, String>> parse(String jsonData) {
			JSONArray jsonArray = null;
			JSONObject jsonObject;

			try {
				Log.d("Places", "parse");
				jsonObject = new JSONObject((String) jsonData);
				jsonArray = jsonObject.getJSONArray("results");
			} catch (JSONException e) {
				Log.d("Places", "parse error");
				e.printStackTrace();
			}
			return getPlaces(jsonArray);
		}

		private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
			int placesCount = jsonArray.length();
			List<HashMap<String, String>> placesList = new ArrayList<>();
			HashMap<String, String> placeMap = null;
			Log.d("Places", "getPlaces");

			for (int i = 0; i < placesCount; i++) {
				try {
					placeMap = getPlace((JSONObject) jsonArray.get(i));
					placesList.add(placeMap);
					Log.d("Places", "Adding places");

				} catch (JSONException e) {
					Log.d("Places", "Error in Adding places");
					e.printStackTrace();
				}
			}
			return placesList;
		}

		private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
			HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
			String placeName = "-NA-";
			String vicinity = "-NA-";
			String latitude = "";
			String longitude = "";
			String reference = "";

			Log.d("getPlace", "Entered");

			try {
				if (!googlePlaceJson.isNull("name")) {
					placeName = googlePlaceJson.getString("name");
				}
				if (!googlePlaceJson.isNull("vicinity")) {
					vicinity = googlePlaceJson.getString("vicinity");
				}
				latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
				longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
				reference = googlePlaceJson.getString("reference");
				googlePlaceMap.put("place_name", placeName);
				googlePlaceMap.put("vicinity", vicinity);
				googlePlaceMap.put("lat", latitude);
				googlePlaceMap.put("lng", longitude);
				googlePlaceMap.put("reference", reference);
				Log.d("getPlace", "Putting Places");
			} catch (JSONException e) {
				Log.d("getPlace", "Error");
				e.printStackTrace();
			}
			return googlePlaceMap;
		}
	}

	private class DownloadUrl {

		public String readUrl(String strUrl) throws IOException {
			String data = "";
			InputStream iStream = null;
			HttpURLConnection urlConnection = null;
			try {
				URL url = new URL(strUrl);

				// Creating an http connection to communicate with url
				urlConnection = (HttpURLConnection) url.openConnection();

				// Connecting to url
				urlConnection.connect();

				// Reading data from url
				iStream = urlConnection.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

				StringBuffer sb = new StringBuffer();

				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				data = sb.toString();
				Log.d("downloadUrl", data.toString());
				br.close();

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			} finally {
				iStream.close();
				urlConnection.disconnect();
			}
			return data;
		}
	}
}