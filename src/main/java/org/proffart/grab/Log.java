package org.proffart.grab;

public class Log {

    public static Log i = new Log();

    public void info(final String s) {
        if (s != null)
            out("[INFO]", s);
    }

    public void warning(final String s) {
        if (s != null)
            out("[WARN]", s);
    }

    public void error(final String s) {
        if (s != null)
            out("[ERRO]", s);
    }

    private void out(String prefix, String s) {
        Thread t = Thread.currentThread();
        String name = t.getName();
        System.out.println(prefix + " [" + name + "] " + s);
    }

}
