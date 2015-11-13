package com.phn.contact;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.phn.contact.dao.PeopleDao;
import com.phn.contact.entity.People;

public class MainActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private List<String> listPeopleName;
	private final int CONTEXT_MENU_EDIT = 0x111;
	private final int CONTEXT_MENU_DELETE = 0x112;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setContentViewDate();
		setOnClickEvent();
	}
	private void setOnClickEvent() {
		Button titleEditPeople = (Button) findViewById(R.id.action_add_people);
		titleEditPeople.setOnClickListener(this);
	}
	private void setContentViewDate() {
		listPeopleName = PeopleDao.getContactsName(getContentResolver());
		TextView peopleCount = (TextView) findViewById(R.id.all_count_people);
		if(listPeopleName!=null&&listPeopleName.size()!=0){
			ArrayAdapter<String> peopleNameAdapter = new ArrayAdapter<String>(
					MainActivity.this, R.layout.main_contact_item, listPeopleName);
			peopleCount.setText("(" + listPeopleName.size() + "位)");
			ListView listView = (ListView) findViewById(R.id.contact_list);
			listView.setAdapter(peopleNameAdapter);
			listView.setOnItemClickListener(this);
			registerForContextMenu(listView);
		}else{
			peopleCount.setText("(0位)");
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
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, CONTEXT_MENU_EDIT, 0, "编辑");
		menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		String peopleName = listPeopleName.get(info.position);
		switch (item.getItemId()) {
		case CONTEXT_MENU_EDIT:
			PeopleEditActivity.activityStart(this, PeopleDao.getPeopleByName(getContentResolver(), peopleName));
//			Toast.makeText(this, "您点击了编辑" + peopleName, Toast.LENGTH_SHORT)
//					.show();
			break;
		case CONTEXT_MENU_DELETE:
			deletePeopleOption(peopleName);
			break;
		default:
			break;
		}
		return true;
	}

	private void deletePeopleOption(final String peopleName) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("警告");
		dialogBuilder.setMessage("您确定要删除联系人“" + peopleName + "”吗？");
		dialogBuilder.setCancelable(true);
		dialogBuilder.setNegativeButton("取消", null);
		dialogBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						PeopleDao.deletePeopleByName(getContentResolver(),
								peopleName);
						listPeopleName = null;
						setContentViewDate();
					}
				});
		AlertDialog alertDialog = dialogBuilder.create();
		// 需要设置AlertDialog的类型，保证在广播接收器中可以正常弹出
		alertDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.show();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String peopleName = listPeopleName.get(position);
		People people = PeopleDao.getPeopleByName(getContentResolver(),
				peopleName);
		PeopleDetailsActivity.activityStart(this, people);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_add_people:
			Intent intent = new Intent(this, PeopleAddActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	@Override
	public void onBackPressed() {
		ActivityController.finishAll();
	}
}
