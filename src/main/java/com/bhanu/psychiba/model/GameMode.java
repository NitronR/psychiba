package com.bhanu.psychiba.model;

public enum GameMode {
    IS_THAT_A_FACT(1), UNSCRAMBLE(2), WORD_UP(3);

    private int value;

    GameMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static GameMode fromValue(int value) {
        switch (value) {
        case 1:
            return IS_THAT_A_FACT;
        case 2:
            return UNSCRAMBLE;
        case 3:
            return WORD_UP;
        default:
            return IS_THAT_A_FACT;
        }
    }
}