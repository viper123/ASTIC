package ro.info.astic;

import java.util.prefs.Preferences;

public class PrefUtils {
	static private Preferences pref;
	
	static{
		pref = Preferences.userRoot();
	}

	public static void save(String name,boolean data){
		pref.putBoolean(name, data);
	}
	
	public static boolean load(String name){
		return pref.getBoolean(name, false);
	}
}
