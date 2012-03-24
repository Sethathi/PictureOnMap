package com.mobileapps.pictureonmap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

//for testing 


public class GetExtras extends Activity {

	DmsToDegree dtd = new DmsToDegree();
	List<Picture> geoPoints = new ArrayList<Picture>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_extras);
		String title = "";

		TextView tv = (TextView) findViewById(R.id.extras);

		ArrayList<String> s = (ArrayList<String>) getIntent().getExtras()
				.getStringArrayList("pics");
		for (int i = 0; i < s.size(); i++) {
			title = s.get(i);
			//tv.setText(title);
			try {
				createGeoPoint(new ExifInterface("/sdcard/" + title), title);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (Picture picture : geoPoints) {
			tv.setText(" " + picture.getLongitude());
		}

	}

	public void createGeoPoint(ExifInterface exif, String title) {

		Picture picture = new Picture();
		picture.setTitle(title);
		picture.setLatitude(dtd.convertToDegree(
				exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE),
				exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)));
		picture.setLongitude(dtd.convertToDegree(
				exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),
				exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)));
		geoPoints.add(picture);

	}

}
