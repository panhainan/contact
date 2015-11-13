package com.phn.contact;

import java.util.ArrayList;
import java.util.List;

import com.phn.contact.dao.PeopleDao;
import com.phn.contact.entity.People;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PeopleEditActivity extends BaseActivity implements OnClickListener {
	private People people;
	private int people_phone_count = 0;

	public static void activityStart(Context context, People people) {
		Intent intent = new Intent(context, PeopleEditActivity.class);
		intent.putExtra("people", people);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_people_edit);
		initViewData();
		setOnClickEvent();
	}

	private void setOnClickEvent() {
		Button titleCancelToDetails = (Button) findViewById(R.id.action_cancel_to_details);
		titleCancelToDetails.setOnClickListener(this);
		Button titleUpdatePeople = (Button) findViewById(R.id.action_update_people);
		titleUpdatePeople.setOnClickListener(this);
	}

	private void initViewData() {
		Bundle bundle = getIntent().getExtras();
		people = (People) bundle.getSerializable("people");
		Log.d("编辑联系人", people.toString());
		TextView raw_contact_id = (TextView) findViewById(R.id.raw_contact_id);
		raw_contact_id.setText(people.getpId());
		EditText edit_people_name = (EditText) findViewById(R.id.edit_people_name);
		edit_people_name.setText(people.getpName());
		EditText edit_people_company = (EditText) findViewById(R.id.edit_people_company);
		edit_people_company.setText(people.getpCompany());
		EditText edit_people_position = (EditText) findViewById(R.id.edit_people_position);
		edit_people_position.setText(people.getpPosition());
		EditText edit_people_email = (EditText) findViewById(R.id.edit_people_email);
		edit_people_email.setText(people.getpEmail());
		List<String> listPhone = people.getpPhoneList();
		if (listPhone != null) {
			people_phone_count = listPhone.size();
			if (people_phone_count >= 1) {
				switch (people_phone_count) {
				case 6:
					EditText edit_people_phone5 = (EditText) findViewById(R.id.edit_people_phone5);
					edit_people_phone5.setText(listPhone.get(5));
					findViewById(R.id.tablerow_phone_5).setVisibility(
							View.VISIBLE);
				case 5:
					EditText edit_people_phone4 = (EditText) findViewById(R.id.edit_people_phone4);
					edit_people_phone4.setText(listPhone.get(4));
					findViewById(R.id.tablerow_phone_4).setVisibility(
							View.VISIBLE);
				case 4:
					EditText edit_people_phone3 = (EditText) findViewById(R.id.edit_people_phone3);
					edit_people_phone3.setText(listPhone.get(3));
					findViewById(R.id.tablerow_phone_3).setVisibility(
							View.VISIBLE);
				case 3:
					EditText edit_people_phone2 = (EditText) findViewById(R.id.edit_people_phone2);
					edit_people_phone2.setText(listPhone.get(2));
					findViewById(R.id.tablerow_phone_2).setVisibility(
							View.VISIBLE);
				case 2:
					EditText edit_people_phone1 = (EditText) findViewById(R.id.edit_people_phone1);
					edit_people_phone1.setText(listPhone.get(1));
					findViewById(R.id.tablerow_phone_1).setVisibility(
							View.VISIBLE);
				case 1:
					EditText edit_people_phone = (EditText) findViewById(R.id.edit_people_phone);
					edit_people_phone.setText(listPhone.get(0));
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_cancel_to_details:
			PeopleDetailsActivity.activityStart(this, people);
			break;

		case R.id.action_update_people:
			editPeople();
			// Toast.makeText(this, "您点击了确定按钮，不过目前此功能还没有开放，见谅!",
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	private void editPeople() {
		People editPeople = new People();
		EditText edit_people_name = (EditText) findViewById(R.id.edit_people_name);
		editPeople.setpName(edit_people_name.getText().toString());
		EditText edit_people_company = (EditText) findViewById(R.id.edit_people_company);
		editPeople.setpCompany(edit_people_company.getText().toString());
		EditText edit_people_position = (EditText) findViewById(R.id.edit_people_position);
		editPeople.setpPosition(edit_people_position.getText().toString());

		List<String> listPhone = getPhoneList();
		editPeople.setpPhoneList(listPhone);

		EditText edit_people_email = (EditText) findViewById(R.id.edit_people_email);
		editPeople.setpEmail(edit_people_email.getText().toString());

		TextView raw_contact_id_textView = (TextView) findViewById(R.id.raw_contact_id);
		PeopleDao.editPeople(getContentResolver(), editPeople,
				raw_contact_id_textView.getText().toString());
		People people111 = PeopleDao.getPeopleByName(getContentResolver(),
				editPeople.getpName());
		PeopleDetailsActivity.activityStart(this, people111);
	}

	private List<String> getPhoneList() {
		List<String> listPhone = new ArrayList<String>();
		switch (people_phone_count) {
		case 6:
			String phone6 = ((EditText) findViewById(R.id.edit_people_phone5))
					.getText().toString();
			if (phone6 != null && !phone6.trim().equals("")) {
				listPhone.add(phone6);
			}
		case 5:
			String phone5 = ((EditText) findViewById(R.id.edit_people_phone4))
					.getText().toString();
			if (phone5 != null && !phone5.trim().equals("")) {
				listPhone.add(phone5);
			}
		case 4:
			String phone4 = ((EditText) findViewById(R.id.edit_people_phone3))
					.getText().toString();
			if (phone4 != null && !phone4.trim().equals("")) {
				listPhone.add(phone4);
			}
		case 3:
			String phone3 = ((EditText) findViewById(R.id.edit_people_phone2))
					.getText().toString();
			if (phone3 != null && !phone3.trim().equals("")) {
				listPhone.add(phone3);
			}
		case 2:
			String phone2 = ((EditText) findViewById(R.id.edit_people_phone1))
					.getText().toString();
			if (phone2 != null && !phone2.trim().equals("")) {
				listPhone.add(phone2);
			}
		case 1:
		case 0:
			String phone = ((EditText) findViewById(R.id.edit_people_phone))
					.getText().toString();
			if (phone != null && !phone.trim().equals("")) {
				listPhone.add(phone);
			}
		}
		return listPhone;
	}

	@Override
	public void onBackPressed() {
		// 点击back（返回键）返回到DetailsActivity并携带数据
		Intent intent = new Intent(this, PeopleDetailsActivity.class);
		intent.putExtra("people", people);
		startActivity(intent);
		finish();
	}
}
