#!/bin/bash

java -Daccount="./account.txt" -Dproxy="./proxy.txt" -Dvideo="./video.txt" -jar ../../../target/grab-video-jar-with-dependencies.jar
