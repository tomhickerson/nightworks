package net.sf.nightworks.enums;

import java.util.HashMap;
import java.util.Map;

public enum PlayerAchievement {
    KILL_50_MOBS(1, "Kill 50 Monsters in the MUD"),
    EARN_15_LEVELS(2, "Earn 15 levels in your class"),
    TALK_TO_SEWER_GARGOYLE(3, "Meet the Gargoyle in the Sewer", false);

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

    public PlayerAchievement lookupAchievement(Integer id) {
        return achieveMap.get(id);
    }
}
