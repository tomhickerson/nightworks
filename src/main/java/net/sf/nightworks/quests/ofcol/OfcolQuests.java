package net.sf.nightworks.quests.ofcol;

import net.sf.nightworks.quests.SimpleCollectQuest;

public class OfcolQuests {

    public static SimpleCollectQuest returnLGLQuest() {
        SimpleCollectQuest lglQuest = new SimpleCollectQuest(4,
                "Find out what is happening to young girls in the Orphanage");
        return lglQuest;
    }
}
