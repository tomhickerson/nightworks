package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;

public class SimpleGetQuest extends SimpleQuest {

    private int vnumToGet = -1;
    private int questPoints = 0;

    public SimpleGetQuest(String name) {
        super(name);
    }

    public SimpleGetQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isStandalone() {
        return false;
    }

    public int getVnumToGet() {
        return vnumToGet;
    }

    public void setVnumToGet(int vnumToGet) {
        this.vnumToGet = vnumToGet;
    }

    public int getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }
}
