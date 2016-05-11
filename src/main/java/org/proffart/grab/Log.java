package org.proffart.grab;

public class Log {

    public static Log instance = new Log();

    public void info(final String s) {
        if (s != null)
            System.out.println("[INFO] " + s);
    }

    public void warning(final String s) {
        if (s != null)
            System.out.println("[WARN] " + s);
    }

    public void error(final String s) {
        if (s != null)
            System.out.println("[ERRO] " + s);
    }

}
