package cn.lashou.entity;

import java.io.Serializable;

/**
 * Created by luow on 2016/8/18.
 */

public class HomeImgBean implements Serializable {
    private String mIconName;
    private int mIconId;

    public HomeImgBean(String iconName, int iconId) {
        mIconName = iconName;
        mIconId = iconId;
    }

    public String getIconName() {
        return mIconName;
    }

    public void setIconName(String iconName) {
        mIconName = iconName;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }
}
