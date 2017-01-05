package cn.lashou.entity;

/**
 * Created by luow on 2016/12/24.
 */

public class UserBean {
    private String name;
    private String HeaderIcon;

    public UserBean(String name, String headerIcon) {
        this.name = name;
        HeaderIcon = headerIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderIcon() {
        return HeaderIcon;
    }

    public void setHeaderIcon(String headerIcon) {
        HeaderIcon = headerIcon;
    }
}
