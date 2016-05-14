package org.proffart.grab.task;

import org.openqa.selenium.WebDriver;
import org.proffart.grab.Browser;
import org.proffart.grab.account.Account;
import org.proffart.grab.proxy.Proxy;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/14/16
 * time 11:02 PM
 */
public class WatchVideo {

    private Account account;
    private Proxy proxy;
    private Browser browser;
    private WebDriver driver;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void initBrowser() {
        if (proxy != null) {
            browser = new Browser(proxy.getHost(), proxy.getPort());
        } else {
            browser = new Browser();
        }
        WebDriver driver = browser.getDriver();
    }

    public void doTask() {
        initBrowser();
        login();
        watchVideo();
    }

    private void login() {
        // https://vk.com/login.php
        // http://vk.com/video?section=all&z=video81628882_171788517%2Falbum81628882%2Fpl_81628882
        // *[@id="email"]
        // *[@id="pass"]
        // *[@id="login"]/table/tbody/tr[4]/td[2]/div/button
    }

    private void watchVideo() {

    }

}
