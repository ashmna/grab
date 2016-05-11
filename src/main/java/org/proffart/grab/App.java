package org.proffart.grab;

public class App {

    public static void main(String arg[]) {

        Browser browser = new Browser("104.156.253.208", "3128");

        Log.instance.info(browser.getMyIp());
        browser.waite(10);
        browser.close();
    }

}
//body > div:nth-child(1) > img:nth-child(5)
