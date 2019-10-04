package com.liujiajun.domain;

public class CheckUsername {

    private String username;
    private boolean flag; //用户存在 返回false  用户不存在 返回true
    private String errorMsg;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "CheckUsername{" +
                "username='" + username + '\'' +
                ", flag=" + flag +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
