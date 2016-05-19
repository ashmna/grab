package org.proffart.grab.task;

import org.proffart.grab.Log;
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
    private int currentProxyIndex = -1;
    private int currentAccountIndex = -1;

    public TaskRunner(List<Proxy> proxyList, List<Account> accountList, List<Video> videoList) {
        this.proxyList = proxyList;
        this.accountList = accountList;
        this.videoList = videoList;
    }

    public void start() {
        double time = 0;
        int accountCount;

        while (!isStopWatching()) {
            waitSecond((int) (time * 500));
            accountCount = viewNum(time, accountList.size());
            if (accountCount == 0 ) {
                Log.instance.error("Vahan algoritmt chi ashxatum !!!");
            }
            while (--accountCount != 0) {
                currentAccountIndex++;
                watch(accountList.get(currentProxyIndex), getProxy(), videoList.size(), 0);
            }
            time += 0.2;
        }
    }

    private boolean isStopWatching() {
        return accountList.size() == 0 || currentAccountIndex == accountList.size();
    }

    private int viewNum(final double time, final int maxNum) {
        return (int) (maxNum / 2 * Math.exp(time));
    }

    private Proxy getProxy() {
        currentProxyIndex++;
        if (currentProxyIndex == proxyList.size()) {
            currentProxyIndex = 0;
        }
        return proxyList.get(currentProxyIndex);
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
