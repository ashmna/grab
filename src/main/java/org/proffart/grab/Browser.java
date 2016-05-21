package org.proffart.grab;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Browser {

    private FirefoxDriver driver;
    private Log log = new Log();

    public Browser() {
        driver = new FirefoxDriver();
    }

    public Browser(final String host, final String port) {

        if (host != null && port != null) {
            String PROXY = host + ":" + port;

            org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();


            proxy.setHttpProxy(PROXY);
            proxy.setFtpProxy(PROXY);
            proxy.setSslProxy(PROXY);


            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(CapabilityType.PROXY, proxy);

            driver = new FirefoxDriver(cap);
        } else {
            log.warning("Browser open without proxy");

            driver = new FirefoxDriver();
        }
    }

    public String getHttpsIp() {
        driver.get("https://api.ipify.org/");

        WebElement text = driver.findElement(By.xpath("/html/body"));
        if (text != null) {
            log.info("[HTTPS] IP: " + text.getText());
            return text.getText();

        }

        return "";
    }

    public String getHttpIp() {
        driver.get("http://api.ipify.org/");

        WebElement text = driver.findElement(By.xpath("/html/body"));
        if (text != null) {
            log.info("[HTTP] IP: " + text.getText());
            return text.getText();
        }

        return "";
    }

    public void test_() {

        driver.get("https://www.google.com/search?q=my+ip");

        WebElement captcha = driver.findElement(By.xpath("/html/body/div/img"));
        if (captcha != null) {
            String alt = captcha.getAttribute("alt");
            if ("Please enable images".equals(alt)) {
                log.info("this is captcha ura !!!");
                CaptchaSolver captchaSolver = new CaptchaSolver();
                String text = captchaSolver.solver(getCaptchaImage(captcha));
                log.info("!!!! " + text + " !!!!");
            }
        }

        waitSecond(50);


    }

    public void waitSecond(final int second) {
        try {
            log.info("Start waiting second: " + second);
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitForLoad() {
        log.info("Wait For load page");
        waitSecond(1);
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void checkCaptcha() {
        if (driver.findElements(By.id("captchaImg")).size() == 1
                && driver.findElements(By.id("captchaKey")).size() == 1) {
            log.info("Captcha detected");
            waitSecond(4);
            WebElement captchaImg = driver.findElement(By.id("captchaImg"));
            WebElement captchaKey = driver.findElement(By.id("captchaKey"));
            log.warning("Captcha solving can take many seconds");
            CaptchaSolver captchaSolver = new CaptchaSolver();
            String text = captchaSolver.solver(getCaptchaImage(captchaImg));
            log.info("Captcha solver [" + text + "]");
            captchaKey.sendKeys(text);
            captchaKey.sendKeys(Keys.ENTER);
            waitForLoad();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        driver.quit();
    }

    public BufferedImage getCaptchaImage(final WebElement cap) {
        try {
            byte[] arrScreen = driver.getScreenshotAs(OutputType.BYTES);
            BufferedImage imageScreen = ImageIO.read(new ByteArrayInputStream(arrScreen));
            Dimension capDimension = cap.getSize();
            Point capLocation = cap.getLocation();
            BufferedImage imgCap = imageScreen.getSubimage(capLocation.x, capLocation.y, capDimension.width, capDimension.height);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(imgCap, "png", os);
            return imgCap;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
