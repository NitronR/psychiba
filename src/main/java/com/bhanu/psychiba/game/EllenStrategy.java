package com.bhanu.psychiba.game;

import com.bhanu.psychiba.model.EllenAnswer;
import com.bhanu.psychiba.model.Round;

public interface EllenStrategy {
    EllenAnswer getAnswer(Round round);
}
