
/**
 * The home screen
 * @author Sethathi <Sethathi@gmail.com>
 * 
 */
package com.mobileapps.pictureonmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Main extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
    	inflator.inflate(R.menu.homemenu, menu);
    	return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.scandirectory){
			//We go to the activity which scans the directory
			startActivity(new Intent(Main.this, ImagesDisplayable.class));
    	}
		return super.onOptionsItemSelected(item);
	}

}
