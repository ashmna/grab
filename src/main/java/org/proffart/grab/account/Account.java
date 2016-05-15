package org.proffart.grab.account;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/14/16
 * time 10:22 PM
 */
public class Account {
    private String login;
    private String password;

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
}
