package com.bhanu.psychiba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticContent {
    private static List<String> gameCodeWords;

    // loads static content
    public static void loadContent() throws FileNotFoundException {
        loadGameWords();
    }

    // populates game words list from words.txt file
    public static void loadGameWords() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("words.txt"));
        gameCodeWords = new ArrayList<>();
        String word;
        try {
            while ((word = bufferedReader.readLine()) != null) {
                gameCodeWords.add(word);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Logger.getLogger("default").log(Level.SEVERE, "Exception while reading words for game codes.");
        }
    }

    // give read access to game words
    public static String getGameCodeWord(int position) {
        return gameCodeWords.get(position);
    }
    
    // give read access to game words
    public static int getNumGameCodeWords() {
        return gameCodeWords.size();
    }
}