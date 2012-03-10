package com.mobileapps.pictureonmap;

import java.util.List;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PictureDatasource {
	// Database fields
		private SQLiteDatabase database;
		private SQLiteHelper dbHelper;
		private String[] allColumns = { SQLiteHelper.COLUMN_ID,SQLiteHelper.COLUMN_SNIPPET, SQLiteHelper.COLUMN_LAT, SQLiteHelper.COLUMN_LONG };

		public PictureDatasource(Context context) {
			dbHelper = new SQLiteHelper(context);
			
			
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
			
		}

		public void close() {
			dbHelper.close();
		}

		public Picture createPictures(String title,String snippet, String lat, String lng) {
			ContentValues values = new ContentValues();
			
			values.put(SQLiteHelper.COLUMN_SNIPPET, snippet);
			values.put(SQLiteHelper.COLUMN_LAT, lat);
			values.put(SQLiteHelper.COLUMN_LONG, lng);
			
			long insertId = database.insert(SQLiteHelper.TABLE_PICTURES, null,
					values);
			// To show how to query
			Cursor cursor = database.query(SQLiteHelper.TABLE_PICTURES,
					allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			return cursorToPicture(cursor);
		}

		/*public void deleteComment(Comment comment) {
			long id = comment.getId();
			System.out.println("Comment deleted with id: " + id);
			database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
					+ " = " + id, null);
		}
*/
		public List<Picture> getAllPictures() {
			List<Picture> pictures = new ArrayList<Picture>();
			Cursor cursor = database.query(SQLiteHelper.TABLE_PICTURES,
					allColumns, null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Picture picture = cursorToPicture(cursor);
				pictures.add(picture);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return pictures;
		}


		private Picture cursorToPicture(Cursor cursor) {
			Picture picture = new Picture();
			picture.setId(cursor.getLong(0));
			picture.setSnippet(cursor.getString(1));
			picture.setLatitude(cursor.getString(2));
			picture.setLongitude(cursor.getString(3));
			return picture;
		}

}
