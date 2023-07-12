package net.sf.nightworks.quests;

import java.util.ArrayList;

/**
 * SimpleHuntQuest
 * The quest for hunting down several of one vnum of mobs and killing them
 * Can also be for hunting down several vnums?  Nope, that is for the Defend Quest
 */
public class SimpleHuntQuest extends SimpleQuest {

    private int vnumToKill = -1;
    private int numberToKill = -1;
    private ArrayList<Integer> rooms = new ArrayList<>();

    public SimpleHuntQuest(String name) {
        super(name);
    }

    public SimpleHuntQuest(int id, String name) {
        super(id, name);
    }

    public int getVnumToKill() {
        return vnumToKill;
    }

    public void setVnumToKill(int vnumToKill) {
        this.vnumToKill = vnumToKill;
    }

    public int getNumberToKill() {
        return numberToKill;
    }

    public void setNumberToKill(int numberToKill) {
        this.numberToKill = numberToKill;
    }

    public ArrayList<Integer> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Integer> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean isStandalone() {
        return false;
    }
}
