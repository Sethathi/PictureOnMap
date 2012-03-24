/**
 * The home screen
 * @author Sethathi <Sethathi@gmail.com>
 * 
 */
package com.mobileapps.pictureonmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
		if (item.getItemId() == R.id.scandirectory) {
			// if the scan directory menu item is clicked
			// We go to the activity which scans the directory
			startActivity(new Intent(Main.this, ImagesDisplayable.class));

		} else if (item.getItemId() == R.id.exit) {
			//if the exit menu item is clicked, creare a dialog to confirm the action
			AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
			builder.setMessage("Are you you want to quit?");
			builder.setCancelable(false);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//Kill the application process to exit
							int pid = android.os.Process.myPid();
							android.os.Process.killProcess(pid);

						}
					});

			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}
		return super.onOptionsItemSelected(item);
	}

}
