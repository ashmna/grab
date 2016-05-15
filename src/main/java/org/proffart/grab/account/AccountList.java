package org.proffart.grab.account;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * team ProffArt
 * author Ashot Mnatsakanyan
 * date 5/14/16
 * time 10:24 PM
 */
public class AccountList {

    private String filePath;
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private int currentIndex = -1;

    public AccountList(final String filePath) {
        this.filePath = filePath;
    }

    public void read() {
        try {
            Scanner inFile = new Scanner(new File(filePath)).useDelimiter("\n");
            while (inFile.hasNext()) {
                addAccount(inFile.next());
            }
            inFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return accounts.size();
    }

    public Account getNext() {
        if (currentIndex == size() - 1) {
            currentIndex = 0;
        } else {
            ++currentIndex;
        }
        return accounts.get(currentIndex);
    }

    private void addAccount(final String s) {
        if (s == null) {
            return;
        }
        if (s.isEmpty()) {
            return;
        }
        String[] str = s.split(":");
        Account account = new Account();
        account.setLogin(str[0]);
        account.setPassword(str[1]);
        accounts.add(account);
    }
}
