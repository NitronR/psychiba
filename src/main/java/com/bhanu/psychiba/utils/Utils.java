package com.bhanu.psychiba.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bhanu.psychiba.Constants;
import com.bhanu.psychiba.StaticContent;

import org.springframework.data.util.Pair;;

public class Utils {

    public static List<Pair<String, String>> readQAFile(String fileName) throws FileNotFoundException {
        List<Pair<String, String>> QAs = new ArrayList<>();
        BufferedReader bReader = new BufferedReader(new FileReader("qa_facts.txt"));

        String question, answer;
        try {
            do {
                question = bReader.readLine();
                answer = bReader.readLine();
                if (question == null || answer == null) {
                    break;
                }
                if (question.length() > Constants.MAX_QUESTION_LENGTH
                        || answer.length() > Constants.MAX_ANSWER_LENGTH) {
                    System.out.println("Skipping question: " + question);
                    System.out.println("Skipping answer: " + answer);
                    continue;
                }

                QAs.add(Pair.of(question, answer));
            } while (true);
        } catch (IOException ignored) {

        }

        return QAs;
    }

    public static void sendInvites(List<String> emails, String playerName, String gameCode)
            throws FileNotFoundException {
        // read invite template
        String inviteSubject, inviteTemplate;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("invite_template.txt"));

        // TODO OK to read as sending invites not that frequent?
        try {
            inviteSubject = bufferedReader.readLine();
            inviteTemplate = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            Logger.getLogger("default").log(Level.SEVERE, "Error while reading invite template file.");
            return;
        }

        // send emails
        for (String email : emails) {
            // TODO log email sent
            EmailUtils.sendEmails(email, inviteSubject,
                    inviteTemplate.replace("{player_name}", playerName).replace("{game_code}", gameCode));
        }

        // TODO log all emails sent
    }

    public static String getGameCode(Long gameid) {
        int gameId = (int) (long) gameid - 1, base = StaticContent.getNumGameCodeWords();
        String gameCode = "";

        while (gameId > 0) {
            gameCode += StaticContent.getGameCodeWord(gameId % base) + "";
            gameId /= base;
        }

        return gameCode;
    }

    public static Long getGameIdFromGameCode(String gameCode) {
        String words[] = gameCode.split(" ");
        Long gameId = 0L;
        int base = StaticContent.getNumGameCodeWords();
        for (int i = words.length - 1; i >= 0; i--) {
            gameId = gameId * base + StaticContent.getWordIndex(words[i]);
        }

        return gameId + 1;
    }
}