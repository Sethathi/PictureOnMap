/**
 * On this activity, images which have a geotag will be displayed
 * 
 * @author Sethathi <Sethathi@gmail.com>
 * 
 */


package com.mobileapps.pictureonmap;

import java.io.File;
import java.io.FilenameFilter;

import android.app.Activity;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ImagesDisplayable extends Activity {
	boolean is_geotag_set = true;
	int pics_with_geotag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.images_displayable_on_map);
		TextView tv = (TextView) findViewById(R.id.images_list);
		String picture_titles = "";
		String pic_title = "";

		File f = new File("/sdcard/");
		// Get all files with a JPG extension
		File[] files = f.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("jpg");
			}
		});

		// get the names of our images
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			pic_title = file.getName();
			try {
				if (checkGeoTag(new ExifInterface("/sdcard/" + pic_title))) {
					++pics_with_geotag;
					picture_titles += file.getName() + "\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
			}

		}
		//Display a list of out images
		tv.setText(picture_titles);

	}

	/**
	 * Checks if the an image has a latitude tag set, if its sets its diplayable
	 * on a map
	 * 
	 * @param exif
	 * @return true if latitude tag set, false otherwise
	 */
	public boolean checkGeoTag(ExifInterface exif) {

		if (exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) == null
				&& exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) == null)
			is_geotag_set = false;

		return is_geotag_set;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.display_on_a_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.displayonamap) {
			// if there are images with a geotag, we make it possible for the
			// PictureOnMap activity to be started where those images will be
			// displayed on a map.
			if (pics_with_geotag > 0) {
				startActivity(new Intent(ImagesDisplayable.this,
						PictureOnMap.class));
			}

		}
		return super.onOptionsItemSelected(item);
	}

}
