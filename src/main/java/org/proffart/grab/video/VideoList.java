package org.proffart.grab.video;

import org.proffart.grab.proxy.Proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/15/16
 * time 17:00 PM
 */
public class VideoList {

    private String filePath;
    private ArrayList<Video> videos = new ArrayList<Video>();
    private int currentIndex = -1;

    public VideoList(final String filePath) {
        this.filePath = filePath;
    }

    public void read() {
        try {
            Scanner inFile = new Scanner(new File(filePath)).useDelimiter("\n");
            while (inFile.hasNext()) {
                addVideo(inFile.next());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return videos.size();
    }

    public Video getNext() {
        if (currentIndex == size() - 1) {
            currentIndex = 0;
        } else {
            ++currentIndex;
        }
        return videos.get(currentIndex);
    }

    private void addVideo(final String s) {
        if (s == null) {
            return;
        }
        if (s.isEmpty()) {
            return;
        }
        String[] str = s.split(" ");
        Video video = new Video();

        video.setUrl(str[0]);
        video.setSecond(Integer.parseInt(str[1]));

        videos.add(video);
    }
}
