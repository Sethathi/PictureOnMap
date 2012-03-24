package com.mobileapps.pictureonmap;

import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class PictureOnMap extends MapActivity {

	MapView mapView;
	MapController mc;
	GeoPoint p, b;

	private class PointsOverlay extends ItemizedOverlay<OverlayItem> {
		

		// our points will be in here
		private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

		private Drawable marker = null;
		private String title = "";

		// to hold a list of Picture instances to be placed on a map
		List<Picture> geoPoints = new ArrayList<Picture>();
		DmsToDegree dtd = new DmsToDegree();

		public PointsOverlay(Drawable marker) {
			super(boundCenterBottom(marker));
			this.marker = marker;
			

			ArrayList<String> s = (ArrayList<String>) getIntent().getExtras()
					.getStringArrayList("pics");
			for (int i = 0; i < s.size(); i++) {
				title = s.get(i);
				try {
					createGeoPoint(new ExifInterface("/sdcard/" + title), title);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (Picture picture : geoPoints) {
				items.add(new OverlayItem(
						new GeoPoint((int) ((Double.parseDouble(picture
								.getLongitude()) * 1E6)), (int) (Double
								.parseDouble(picture.getLatitude()) * 1E6)),
						"title", picture.getTitle()));
			}
		//
			// List<Picture> pics = datasource.getAllPictures();
			// for (Picture picture : pics) {
			// items.add(new OverlayItem( new
			// GeoPoint((int)((Double.parseDouble(picture .getLongitude()) *
			// 1E6)), (int) (Double.parseDouble(picture.getLatitude()) *
			// 1E6)),"Hey",picture.getSnippet()));
			// }

			populate();

		}

		public void createGeoPoint(ExifInterface exif, String title) {

			Picture picture = new Picture();
			picture.setTitle(title);
			// set an image's latitude coordinates after changing from DMS to
			// Degree eg 31/1,15/1,200/100 to 31.58452214
			picture.setLatitude(dtd.convertToDegree(
					exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE),
					exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)));
			picture.setLongitude(dtd.convertToDegree(
					exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),
					exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)));
			geoPoints.add(picture);

		}

		@Override
		protected OverlayItem createItem(int i) {
			return (items.get(i));
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(marker);
		}

		@Override
		protected boolean onTap(int i) {
			// Create a dialog to diplay an image title
			Dialog d = new Dialog(PictureOnMap.this);
			Window window = d.getWindow();
			window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
					WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			d.setTitle(items.get(i).getSnippet());
			d.setCancelable(true);
			d.setContentView(R.layout.dialog);
			d.show();

			// Toast.makeText(PictureOnMap.this,
			// items.get(i).getSnippet(),Toast.LENGTH_SHORT).show();
			return (true);
		}

		@Override
		public int size() {
			return (items.size());
		}
		
		
	}

	/**
	 * 
	 * Called when the activity is first created.
	 * 
	 * */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		
		mapView = (MapView) findViewById(R.id.map_view);

		mc = mapView.getController();

		// Set the zoom level
		mc.setZoom(3);

		// Set the zoom in/out controls
		mapView.setBuiltInZoomControls(true);

		List<Overlay> overlays = mapView.getOverlays();
		// create an icon to represent an image on a map
		Drawable marker = getResources().getDrawable(R.drawable.map_marker);
		// Create markers

		PointsOverlay markers = new PointsOverlay(marker);
		overlays.add(markers);

		mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}