package org.proffart.grab.domains;

import org.proffart.grab.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/18/16
 * time 11:18 AM
 */
public class Reader<T extends AbstractDomain> {

    private List<T> list = new ArrayList<T>();
    private Class<T> domainClass;
    private Log log = new Log();

    public Reader(final Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    public List<T> read(final String path) {
        try {
            Scanner inFile = new Scanner(new File(path)).useDelimiter("\n");
            while (inFile.hasNext()) {
                addDomain(inFile.next());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            log.error("File doesn't exist, path: " + path);
            log.error(e.getMessage());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private void addDomain(final String s) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (s == null) {
            return;
        }

        if (s.isEmpty()) {
            return;
        }

        Class[] cArg = {String.class};
        T domain = domainClass.getDeclaredConstructor(cArg).newInstance(s);

        if (!list.contains(domain)) {
            list.add(domain);
        }
    }

}
