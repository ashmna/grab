package org.proffart.grab;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Browser {

    private FirefoxDriver driver;

    public Browser() {
        driver = new FirefoxDriver();
    }

    public Browser(final String host, final String port) {
        if (host != null && port != null) {
            String PROXY = host + ":" + port;

            org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
            proxy.setHttpProxy(PROXY)
                    .setFtpProxy(PROXY)
                    .setSslProxy(PROXY);
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(CapabilityType.PROXY, proxy);

            driver = new FirefoxDriver(cap);
        } else {
            Log.instance.warning("open without proxy");

            driver = new FirefoxDriver();
        }
    }

    public String getMyIp() {
        driver.get("https://api.ipify.org/");

        WebElement text = driver.findElement(By.xpath("/html/body"));
        if( text != null ) {
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
                Log.instance.info("this is captcha ura !!!");
                String text = CaptchaSolver.solver(getCaptchaImage(captcha));
                Log.instance.info("!!!! " + text + " !!!!");
            }
        }

        waite(50);


    }

    public void waite(final int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void close() {
        driver.quit();
    }

    public BufferedImage getCaptchaImage(WebElement cap) {
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
