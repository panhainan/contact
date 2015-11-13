/**
 * 
 */
package com.phn.contact.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author FireOct
 * @website http://panhainan.com
 * @email panhainan@yeah.net
 * @date 2015-11-10
 */
public class People implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pId;
	private String pName;
	private List<String> pPhoneList;
	private String pEmail;
	private String pCompany;
	private String pPosition;
	private String pAddress;
	private String pWebSite;
	private String pChineseCalendarBirthDay;
	private String pGregorianCalendarBirthDay;
	private String pNotes;

	public People() {
	}

	public People(String name) {
		this.pName = name;
	}

	public People(String name, List<String> phoneList) {
		this.pName = name;
		this.pPhoneList = phoneList;
	}

	@Override
	public String toString() {
		return pName + ":" + pPhoneList + ","+pCompany+","+pPosition+","+pEmail;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public List<String> getpPhoneList() {
		return pPhoneList;
	}

	public void setpPhoneList(List<String> pPhoneList) {
		this.pPhoneList = pPhoneList;
	}

	public String getpEmail() {
		return pEmail;
	}

	public void setpEmail(String pEmail) {
		this.pEmail = pEmail;
	}

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}

	public String getpWebSite() {
		return pWebSite;
	}

	public void setpWebSite(String pWebSite) {
		this.pWebSite = pWebSite;
	}

	public String getpChineseCalendarBirthDay() {
		return pChineseCalendarBirthDay;
	}

	public void setpChineseCalendarBirthDay(String pChineseCalendarBirthDay) {
		this.pChineseCalendarBirthDay = pChineseCalendarBirthDay;
	}

	public String getpGregorianCalendarBirthDay() {
		return pGregorianCalendarBirthDay;
	}

	public void setpGregorianCalendarBirthDay(String pGregorianCalendarBirthDay) {
		this.pGregorianCalendarBirthDay = pGregorianCalendarBirthDay;
	}

	public String getpNotes() {
		return pNotes;
	}

	public void setpNotes(String pNotes) {
		this.pNotes = pNotes;
	}

	/**
	 * @return the pCompany
	 */
	public String getpCompany() {
		return pCompany;
	}

	/**
	 * @param pCompany the pCompany to set
	 */
	public void setpCompany(String pCompany) {
		this.pCompany = pCompany;
	}

	/**
	 * @return the pPosition
	 */
	public String getpPosition() {
		return pPosition;
	}

	/**
	 * @param pPosition the pPosition to set
	 */
	public void setpPosition(String pPosition) {
		this.pPosition = pPosition;
	}

	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}

}
