/**
 * 
 */
package com.phn.contact;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * @author FireOct
 * @website http://panhainan.com
 * @email panhainan@yeah.net
 * @date 2015-11-6
 */
public class ActivityController {
	private static List<Activity> activities = new ArrayList<Activity>();
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}
	public static void finishAll(){
		for (Activity activity : activities) {
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
		
	}
	
}
