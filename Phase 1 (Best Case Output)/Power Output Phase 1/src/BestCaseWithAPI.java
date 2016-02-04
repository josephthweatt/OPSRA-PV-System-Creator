import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class BestCaseWithAPI {
	final static private String apiSite = "http://api.sunrise-sunset.org/";
	final static private String format = "json?";
	final private static String resultKey = "results";
	final private static String sunriseKey = "sunrise";
	final private static String sunsetKey = "sunset";
	
	private static String latitude, longitude; //three JSON variables
	private static String sunrise, sunset;
	private static double sunriseDbl, sunsetDbl;
	private static double daylightHours;

	public static void main(String[] args) throws IOException, JSONException {
		getDaylightHours();
		
		System.out.println(daylightHours);
	}

	public static void getDaylightHours() throws IOException, JSONException {
		latitude = "lat=" + 33.45; //az lat
		longitude = "lng=" + 112.0667; //az long
		String url = apiSite + format + latitude + "&"+ longitude;
		String source = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
		JSONObject mainObject = new JSONObject(source);
		mainObject = mainObject.getJSONObject(resultKey);
		sunrise = mainObject.getString(sunriseKey);
		sunset = mainObject.getString(sunsetKey);
		
		String[] dubs = sunrise.split(":");	
		sunriseDbl = Double.parseDouble(dubs[0]) + Double.parseDouble(dubs[1]) / 60;
		
		dubs = sunset.split(":");
		sunsetDbl = Double.parseDouble(dubs[0]) + Double.parseDouble(dubs[1]) / 60;
		if (sunsetDbl < 12) {
			sunsetDbl += 12;
		}
		
		daylightHours = sunsetDbl - sunriseDbl;
	}
	
}
