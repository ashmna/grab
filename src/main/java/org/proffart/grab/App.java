package org.proffart.grab;

import org.proffart.grab.domains.Account;
import org.proffart.grab.domains.Proxy;
import org.proffart.grab.domains.Reader;
import org.proffart.grab.domains.Video;
import org.proffart.grab.task.TaskRunner;
import org.proffart.grab.task.WatchVideo;

import java.util.List;

public class App {

    private static Reader<Proxy> proxyReader = new Reader<Proxy>(Proxy.class);
    private static Reader<Account> accountReader = new Reader<Account>(Account.class);
    private static Reader<Video> videoReader = new Reader<Video>(Video.class);

    public static void main(String arg[]) {

        final List<Proxy> proxyList = proxyReader.read("/home/all/Projects/java/grab/proxies.txt");
        final List<Account> accountList = accountReader.read("/home/all/Projects/java/grab/accounts.txt");
        final List<Video> videoList = videoReader.read("/home/all/Projects/java/grab/videos.txt");

        TaskRunner runner = new TaskRunner(proxyList, accountList, videoList);
        runner.start();
    }

}
