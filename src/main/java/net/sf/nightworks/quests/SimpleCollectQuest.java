package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;

public class SimpleCollectQuest extends SimpleQuest {

    // private int[] vnumsToGet = new int[];

    private int questPoints = 0;

    public SimpleCollectQuest(String name) {
        super(name);
    }

    public SimpleCollectQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean canRunAgain() {
        return false;
    }

    @Override
    public boolean isStandalone() {
        return false;
    }
}
