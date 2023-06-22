package net.sf.nightworks.quests;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestManager {

    private static final int VNUM_DRUID_PLAINS = 300;
    HashMap<Integer, ArrayList<SimpleQuest>> quests = new HashMap<>();

    /**
     * Quest Manager returns a hashmap which is run once during server startup
     * @return hashmap of mob vnum => list of quests; it's assumed that these are
     * generated in order, if they are sequential and not standalone.  Ideally, a mob
     * should either have a list of sequential quests or several unconnected quests.
     */
    public HashMap<Integer, ArrayList<SimpleQuest>> getQuests() {

        return quests;
    }
}
