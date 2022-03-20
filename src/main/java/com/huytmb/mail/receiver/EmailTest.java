package com.huytmb.mail.receiver;

import javax.mail.*;
import java.util.Properties;

public class EmailTest {
    public static void main(String[] args) throws MessagingException {
        String host = "pop.163.com";
        String username = "jtrminsk@163.com";
        String password = "AFEZWLDCHEVZXDIZ";


// Create empty properties

        Properties props = new Properties();


// Get session

        Session session = Session.getDefaultInstance(props, null);


// Get the store

        Store store = session.getStore("pop3");
        store.connect(host, username, password);


// Get folder

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);


// Get directory
        Message message[] = folder.getMessages();



        for (int i=0, n=message.length; i<n; i++) {

            System.out.println(i + ": " + message[i].getFrom()[0] + "/t" + message[i].getSubject());
            //要求：主题契合
        }


// Close connection

        folder.close(false);
        store.close();
    }
}
