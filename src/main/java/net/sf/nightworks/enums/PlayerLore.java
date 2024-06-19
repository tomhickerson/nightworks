package net.sf.nightworks.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Player Lore, where we can add or remove lore from a player.
 * We can also add knowledge of:
 * - ancient languages
 * - gods long forgotten
 * - apocryphal magic items
 * - secret password for the thieves' guild
 * - etc.
 * - possible addition of levels of mastery, i.e. I, II or III
 */
public enum PlayerLore {
    KNOWLEDGE_SOULCUBE(1, "Knowledge of the Soul Cube"),
    ANCIENT_ELVISH(2, "Ancient Elvish"),
    EASTERN_ELVISH(3, "Eastern Elvish");

    PlayerLore(int id, String knowledge) {
        this.id = id;
        this.knowledge = knowledge;
    }

    private final int id;
    private final String knowledge;

    public int getId() {
        return id;
    }

    public String getKnowledge() {
        return knowledge;
    }

    private static final PlayerLore[] lore = PlayerLore.values();

    public static final Map<Integer, PlayerLore> loreMap = new HashMap<>();

    static {
        for (PlayerLore pl : lore) {
            loreMap.put(pl.getId(), pl);
        }
    }

    public static PlayerLore lookupLore(int id) {
        return loreMap.get(id);
    }
}
