package org.proffart.grab.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/14/16
 * time 10:54 PM
 */
public class ProxyList {

    private String filePath;
    private ArrayList<Proxy> proxies;
    private int currentIndex = -1;

    public ProxyList(String filePath) {
        filePath = filePath;
    }

    public void read() {
        try {
            Scanner inFile = new Scanner(new File(filePath)).useDelimiter("\n");
            while (inFile.hasNext()) {
                addProxy(inFile.next());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return proxies.size();
    }

    public Proxy getNext() {
        currentIndex = currentIndex == size() ? 0 : currentIndex++;
        return proxies.get(currentIndex);
    }

    private void addProxy(String s) {
        String[] str = s.split(":");
        Proxy proxy = new Proxy();
        proxy.setHost(str[0]);
        proxy.setPort(str[1]);
        proxies.add(proxy);
    }
}
