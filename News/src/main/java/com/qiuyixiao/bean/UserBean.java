package com.qiuyixiao.bean;

public class UserBean {
    private String name;
    private String psd;
    private String countLogo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return psd;
    }

    public String getCountLogo() {
        return countLogo;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public void setCountLogo(String countLogo) {
        this.countLogo = countLogo;
    }

    @Override
    public String toString() {
        return "账号：='" + name + '\'' +
                ", 密码：='" + psd + '\'' +
                ", 头像：='" + countLogo + '\'' +
                '}';
    }
}
