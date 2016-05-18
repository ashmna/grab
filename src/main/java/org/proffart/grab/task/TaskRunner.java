package org.proffart.grab.task;

import org.proffart.grab.domains.Account;
import org.proffart.grab.domains.Proxy;
import org.proffart.grab.domains.Video;

import java.util.List;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/18/16
 * time 11:54 AM
 */
public class TaskRunner {

    private List<Proxy> proxyList;
    private List<Account> accountList;
    private List<Video> videoList;

    public TaskRunner(List<Proxy> proxyList, List<Account> accountList, List<Video> videoList) {
        this.proxyList = proxyList;
        this.accountList = accountList;
        this.videoList = videoList;
    }

    public void start() {

    }

    public int viewNum(double time, int maxNum) {
        return (int) (maxNum / Math.exp(time));
    }

    private void watch(final Account account, final Proxy proxy, final int count, final int delaySecond) {
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

            if (delaySecond <= 0) {
                continue;
            }

            try {
                Thread.sleep(delaySecond * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        task.logout();
        task.closeBrowser();
    }
}
