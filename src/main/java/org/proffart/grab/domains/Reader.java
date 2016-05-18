package org.proffart.grab.domains;

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
            return list;
        } catch (FileNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
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
