package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;

public class SimpleKillQuest extends SimpleQuest {

    private int vnumToKill = -1;
    private int questPoints = 0;

    public SimpleKillQuest(String name) {
        super(name);
    }

    public SimpleKillQuest(int id, String name) {
        super(id, name);
    }

    // @Override
    // public boolean doesQualify(Nightworks.CHAR_DATA ch) {
       // return true;
    //}

    @Override
    public boolean canRunAgain() {
        return true;
    }

    @Override
    public boolean isStandalone() {
        return false;
    }

    @Override
    public Object deliverReward(Nightworks.CHAR_DATA ch) {
        ch.pcdata.questpoints += questPoints;
        return ch;
    }

    public int getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }

    public int getVnumToKill() {
        return vnumToKill;
    }

    public void setVnumToKill(int vnumToKill) {
        this.vnumToKill = vnumToKill;
    }
}
