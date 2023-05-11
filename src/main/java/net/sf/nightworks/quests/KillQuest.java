package net.sf.nightworks.quests;

import net.sf.nightworks.Nightworks;

public class KillQuest extends Quest {

    private int vnumToKill;

    public KillQuest(String name) {
        super(name);
    }

    public KillQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean doesQualify(Nightworks.CHAR_DATA ch) {
        return false;
    }

    @Override
    public boolean canRunAgain() {
        return false;
    }

    @Override
    public Object deliverReward(Nightworks.CHAR_DATA ch) {
        return null;
    }

    public int getVnumToKill() {
        return vnumToKill;
    }

    public void setVnumToKill(int vnumToKill) {
        this.vnumToKill = vnumToKill;
    }
}
