package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.StickyHeader;

/**
 * GroupInfo
 *
 * @author 林zero
 * @date 2018/4/11
 */

public class GroupInfo {
    //组号
    private int groupId;
    //组内位置
    private int position;
    //标题
    private String title;

    public GroupInfo(int groupId, int position, String title) {
        this.groupId = groupId;
        this.position = position;
        this.title = title;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 是否是组内第一个
     */
    public boolean isFirstViewInGroup() {
        return position == 0;
    }
}
