package org.proffart.grab.domains;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/14/16
 * time 10:38 PM
 */
public class Proxy extends AbstractDomain {

    private String host;
    private String port;

    public Proxy() {
        super();
    }

    public Proxy(final String string) {
        super(string);
        String[] str = string.split(":");
        host = str[0];
        port = str[1];
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(final String port) {
        this.port = port;
    }
}
