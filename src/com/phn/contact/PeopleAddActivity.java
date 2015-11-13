package com.phn.contact;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phn.contact.dao.PeopleDao;
import com.phn.contact.entity.People;

public class PeopleAddActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_people_add);
		setOnClickEvent();
	}

	private void setOnClickEvent() {
		Button titleCancelToList = (Button) findViewById(R.id.action_cancel_to_list);
		titleCancelToList.setOnClickListener(this);
		Button titleSavePeople = (Button) findViewById(R.id.action_save_people);
		titleSavePeople.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_cancel_to_list:
			Intent backIntent = new Intent(this, MainActivity.class);
			startActivity(backIntent);
			break;

		case R.id.action_save_people:
			savePeople();
			// Toast.makeText(this, "您点击了确定按钮，不过目前此功能还没有开放，见谅!",
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	private void savePeople() {
		EditText add_people_name = (EditText) findViewById(R.id.add_people_name);
		String name = add_people_name.getText().toString();
		if (name != null && name != "" && !name.equals("")) {
			People people = new People();
			people.setpName(name);
			EditText add_people_company = (EditText) findViewById(R.id.add_people_company);
			people.setpCompany(add_people_company.getText().toString());
			EditText add_people_position = (EditText) findViewById(R.id.add_people_position);
			people.setpPosition(add_people_position.getText().toString());
			EditText add_people_phone = (EditText) findViewById(R.id.add_people_phone);
			List<String> listPhone = new ArrayList<String>();
			listPhone.add(add_people_phone.getText().toString());
			people.setpPhoneList(listPhone);
			EditText add_people_email = (EditText) findViewById(R.id.add_people_email);
			people.setpEmail(add_people_email.getText().toString());
			PeopleDao.addPeople(getContentResolver(), people);
			Log.d("添加联系人", people.toString());
			People people111 = PeopleDao.getPeopleByName(getContentResolver(),
					people.getpName());
			PeopleDetailsActivity.activityStart(this, people111);
		} else {
			Toast.makeText(this, "必须填写联系人姓名!", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onBackPressed() {
		// 点击back（返回键）返回到MainActivity
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
