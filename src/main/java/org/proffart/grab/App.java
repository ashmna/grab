package org.proffart.grab;

import org.proffart.grab.account.AccountList;
import org.proffart.grab.proxy.ProxyList;
import org.proffart.grab.task.WatchVideo;
import org.proffart.grab.video.Video;
import org.proffart.grab.video.VideoList;

public class App {

    public static void main(String arg[]) {

        ProxyList proxyList = new ProxyList("/home/all/Projects/java/grab/proxies.txt");
        AccountList accountList = new AccountList("/home/all/Projects/java/grab/accounts.txt");
        VideoList videoList = new VideoList("/home/all/Projects/java/grab/videos.txt");
        proxyList.read();
        videoList.read();
        accountList.read();

        for (int k = 0; k < proxyList.size(); k++) {

            WatchVideo task = new WatchVideo();
            // task.setProxy(proxyList.getNext());
            task.initBrowser();

            for (int j = 0; j < accountList.size(); ++j) {

                task.setAccount(accountList.getNext());
                task.login();

                for (int i = 0; i < videoList.size(); ++i) {
                    Video video = videoList.getNext();
                    task.watchVideo(video.getUrl(), video.getSecond());
                }

                task.logout();

            }

            task.closeBrowser();
        }

    }

}
