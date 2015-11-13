/**
 * 
 */
package com.phn.contact.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.util.Log;

import com.phn.contact.entity.People;

/**
 * @author FireOct
 * @website http://panhainan.com
 * @email panhainan@yeah.net
 * @date 2015-11-10
 */
public class PeopleDao {
	public static List<String> getContactsName(ContentResolver contentResolver) {
		List<String> listPeopleName = null;
		Cursor cursor = null;
		try {
			cursor = contentResolver.query(
					ContactsContract.Contacts.CONTENT_URI,
					new String[] { ContactsContract.Contacts.DISPLAY_NAME },
					null, null, StructuredName.SORT_KEY_PRIMARY);
			listPeopleName = new ArrayList<String>();
			while (cursor.moveToNext()) {
				listPeopleName
						.add(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return listPeopleName;

	}

	public static People getPeopleByName(ContentResolver contentResolver,
			String name) {
		People people = null;
		Cursor cursor = null;
		try {
			cursor = contentResolver.query(
					ContactsContract.Contacts.CONTENT_URI, new String[] {
							ContactsContract.Contacts.DISPLAY_NAME,
							ContactsContract.Contacts._ID,
							ContactsContract.Contacts.HAS_PHONE_NUMBER },
					ContactsContract.Contacts.DISPLAY_NAME + "=?",
					new String[] { name }, null);
			people = new People(name);
			if (cursor.moveToFirst()) {
				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				people.setpId(contactId);
				people.setpEmail(getEamilByContactId(contentResolver, contactId));
				List<String> list = new ArrayList<String>(2);
				list = getOrganizationByContactId(contentResolver, contactId);

				people.setpCompany(list.get(0));
				people.setpPosition(list.get(1));
				int hasNumberCount = cursor
						.getInt(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (hasNumberCount >= 1) {
					people.setpPhoneList(getPhoneListByContactId(
							contentResolver, contactId));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return people;
	}

	/**
	 * @param contentResolver
	 * @param contactId
	 */
	private static List<String> getOrganizationByContactId(
			ContentResolver contentResolver, String contactId) {
		List<String> list = new ArrayList<String>(2);
		Cursor organizationCursor = null;
		String company = "";
		String position = "";
		try {
			organizationCursor = contentResolver
					.query(ContactsContract.Data.CONTENT_URI,
							null,
							ContactsContract.Data.CONTACT_ID + " = ? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									contactId,
									ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE },
							null);
			if (organizationCursor.moveToFirst()) {
				company = organizationCursor
						.getString(organizationCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA1));
				position = organizationCursor
						.getString(organizationCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA4));
			}
			list.add(company);
			list.add(position);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (organizationCursor != null) {
				organizationCursor.close();
			}
		}
		return list;
	}

	/**
	 * @param contentResolver
	 * @param contactId
	 * @return
	 */
	private static List<String> getPhoneListByContactId(
			ContentResolver contentResolver, String contactId) {
		List<String> listPhone = null;
		Cursor phoneCursor = null;
		try {
			phoneCursor = contentResolver
					.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=?", new String[] { contactId }, null);
			listPhone = new ArrayList<String>(2);
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor
						.getString(phoneCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				listPhone.add(phoneNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (phoneCursor != null) {
				phoneCursor.close();
			}
		}
		return listPhone;
	}

	/**
	 * @param contentResolver
	 * @param contactId
	 */
	private static String getEamilByContactId(ContentResolver contentResolver,
			String contactId) {
		Cursor emailCursor = null;
		String email = null;
		try {
			emailCursor = contentResolver
					.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							new String[] { ContactsContract.CommonDataKinds.Email.DATA },
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ "=?", new String[] { contactId }, null);
			if (emailCursor.moveToFirst()) {
				email = emailCursor
						.getString(emailCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (emailCursor != null) {
				emailCursor.close();
			}
		}
		return email;
	}

	public static boolean deletePeopleByName(ContentResolver contentResolver,
			String name) {
		Cursor cursor = null;
		try {
			cursor = contentResolver.query(
					ContactsContract.Contacts.CONTENT_URI,
					new String[] { ContactsContract.Contacts._ID },
					ContactsContract.Contacts.DISPLAY_NAME + "=?",
					new String[] { name }, null);
			if (cursor.moveToFirst()) {
				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));

				int result = contentResolver.delete(
						ContactsContract.Data.CONTENT_URI,
						ContactsContract.Data.RAW_CONTACT_ID + "=?",
						new String[] { contactId });
				Log.d("删除操作", "返回结果：" + result);
				contentResolver.delete(ContactsContract.Contacts.CONTENT_URI,
						ContactsContract.Contacts._ID + "=?",
						new String[] { contactId });
				contentResolver.delete(
						ContactsContract.RawContacts.CONTENT_URI,
						ContactsContract.RawContacts._ID + "=?",
						new String[] { contactId });
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return true;
	}

	public static boolean addPeople(ContentResolver contentResolver,
			People people) {
		ContentValues values = new ContentValues();
		Uri uri = contentResolver.insert(
				ContactsContract.RawContacts.CONTENT_URI, values);
		long raw_contact_id = ContentUris.parseId(uri);
		values.clear();
		saveContacts(contentResolver, people, raw_contact_id);
		return true;
	}

	/**
	 * @param contentResolver
	 * @param people
	 * @param values
	 * @param raw_contact_id
	 */
	private static void saveContacts(ContentResolver contentResolver,
			People people, long raw_contact_id) {
		ContentValues values = new ContentValues();
		values.put(ContactsContract.Data.RAW_CONTACT_ID, raw_contact_id);
		values.put(ContactsContract.Data.MIMETYPE,
				"vnd.android.cursor.item/name");
		values.put(ContactsContract.Data.DATA1, people.getpName());
		values.put(ContactsContract.Data.DATA2, people.getpName());
		contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
		values.clear();
		List<String> listPhone = people.getpPhoneList();
		for (int i = 0; i < listPhone.size(); i++) {
			values.put(ContactsContract.Data.RAW_CONTACT_ID, raw_contact_id);
			values.put(ContactsContract.Data.MIMETYPE,
					"vnd.android.cursor.item/phone_v2");
			values.put(ContactsContract.Data.DATA1, listPhone.get(i));
			values.put(ContactsContract.Data.DATA2, "2");
			contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
			values.clear();
		}
		values.put(ContactsContract.Data.RAW_CONTACT_ID, raw_contact_id);
		values.put(ContactsContract.Data.MIMETYPE,
				"vnd.android.cursor.item/email_v2");
		values.put(ContactsContract.Data.DATA1, people.getpEmail());
		values.put(ContactsContract.Data.DATA2, "2");
		contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
		values.clear();
		values.put(ContactsContract.Data.RAW_CONTACT_ID, raw_contact_id);
		values.put(ContactsContract.Data.MIMETYPE,
				"vnd.android.cursor.item/organization");
		values.put(ContactsContract.Data.DATA1, people.getpCompany());
		values.put(ContactsContract.Data.DATA4, people.getpPosition());
		contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
		values.clear();
	}

	public static void editPeople(ContentResolver contentResolver,
			People people, String raw_contact_id) {
		updateContacts(contentResolver, people, raw_contact_id);
	}

	private static void updateContacts(ContentResolver contentResolver,
			People people, String raw_contact_id) {
		contentResolver.delete(ContactsContract.Data.CONTENT_URI, ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
				+ ContactsContract.Data.MIMETYPE + "=?", new String[] { raw_contact_id, Phone.CONTENT_ITEM_TYPE });
		ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();
		cpo.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] { raw_contact_id,
								StructuredName.CONTENT_ITEM_TYPE })
				.withValue(StructuredName.DISPLAY_NAME, people.getpName())
				.build());

		List<String> listPhone = people.getpPhoneList();
		Log.d("编辑，电话号码", listPhone.toString());
		for (int i = 0; i < listPhone.size(); i++) {
			Log.d("编辑，电话号码", listPhone.get(i));
			cpo.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValue(ContactsContract.Data.RAW_CONTACT_ID,
							raw_contact_id)
					.withValue(ContactsContract.Data.MIMETYPE,
							Phone.CONTENT_ITEM_TYPE)
					.withValue(Phone.TYPE, String.valueOf(2))
					.withValue(Phone.NUMBER, listPhone.get(i)).build());

		}
		cpo.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] { raw_contact_id, Email.CONTENT_ITEM_TYPE })
				.withValue(Email.DATA, people.getpEmail()).build());
		cpo.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
								+ ContactsContract.Data.MIMETYPE + "=?",
						new String[] { raw_contact_id,
								Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.DATA1, people.getpCompany())
				.withValue(Organization.DATA4, people.getpPosition()).build());

		try {
			contentResolver.applyBatch(ContactsContract.AUTHORITY, cpo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
