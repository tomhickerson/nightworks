package net.sf.nightworks.behave;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MobDeath {
    static HashMap<Integer, ArrayList<String>> sayings = new HashMap<>();
    static HashMap<Integer, ArrayList<String>> actions = new HashMap<>();
    static final int VNUM_TRAINING_MONSTER = 3703;
    static final int VNUM_TRAINING_OTHER_MONSTER = 3704;

    public static HashMap<Integer, ArrayList<String>> initSayings() {
        sayings.put(VNUM_TRAINING_MONSTER, returnGenericSayings());
        return sayings;
    }

    public static HashMap<Integer, ArrayList<String>> initActions() {
        actions.put(VNUM_TRAINING_OTHER_MONSTER, returnGenericActions());
        return actions;
    }

    public static ArrayList<String> getSayings(int vnum) {
        return initSayings().get(vnum);
    }

    public static ArrayList<String> getDoings(int vnum) { return initActions().get(vnum); }

    public static ArrayList<String> returnGenericSayings() {
        ArrayList<String> say = new ArrayList<>();
        say.add("Oof, that hurt!");
        say.add("You got me, stranger!");
        say.add("Oh, the humanity!");
        say.add("Aww, I'm sure I've got a little more life in me yet...");
        return say;
    }

    public static ArrayList<String> returnGenericActions() {
        ArrayList<String> doing = new ArrayList<>();
        doing.add("$n clutches its chest as it dies!");
        doing.add("$n shudders, and is still.");
        doing.add("$n thrashes for a moment, and then stops.");
        return doing;
    }
}
