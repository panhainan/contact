package com.phn.contact;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.phn.contact.entity.People;

public class PeopleDetailsActivity extends BaseActivity implements OnClickListener {

	private People people;

	public static void activityStart(Context context, People people) {
		Intent intent = new Intent(context, PeopleDetailsActivity.class);
		intent.putExtra("people", people);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_people_details);
		initViewData();
		setOnClickEvent();
	}

	private void setOnClickEvent() {
		Button titleBackToList = (Button) findViewById(R.id.action_back_list);
		titleBackToList.setOnClickListener(this);
		Button titleEditPeople = (Button) findViewById(R.id.action_edit_people);
		titleEditPeople.setOnClickListener(this);
	}

	private void initViewData() {
		Bundle bundle = getIntent().getExtras();
		people = (People) bundle.getSerializable("people");
		TextView peopleName = (TextView) findViewById(R.id.title_name);
		peopleName.setText(people.getpName());
		TextView peopleOtherInfo = (TextView) findViewById(R.id.details_people_com_pos);
		peopleOtherInfo.setText(people.getpPosition() + "\n"
				+ people.getpCompany());
		List<String> listPhone = people.getpPhoneList();
		if (listPhone != null && listPhone.size()!=0) {
			setPhonesCallSend(listPhone, listPhone.size());
		}
		String email = people.getpEmail();
		if (email != null && !email.equals("")) {
			setMail(email);
		}

	}

	private void setMail(String email) {
		TableLayout detailsPeopleTable = (TableLayout) findViewById(R.id.details_people_table);
		TableRow row = new TableRow(this);
		TextView txtView = new TextView(this);
		txtView.setText(email);
		txtView.setTextSize(20);
		txtView.setGravity(Gravity.LEFT);
		txtView.setWidth(500);
		Button btn = new Button(this);
		btn.setText("发邮件");
		btn.setTag(email);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.parse("mailto:" + v.getTag().toString()));
				startActivity(Intent.createChooser(emailIntent, "请选择邮件类应用"));
			}

		});
		row.addView(txtView);
		row.addView(btn);
		detailsPeopleTable.addView(row);
	}

	private void setPhonesCallSend(List<String> listPhone, int phoneCount) {
		TableLayout detailsPeopleTable = (TableLayout) findViewById(R.id.details_people_table);

		for (int index = 0; index < phoneCount; index++) {
			StringBuffer stbf = new StringBuffer(listPhone.get(index));
			TableRow row = new TableRow(this);
			TextView txtView = new TextView(this);
			txtView.setText(stbf.toString());
			txtView.setTag(stbf.toString());
			txtView.setTextSize(20);
			txtView.setGravity(Gravity.LEFT);
			txtView.setWidth(500);
			txtView.setPadding(10, 10, 10, 0);
			txtView.setHeight(80);
			txtView.setClickable(true);
			setPressedBg(txtView, "#ffffff", "#33B5E5", "#33B5E5");
			setTextPressedBg(txtView, "#000000", "#ffffff", "#ffffff");
			txtView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + v.getTag().toString()));
					startActivity(callIntent);
				}
			});
			Button btn = new Button(this);
			btn.setText("短信");
			btn.setTag(stbf.toString());
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri
							.parse("smsto:" + v.getTag().toString()));
					startActivity(sendIntent);
				}

			});
			row.addView(txtView);
			row.addView(btn);
			detailsPeopleTable.addView(row);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_back_list:
			Intent backIntent = new Intent(this, MainActivity.class);
			startActivity(backIntent);
			break;

		case R.id.action_edit_people:
			PeopleEditActivity.activityStart(this, people);
			// Toast.makeText(this, "您点击了编辑按钮，不过目前此功能还没有开放，见谅!",
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	/**
	 * 设置单击背景色
	 * 
	 * @param view
	 *            控件
	 * @param normal
	 *            未选择时的背景色
	 * @param focused
	 *            选择时的背景色
	 * @param pressed
	 *            选择时的背景色
	 */
	public void setPressedBg(View view, String normal, String focused,
			String pressed) {
		int[][] states = new int[6][];
		states[0] = new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused };
		states[2] = new int[] { android.R.attr.state_enabled };
		states[3] = new int[] { android.R.attr.state_focused,
				android.R.attr.state_window_focused };
		states[4] = new int[] { android.R.attr.state_window_focused };

		StateListDrawable bg = new StateListDrawable();
		bg.addState(states[0], new ColorDrawable(Color.parseColor(pressed)));
		bg.addState(states[3], new ColorDrawable(Color.parseColor(focused)));
		bg.addState(states[2], new ColorDrawable(Color.parseColor(normal)));

		view.setBackgroundDrawable(bg);
	}

	/**
	 * 对TextView设置不同状态时其文字颜色。
	 * 
	 * @param textView
	 *            控件
	 * @param normal
	 *            未选择时的文字颜色
	 * @param pressed
	 *            选择时的文字颜色
	 * @param focused
	 *            选择时的文字颜色
	 */
	private void setTextPressedBg(TextView textView, String normal,
			String pressed, String focused) {
		int[][] states = new int[6][];
		states[0] = new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled };
		states[1] = new int[] { android.R.attr.state_enabled,
				android.R.attr.state_focused };
		states[2] = new int[] { android.R.attr.state_enabled };
		states[3] = new int[] { android.R.attr.state_focused };
		states[4] = new int[] { android.R.attr.state_window_focused };
		states[5] = new int[] {};

		int normalColor = Color.parseColor(normal);
		int pressedColor = Color.parseColor(pressed);
		int focusedColor = Color.parseColor(focused);

		ColorStateList colorList = new ColorStateList(states, new int[] {
				pressedColor, focusedColor, normalColor, focusedColor,
				focusedColor, normalColor });

		textView.setTextColor(colorList);
	}

	@Override
	public void onBackPressed() {
		// 点击back（返回键）返回到MainActivity
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
//		setResult(RESULT_OK, intent);
		finish();
	}
}
