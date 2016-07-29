package org.proffart.grab.task;

import org.proffart.grab.Constants;
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
    private List<Video> videoList;
    private int currentAccountIndex = -1;
    private final ExecutorService pool;

    public TaskRunner(List<Proxy> proxyList, List<Video> videoList) {
        this.proxyList = proxyList;
        this.videoList = videoList;
        pool = Executors.newFixedThreadPool(Constants.PARALLEL_BROWSER_COUNT);
    }

    public void startWatching() {

        for (int i = 0; i < proxyList.size(); i++) {
            pool.execute(
                    new WatchHandler(
                            videoList,
                            proxyList.get(i),
                            videoList.size(),
                            (int) Math.exp((i + 1) * 5 / proxyList.size())
                    )
            );
        }

        pool.shutdown();
    }

}
