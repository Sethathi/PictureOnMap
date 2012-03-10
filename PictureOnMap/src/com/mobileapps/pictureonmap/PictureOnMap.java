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
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;


public class PictureOnMap extends MapActivity {
	private PictureDatasource datasource;
	MapView mapView;
	MapController mc;
	GeoPoint p, b;

	private class PointsOverlay extends ItemizedOverlay<OverlayItem> {
		private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		private Drawable marker = null;

		public PointsOverlay(Drawable marker) {
			super(boundCenterBottom(marker));
			this.marker = marker;
			
			List<Picture> pics = datasource.getAllPictures();
			
			for (Picture picture : pics) {
				items.add(new OverlayItem(new GeoPoint((int)(( Double.parseDouble(picture.getLongitude()) *1E6)), (int)( Double.parseDouble(picture.getLatitude()) *1E6)), "Hey",picture.getSnippet()));
			}
			
			
			 
			
			/*items.add(new OverlayItem(new GeoPoint(
					(int) (40.748963847316034 * 1E6),
					(int) (-72.96807193756104 * 1E6)), "Rhodes University",
					"Standard Bank IT Chalenge"));
			
			items.add(new OverlayItem(new GeoPoint(
					(int) (35.748963847316034 * 1E6),
					(int) (-62.96807193756104 * 1E6)), "Rhodes University",
					"Standard Bank Jazz Festival"));
			
			items.add(new OverlayItem(new GeoPoint(
					(int) (25.748963847316034 * 1E6),
					(int) (-52.96807193756104 * 1E6)), "Rhodes University",
					"Graduation"));
			
			items.add(new OverlayItem(new GeoPoint(
					(int) (37.748963847316034 * 1E6),
					(int) (-42.96807193756104 * 1E6)), "Rhodes University",
					"Arts Festival"));
			
			items.add(new OverlayItem(new GeoPoint(
					(int) (15.748963847316034 * 1E6),
					(int) (-82.96807193756104 * 1E6)), "Rhodes University",
					"Graduation 2009"));*/
			populate();
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
			Toast.makeText(PictureOnMap.this, items.get(i).getSnippet(),
			Toast.LENGTH_SHORT).show();
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
		
		datasource = new PictureDatasource(this);
		datasource.open();
		
		datasource.createPictures("Standard Bank IT Challenge","At the Heats", "-72.96807193756104","42.748963847316034");
		datasource.createPictures("Rhodes University","2011 Graduation", "-75.96807193756104","44.748963847316034");
		datasource.createPictures("Durban Internation Airport","Flying of to London", "-79.96807193756104","39.748963847316034");
		datasource.createPictures("Durban Internation Airport","Plane about to take off", "-79.96807193756104","39.948963847316034");

		mapView = (MapView) findViewById(R.id.map_view);

		mc = mapView.getController();
		
		
		//Set the zoom level
		mc.setZoom(3);
		//Set the zoom in/out controls
		mapView.setBuiltInZoomControls(true);
		

		List<Overlay> overlays = mapView.getOverlays();
		Drawable marker = getResources().getDrawable(R.drawable.map_marker);
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