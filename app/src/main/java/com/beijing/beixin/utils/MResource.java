package com.beijing.beixin.utils;

import android.content.Context;
import android.util.Log;

public class MResource {

	public static int getStyleByName(Context context, String name) {
		return getIdByName(context, "style", name);
	}

	public static int getLayoutByName(Context context, String name) {
		return getIdByName(context, "layout", name);
	}

	public final static int getIdByName(Context context, String name) {
		return getIdByName(context, "id", name);
	}

	public static int getAnimByName(Context context, String name) {
		return getIdByName(context, "anim", name);
	}

	public static int getMipmapByName(Context context, String name) {
		return getIdByName(context, "mipmap", name);
	}

	public static int getDimenByName(Context context, String name) {
		return getIdByName(context, "dimen", name);
	}

	private static int getIdByName(Context context, String className, String name) {
		String packageName = context.getPackageName();
		Class r = null;
		int id = 0;
		try {
			r = Class.forName(packageName + ".R");
			Class[] classes = r.getClasses();
			Class desireClass = null;

			for (int i = 0; i < classes.length; ++i) {
				if (classes[i].getName().split("\\$")[1].equals(className)) {
					desireClass = classes[i];
					break;
				}
			}

			if (desireClass != null)
				id = desireClass.getField(name).getInt(desireClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return id;
	}

}
