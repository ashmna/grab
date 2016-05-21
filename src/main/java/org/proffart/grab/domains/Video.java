package org.proffart.grab.domains;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/15/16
 * time 17:00 PM
 */
public class Video extends AbstractDomain {

    private String url;
    private int secondMin;
    private int secondMax;

    private Map<Integer, Integer> usedProxyLis = new HashMap<Integer, Integer>();

    public Video() {
        super();
    }

    public Video(final String string) {
        super(string);
        String[] str = string.split(" ");
        url = str[0];
        secondMin = Integer.parseInt(str[1]);
        secondMax = Integer.parseInt(str[2]);
    }

    public String getUrl() {
        return url;
    }

    public int getSecond() {
        return secondMin + (int)(Math.random() * ((secondMax - secondMin) + 1));
    }

    public boolean isUsedProxy(Proxy proxy) {
        return usedProxyLis.containsKey(proxy.hashCode());
    }

    public void proxyUsed(Proxy proxy, int second) {
        usedProxyLis.put(proxy.hashCode(), second);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Video && url.equals(((Video) obj).url);
    }

    @Override
    public String toString() {
        return url + " second: " + secondMin + "-" + secondMax;
    }
}
