package net.sf.nightworks.quests;

import net.sf.nightworks.quests.loader.LoadedQuest;
import net.sf.nightworks.quests.plains.PlainQuests;
import net.sf.nightworks.util.DikuTextFile;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static net.sf.nightworks.DB.bug;
import static net.sf.nightworks.Nightworks.exit;
import static net.sf.nightworks.Nightworks.nw_config;

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
        // add the quest loader here, pass the entire hashmap to it, return it back
        return quests;
    }

    private static HashMap<Integer, ArrayList<SimpleQuest>> loadQuests(HashMap<Integer, ArrayList<SimpleQuest>> quests) {
        try {
            DikuTextFile questList = new DikuTextFile(nw_config.etc_quests_list);
            DikuTextFile questFile = null;
            ArrayList<LoadedQuest> loadedQuests = new ArrayList<>();
            for ( ; ; ) {
                String questFileName = questList.fread_word();
                if (questFileName.charAt(0) == '$') {
                    break;
                }
                if (questFileName.charAt(0) == '#' || questFileName.charAt(0) == '*') {
                    continue;
                }
                questFile = new DikuTextFile(nw_config.lib_quests_dir + "/" + questFileName);
                while (!questFile.feof()) {
                    String word = questFile.fread_word();
                    if (word.equals("#QUESTS")) {
                        // read in the quests after this line
                        ArrayList<LoadedQuest> lQuests = loadSingleQuest(questFile);
                        // return an array list and load each one into the hashmap
                        for (LoadedQuest lq : lQuests) {
                            ArrayList<SimpleQuest> sqs = quests.get(lq.getVnumQuestGiver());
                            sqs.add(convertLoadedQuest(lq));
                            quests.put(lq.getVnumQuestGiver(), sqs);
                        }
                    }
                }
            }
            // after the quests are loaded, transfer them over to the hashmap
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return quests;
    }

    private static ArrayList<LoadedQuest> loadSingleQuest(DikuTextFile qFile) {
        ArrayList<LoadedQuest> loadedQuests;
        char letter;
        int vnum;
        for ( ; ; ) {
            letter = qFile.fread_letter();
            if (letter != '#') {
                bug("Load_single_quest: # not found.");
                System.out.println("found letter " + letter);
                exit(1);
            }
        }

    }

    private static SimpleQuest convertLoadedQuest(LoadedQuest lq) {
        return null;
    }
}
