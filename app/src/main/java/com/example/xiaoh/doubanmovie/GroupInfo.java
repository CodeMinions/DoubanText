package com.example.xiaoh.doubanmovie;

import java.security.acl.Group;

public class GroupInfo {//实现分组 保存每组的的相关数据
    private int mGroupID;
    // Header 的 title
    private String mTitle;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public GroupInfo(int groupId, String title) {
        this.mGroupID = groupId;
        this.mTitle = title;
    }

    public int getGroupID() {
        return mGroupID;
    }

    public void setGroupID(int groupID) {
        this.mGroupID = groupID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean isFirstViewInGroup () {
        return position == 0;
    }

}
