/**
 * 
 */
package com.phn.contact.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phn.contact.R;
import com.phn.contact.entity.People;

/**
 * @author FireOct
 * @website http://panhainan.com
 * @email panhainan@yeah.net
 * @date 2015-11-10
 */
public class PeopleAdapter extends ArrayAdapter<People> {
	private int resourceId;

	/**
	 * @param context
	 * @param resource
	 */
	public PeopleAdapter(Context context, int textViewResourceId,
			List<People> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		People people = getItem(position);
		ViewHolder viewHolder;
		View view ;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.peopleName = (TextView) view
					.findViewById(R.id.people_name);
			view.setTag(viewHolder); // 将ViewHolder 存储在View 中
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.peopleName.setText(people.getpName());
		return view;
	}

	class ViewHolder {
		TextView peopleName;
	}
}
