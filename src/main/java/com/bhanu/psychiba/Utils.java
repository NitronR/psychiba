package com.bhanu.psychiba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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
}