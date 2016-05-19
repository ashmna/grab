package org.proffart.grab.task;

import org.proffart.grab.domains.Account;
import org.proffart.grab.domains.Proxy;
import org.proffart.grab.domains.Video;

import java.util.List;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/19/16
 * time 1:46 PM
 */
public class WatchHandler implements Runnable {

    private Account account;
    private Proxy proxy;
    private int count;
    private int delaySecond;
    private List<Video> videoList;

    public WatchHandler(
            final List<Video> videoList,
            final Account account,
            final Proxy proxy,
            final int count,
            final int delaySecond
    ) {
        this.account = account;
        this.proxy = proxy;
        this.count = count;
        this.delaySecond = delaySecond;
        this.videoList = videoList;
    }

    @Override
    public void run() {
        final WatchVideo task = new WatchVideo();
        int counter = count;
        task.setProxy(proxy);
        task.initBrowser();
        task.setAccount(account);
        task.login();
        for (Video video : videoList) {
            if (account.isWatched(video)) {
                continue;
            }

            task.watchVideo(video);

            if (--counter == 0) {
                break;
            }

            waitSecond(delaySecond);
        }
        task.logout();
        task.closeBrowser();
    }

    private void waitSecond(int second) {
        if (second <= 0) {
            return;
        }
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
