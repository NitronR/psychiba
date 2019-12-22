package com.bhanu.psychiba;

import java.util.HashMap;
import java.util.Map;

import com.bhanu.psychiba.model.GameMode;

public class Constants {

    public static final int MAX_ANSWER_LENGTH = 1000;
    public static final int MAX_QUESTION_LENGTH = 1000;
    public static final int MAX_QUESTIONS_READ = 100;
    public static final Map<String, GameMode> qaFiles = new HashMap<>();

    static {
        qaFiles.put("qa_", GameMode.IS_THAT_A_FACT);
        qaFiles.put("qa_", GameMode.WORD_UP);
        qaFiles.put("qa_", GameMode.UNSCRAMBLE);
    }
}