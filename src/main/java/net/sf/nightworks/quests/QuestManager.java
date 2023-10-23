package net.sf.nightworks.quests;

import net.sf.nightworks.quests.plains.PlainQuests;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestManager {

    private static final int VNUM_DRUID_PLAINS = 300;
    private static final int VNUM_HERMIT_PLAINS = 301;
    private static final int VNUM_PILGRIM_PLAINS = 351;
    private static HashMap<Integer, ArrayList<SimpleQuest>> quests = new HashMap<>();

    /**
     * Quest Manager returns a hashmap which is run once during server startup
     * @return hashmap of mob vnum => list of quests; it's assumed that these are
     * generated in order, if they are sequential and not standalone.  Ideally, a mob
     * should either have a list of sequential quests or several unconnected quests.
     */
    public static HashMap<Integer, ArrayList<SimpleQuest>> getQuests() {
        ArrayList<SimpleQuest> questArray = new ArrayList<>();
        questArray.add(PlainQuests.returnDruidQuest());
        quests.put(VNUM_DRUID_PLAINS, questArray);
        // reset the same array?
        questArray = new ArrayList<>();
        questArray.add(PlainQuests.returnHermitQuest());
        questArray.add(PlainQuests.killMoreRabbits());
        quests.put(VNUM_HERMIT_PLAINS, questArray);
        questArray = new ArrayList<>();
        questArray.add(PlainQuests.returnPilgrimQuest());
        quests.put(VNUM_PILGRIM_PLAINS, questArray);
        return quests;
    }
}
