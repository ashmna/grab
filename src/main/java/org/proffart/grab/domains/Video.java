package org.proffart.grab.domains;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/15/16
 * time 17:00 PM
 */
public class Video extends AbstractDomain {

    private String url;
    private int second;

    public Video() {
        super();
    }

    public Video(final String string) {
        super(string);
        String[] str = string.split(" ");
        url = str[0];
        second = Integer.parseInt(str[1]);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Video && url.equals(((Video) obj).url) & second == ((Video) obj).second;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
