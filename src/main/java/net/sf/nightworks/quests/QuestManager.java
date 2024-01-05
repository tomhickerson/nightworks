package net.sf.nightworks.quests;

import net.sf.nightworks.quests.loader.LoadedQuest;
import net.sf.nightworks.quests.plains.PlainQuests;
import net.sf.nightworks.util.DikuTextFile;

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

    public static final int RACE_ELF_ONLY = 1;
    public static final int RACE_DWARF_ONLY = 2;
    public static final int RACE_HUMAN_ONLY = 3;
    public static final int RACE_NOT_HUMAN = 4;
    public static final int RACE_NOT_DWARF = 5;
    public static final int RACE_NOT_ELF = 6;

    public static final int ALIGN_GOOD = 1;
    public static final int ALIGN_NEUTRAL = 2;
    public static final int ALIGN_EVIL = 3;
    public static final int ALIGN_NOT_EVIL = 4;
    public static final int ALIGN_NOT_GOOD = 5;

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
        ArrayList<LoadedQuest> loadedQuests = null;
        char letter;
        int vnum;
        for ( ; ; ) {
            letter = qFile.fread_letter();
            if (letter != '#' && letter != 'S' && letter != '$') {
                bug("Load_single_quest: # or S or $ not found.");
                System.out.println("found letter " + letter);
                exit(1);
            }
            if (letter == 'S') {
                // go on to the next quest
                continue;
            }
            if (letter == '$') {
                // break out and return the array
                break;
            }
            if (letter == '#') {
                vnum = qFile.fread_number();
                if (vnum == 0) {
                    break;
                }
                // check for duplicates
                LoadedQuest loadThisQuest = new LoadedQuest();
                loadThisQuest.setQuestId(vnum);
                // above is quest id and achievement id
                // load all the strings
                loadThisQuest.setQuestName(qFile.fread_string());
                loadThisQuest.setQuestType(qFile.fread_string());
                loadThisQuest.setPreamble(qFile.fread_string());
                loadThisQuest.setComeBackLater(qFile.fread_string());
                loadThisQuest.setAcceptKeyword(qFile.fread_word());
                loadThisQuest.setAcceptPhrase(qFile.fread_string());
                loadThisQuest.setEpilogue(qFile.fread_string());
                // next, load all the numbers
                // first line, four numbers
                loadThisQuest.setQuestMinLevel(qFile.fread_number());
                loadThisQuest.setPrereqAchvId(qFile.fread_number());
                loadThisQuest.setQuestRace(qFile.fread_number());
                loadThisQuest.setQuestAlign(qFile.fread_number());
                // next line, five numbers
                loadThisQuest.setVnumQuestGiver(qFile.fread_number());
                loadThisQuest.setDuration(qFile.fread_number());
                loadThisQuest.setVnumToKill(qFile.fread_number());
                loadThisQuest.setVnumToGet(qFile.fread_number());
                loadThisQuest.setVnumContainer(qFile.fread_number());
                // next line, five more numbers
                loadThisQuest.setQuestPoints(qFile.fread_number());
                loadThisQuest.setSilver(qFile.fread_number());
                loadThisQuest.setGold(qFile.fread_number());
                loadThisQuest.setVirtue(qFile.fread_number());
                loadThisQuest.setVice(qFile.fread_number());
                loadedQuests.add(loadThisQuest);
            }
        }
        return loadedQuests;
    }

    private static SimpleQuest convertLoadedQuest(LoadedQuest lq) {
        SimpleKillQuest skq;
        SimpleGetQuest sgq;
        if (lq.getQuestType().equals("kill")) {
            skq = new SimpleKillQuest(lq.getQuestId(), lq.getQuestName());
            // set kill specific here
            skq.setVnumToKill(lq.getVnumToKill());
            skq = (SimpleKillQuest) loadGenericSettings(lq, skq);
            // then add all generic settings in another function
            return skq;
        } else {
            // add onto this as you add more types
            sgq = new SimpleGetQuest(lq.getQuestId(), lq.getQuestName());
            sgq.setVnumToGet(lq.getVnumToGet());
            // no container yet?
            sgq = (SimpleGetQuest) loadGenericSettings(lq, sgq);
            return sgq;

        }
    }

    private static SimpleQuest loadGenericSettings(LoadedQuest lq, SimpleQuest sq) {
        sq.setLoadedQuest(lq);
        sq.setAchievement(lq.getQuestId());
        sq.setDuration(lq.getDuration());
        sq.setPreamble(lq.getPreamble());
        sq.setEpilogue(lq.getEpilogue());
        sq.setAcceptMessage(lq.getAcceptPhrase());
        sq.setAcceptPhrase(lq.getAcceptKeyword());
        sq.setQualify(null);
        sq.setReward(null);
        sq.setAdvancedPreamble(null);
        return sq;
    }
}
