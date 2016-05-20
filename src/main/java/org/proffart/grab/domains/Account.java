package org.proffart.grab.domains;

import java.util.HashMap;
import java.util.Map;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/14/16
 * time 10:22 PM
 */
public class Account extends AbstractDomain {

    private String login;
    private String password;
    private Map<Integer, Integer> watchedList = new HashMap<Integer, Integer>();

    public Account() {
        super();
    }

    public Account(final String string) {
        super(string);
        String[] str = string.split(":");
        login = str[0];
        password = str[1];
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getCode(final String phonePrefix, final String phonePostfix) {
        final int beginIndex = phonePrefix.trim().replace("+", "").length();
        final int endIndex = login.length() - phonePostfix.trim().length();

        return login.substring(beginIndex ,endIndex);
    }

    public void watched(Video video) {
        watchedList.put(video.hashCode(), video.getSecond());
    }

    public boolean isWatched(Video video) {
        return watchedList.containsKey(video.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account && login.equals(((Account) obj).login) & password.equals(((Account) obj).password);
    }

    @Override
    public String toString() {
        return "login: " + login + " , password: " + password;
    }
}
