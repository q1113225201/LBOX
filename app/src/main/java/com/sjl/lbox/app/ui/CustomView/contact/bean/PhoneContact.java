package com.sjl.lbox.app.ui.CustomView.contact.bean;

import java.util.Comparator;

/**
 * 手机联系人
 *
 * @author SJL
 * @date 2016/8/11 23:36
 */
public class PhoneContact implements Comparator<PhoneContact> {
    private String name;
    private String mobile;
    private String namePinYin;
    private String nameFirstChar;

    public PhoneContact() {
    }

    public PhoneContact(String name, String mobile, String namePinYin) {
        this.name = name;
        this.mobile = mobile;
        this.namePinYin = namePinYin;
        this.nameFirstChar = "";
    }

    public PhoneContact(String name, String mobile, String namePinYin, String nameFirstChar) {
        this.name = name;
        this.mobile = mobile;
        this.namePinYin = namePinYin;
        this.nameFirstChar = nameFirstChar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNamePinYin() {
        return namePinYin;
    }

    public void setNamePinYin(String namePinYin) {
        this.namePinYin = namePinYin;
    }

    public String getNameFirstChar() {
        return nameFirstChar;
    }

    public void setNameFirstChar(String nameFirstChar) {
        this.nameFirstChar = nameFirstChar;
    }

    @Override
    public int compare(PhoneContact lhs, PhoneContact rhs) {
        //先根据首字母排序，再根据文字拼音
        return (lhs.getNameFirstChar().compareTo(rhs.getNameFirstChar()) == 0 || lhs.getNamePinYin().compareTo(rhs.getNamePinYin()) == 0)?0:lhs.getNameFirstChar().compareTo(rhs.getNameFirstChar());
    }
}
