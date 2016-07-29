package org.proffart.grab;

import org.proffart.grab.domains.Account;
import org.proffart.grab.domains.Proxy;
import org.proffart.grab.domains.Reader;
import org.proffart.grab.domains.Video;
import org.proffart.grab.task.TaskRunner;

import java.util.List;

public class App {

    private static Reader<Proxy> proxyReader = new Reader<Proxy>(Proxy.class);
    private static Reader<Video> videoReader = new Reader<Video>(Video.class);

    public static void main(String arg[]) {
        final Log log = new Log();
        String proxyFile = System.getProperty("proxy");
        String videoFile = System.getProperty("video");

        if (proxyFile == null || proxyFile.isEmpty()) {
            proxyFile = "proxy.txt";
        }
        if (videoFile == null || videoFile.isEmpty()) {
            videoFile = "video.txt";
        }


        final List<Proxy> proxyList = proxyReader.read(proxyFile);
        final List<Video> videoList = videoReader.read(videoFile);

        TaskRunner runner = new TaskRunner(proxyList, videoList);
        runner.startWatching();
    }

}
