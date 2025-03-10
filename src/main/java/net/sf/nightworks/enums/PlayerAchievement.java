package net.sf.nightworks.enums;

import java.util.HashMap;
import java.util.Map;

public enum PlayerAchievement {
    KILL_50_MOBS(1, "Kill 50 Monsters in the MUD"),
    EARN_15_LEVELS(2, "Earn 15 levels in your class"),
    TALK_TO_SEWER_GARGOYLE(3, "Meet the Gargoyle in the Sewer", false),
    FIND_FENNEL_FOR_THE_DRUID(4, "Find Fennel for the Druid in the Plains", false),
    KILL_RABBIT_FOR_THE_HERMIT(5, "Kill a rabbit for the Hermit in the Plains", false),
    FIND_ALMS_FOR_THE_PILGRIM(6, "Find alms for the Blind Pilgrim", false),
    KILL_MORE_RABBITS_FOR_THE_HERMIT(7, "Kill more rabbits for the Hermit in the Plains", false),
    TALK_TO_FOREST_GARGOYLE(8, "Meet the Gargoyle in the Forest", false),
    LITTLE_GIRLS_LOST(9, "Find out what happened to the girls in the Orphanage", false),
    TALK_TO_MYTHRAS_GARGOYLE(10, "Meet the Gargoyle in Mythras"),
    TALK_TO_MYTHAIN_GARGOYLE(11, "Meet the Gargoyle in Mythain"),
    DIE_ONCE(12,"Perish one time in Spellbound"),
    FIND_THE_KERCHIEF(1001, "Find the handkerchief for the noblewoman", false);


    PlayerAchievement(int id, String desc) {
        this.id = id;
        this.description = desc;
        this.isPublic = true;
    }

    PlayerAchievement(int id, String desc, boolean isPublic) {
        this.id = id;
        this.description = desc;
        this.isPublic = isPublic;
    }

    private final int id;
    private final String description;
    private final boolean isPublic;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    private static final PlayerAchievement[] achievements = PlayerAchievement.values();

    public static final Map<Integer, PlayerAchievement> achieveMap = new HashMap<>();

    static {
        for (PlayerAchievement pa : achievements) {
            achieveMap.put(pa.getId(), pa);
        }
    }

    /**
     * these both can return null, so watch out
     * @param id Our identifier which will go into CSV lists
     * @return A single Player Achievement
     */
    public PlayerAchievement lookupPublicAchievement(Integer id) {
        PlayerAchievement pa = achieveMap.get(id);
        if (pa != null && pa.isPublic()) {
            return pa;
        }
        return null;
    }

    public static PlayerAchievement lookupAchievement(Integer id) {
        return achieveMap.get(id);
    }

    public boolean equals(PlayerAchievement pa) {
        return this.getId() == pa.getId();
    }
}
