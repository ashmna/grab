package org.proffart.grab;

public class App {

    public static void main(String arg[]) {

        Browser browser = new Browser("178.32.218.104", "3128");

        Log.instance.info(browser.getMyIp());
        browser.waite(10);
        browser.close();
    }

}
//body > div:nth-child(1) > img:nth-child(5)
