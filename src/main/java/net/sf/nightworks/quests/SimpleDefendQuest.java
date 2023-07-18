package net.sf.nightworks.quests;

import java.util.ArrayList;

/**
 * SimpleDefendQuest, where you defend a given area against a number of attackers
 * Attackers can be different mobs or not
 * Attackers will have the 'flee' flag activated
 * Attackers will have to be placed by the quest
 * maybe need a setup quest interface, to run it?
 */
public class SimpleDefendQuest extends SimpleQuest {

    private ArrayList<Integer> vnumMobs = new ArrayList<>();
    private ArrayList<Integer> rooms = new ArrayList<>();
    private setup questSetup;

    public setup getQuestSetup() {
        return questSetup;
    }

    public void setQuestSetup(setup questSetup) {
        this.questSetup = questSetup;
    }

    public void runSetup() {
        this.questSetup.run();
    }

    public ArrayList<Integer> getVnumMobs() {
        return vnumMobs;
    }

    public void setVnumMobs(ArrayList<Integer> vnumMobs) {
        this.vnumMobs = vnumMobs;
    }

    public ArrayList<Integer> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Integer> rooms) {
        this.rooms = rooms;
    }

    public SimpleDefendQuest(String name) {
        super(name);
    }

    public SimpleDefendQuest(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isStandalone() {
        return false;
    }

    public interface setup {
        void run();
    }
}
