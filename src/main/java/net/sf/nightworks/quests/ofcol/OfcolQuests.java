package net.sf.nightworks.quests.ofcol;

import net.sf.nightworks.enums.PlayerAchievement;
import net.sf.nightworks.quests.SimpleCollectQuest;

public class OfcolQuests {

    public static SimpleCollectQuest returnLGLQuest() {
        SimpleCollectQuest lglQuest = new SimpleCollectQuest(4,
                "Find out what is happening to young girls in the Orphanage");
        lglQuest.setAchievement(PlayerAchievement.LITTLE_GIRLS_LOST.getId());
        lglQuest.setNumberToCollect(1);
        lglQuest.setVnumContainer(-1);// to be determined
        lglQuest.setVnumToCollect(-1);// to be determined
        lglQuest.setAdvancedPreamble(null);// to be determined
        lglQuest.setAcceptPhrase("yes");
        lglQuest.setDuration(240);// a long time
        lglQuest.setQualify(null);
        lglQuest.setReward(null);
        lglQuest.setQuestSetup(null);
        lglQuest.setEpilogue(null);
        // will there be an advanced epilogue?
        return lglQuest;
    }
}
