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

        for (int i = 0; i < proxyList.size(); i++) {
            pool.execute(
                    new WatchHandler(
                            videoList,
                            getAccount(),
                            proxyList.get(i),
                            videoList.size(),
                            (int) Math.exp((i + 1) * 5 / proxyList.size())
                    )
            );
        }

        pool.shutdown();
    }

    private Account getAccount() {
        currentAccountIndex++;
        if (currentAccountIndex == accountList.size()) {
            currentAccountIndex = 0;
        }
        return accountList.get(currentAccountIndex);
    }

}
