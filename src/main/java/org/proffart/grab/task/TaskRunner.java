package org.proffart.grab.task;

import org.proffart.grab.Constants;
import org.proffart.grab.Log;
import org.proffart.grab.domains.Account;
import org.proffart.grab.domains.Proxy;
import org.proffart.grab.domains.Video;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final ExecutorService pool;

    public TaskRunner(List<Proxy> proxyList, List<Account> accountList, List<Video> videoList) {
        this.proxyList = proxyList;
        this.accountList = accountList;
        this.videoList = videoList;
        pool = Executors.newFixedThreadPool(Constants.PARALLEL_BROWSER_COUNT);
    }

    public void startWatching() {
        double time = 0;
        int accountCount;

        while (!isStopWatching()) {
            waitSecond((int) (time * 500));
            accountCount = viewNum(time, accountList.size());
            if (accountCount == 0 ) {
                Log.instance.error("Vahan algorithmt chi ashxatum !!!");
            }
            while (accountCount-- != 0) {
                currentAccountIndex++;
                pool.execute(
                    new WatchHandler(
                        videoList,
                        accountList.get(currentAccountIndex),
                        getProxy(),
                        videoList.size(),
                        0
                    )
                );
            }
            time += 0.2;
        }
        pool.shutdown();
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
