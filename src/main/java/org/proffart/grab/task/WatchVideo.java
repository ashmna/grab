package org.proffart.grab.task;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        driver = browser.getDriver();
    }

    public void closeBrowser() {
        browser.close();
    }

    public void login() {
        driver.get("https://vk.com/login.php");

        final WebElement email = driver.findElement(By.id("email"));
        final WebElement pass = driver.findElement(By.id("pass"));
        final WebElement loginButton = driver.findElement(By.id("login"));

        email.sendKeys(account.getLogin());
        pass.sendKeys(account.getPassword());

        loginButton.submit();
        handelLoginSubmit();
    }

    public void watchVideo(final String url, final int second) {
        driver.get(url);
        // browser.waitForLoad();
        browser.checkCaptcha();

        if (driver.findElements(By.id("video_yt_player")).size() == 1) {
            driver.findElement(By.id("video_yt_player")).click();
        } else {
            ((JavascriptExecutor) driver).executeScript("if(window.html5video && !window.html5video.playStarted) window.html5video.playVideo();");
        }

        browser.waitSecond(second);
    }

    public void logout() {
        if (driver.findElements(By.id("logout_link")).size() == 1) {
            final WebElement logoutLink = driver.findElement(By.id("logout_link"));
            final String href = logoutLink.getAttribute("href");
            driver.get(href.replace("&amp;", "&"));
        }
    }

    private boolean handelLoginSubmit() {
        browser.waitForLoad();
        browser.checkCaptcha();

        if (driver.getCurrentUrl().contains("vk.com/login.php?act=security_check&to=&al_page=3")) {
            final WebElement formTable = driver.findElement(By.id("form_table"));
            final String phonePrefix = formTable.findElement(By.className("ta_r")).getText();
            final String phonePostfix = formTable.findElement(By.className("phone_postfix")).getText();

            formTable.findElement(By.id("code")).sendKeys(account.getCode(phonePrefix, phonePostfix));

            formTable.findElement(By.id("validate_btn")).click();

            return handelLoginSubmit();

        } else if (driver.getCurrentUrl().contains("vk.com/feed")) {
            return true;
        }

        return false;
    }
}
