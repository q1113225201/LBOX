package com.sjl.lbox.bean;

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

    public PhoneContact() {
    }

    public PhoneContact(String name, String mobile, String namePinYin) {
        this.name = name;
        this.mobile = mobile;
        this.namePinYin = namePinYin;
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

    @Override
    public int compare(PhoneContact lhs, PhoneContact rhs) {
        return lhs.getNamePinYin().compareTo(rhs.getNamePinYin())>0?1:-1;
    }
}
