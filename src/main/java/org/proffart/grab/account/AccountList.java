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
    private ArrayList<Account> accounts;
    private int currentIndex = -1;

    public AccountList(String filePath) {
        filePath = filePath;
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
        currentIndex = currentIndex == size() ? 0 : currentIndex++;
        return accounts.get(currentIndex);
    }

    private void addAccount(String s) {
        String[] str = s.split(":");
        Account account = new Account();
        account.setLogin(str[0]);
        account.setPassword(str[1]);
        accounts.add(account);
    }
}
