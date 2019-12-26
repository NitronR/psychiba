package com.bhanu.psychiba.utils;

import java.util.logging.Logger;

public class EmailUtils {
    public static void sendEmails(String recipient, String subject, String message) {
        Logger logger = Logger.getLogger("default");

        logger.info("Sending email.");
        logger.info("Recipient: " + recipient);
        logger.info("Subject: " + subject);
        logger.info("Message: " + message);
    }
}