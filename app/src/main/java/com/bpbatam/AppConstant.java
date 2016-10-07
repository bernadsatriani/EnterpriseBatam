package com.bpbatam;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONObject;


public final class AppConstant {
	// Shared Preferences
	public static SharedPreferences pref;

	// Editor for Shared preferences
	public static SharedPreferences.Editor editor;
	public static final String LANGUAGE = "language";
	public static final String SESSION_PREFERENCE = "sesion_preference";
	public static final String EMPTY_STRING = "";
	public static final String LANGUAGE_DEFAULT = "en";
	public static String HASHID ;
	public static String REQID ;
	public static String KEY_PASSWORD = "S.1bc29400";
	public static String KEY_USER = "bc12b22e93149c175cc034f69b031f35";
	public static String PILIH_PESAN = "1" ;
	public static String SEMUA_PESAN = "2" ;
	public static String TIDAK_PESAN = "3" ;


	public static String ACTIVITY_FROM;


	public static final String DOMAIN_URL = "http://182.253.221.109:44311";
	public final static String API_VERSION = "/";

	public static JSONArray gPostJson;
	public static JSONObject gObjJson;
	public static SQLiteDatabase myDb;
	public static String FOLDER_DOWNLOAD = "/Batam/Enterprise/Download";
	public static String STORAGE_CARD = Environment.getExternalStorageDirectory().toString() + "/Batam/Enterprise";

	public static String[] menuList;
	public static int[] menuIcon;
	public static int[] menuIconSelected;
	public static String PDFVIEW_FROM;
	public static String PDFVIEW_SOURCE;
	public static String PDF_FILENAME;
}
