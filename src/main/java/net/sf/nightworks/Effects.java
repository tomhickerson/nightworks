package net.sf.nightworks;

import static net.sf.nightworks.Comm.act;
import static net.sf.nightworks.Comm.send_to_char;
import static net.sf.nightworks.DB.number_percent;
import static net.sf.nightworks.DB.number_range;
import static net.sf.nightworks.Handler.affect_enchant;
import static net.sf.nightworks.Handler.affect_join;
import static net.sf.nightworks.Handler.affect_to_char;
import static net.sf.nightworks.Handler.check_material;
import static net.sf.nightworks.Handler.extract_obj;
import static net.sf.nightworks.Handler.obj_from_obj;
import static net.sf.nightworks.Handler.obj_to_room;
import static net.sf.nightworks.Magic.saves_spell;
import static net.sf.nightworks.Nightworks.AFFECT_DATA;
import static net.sf.nightworks.Nightworks.AFF_BLIND;
import static net.sf.nightworks.Nightworks.AFF_POISON;
import static net.sf.nightworks.Nightworks.AFF_SCREAM;
import static net.sf.nightworks.Nightworks.APPLY_AC;
import static net.sf.nightworks.Nightworks.APPLY_HITROLL;
import static net.sf.nightworks.Nightworks.APPLY_NONE;
import static net.sf.nightworks.Nightworks.APPLY_STR;
import static net.sf.nightworks.Nightworks.CHAR_DATA;
import static net.sf.nightworks.Nightworks.COND_HUNGER;
import static net.sf.nightworks.Nightworks.COND_THIRST;
import static net.sf.nightworks.Nightworks.DAM_COLD;
import static net.sf.nightworks.Nightworks.DAM_FIRE;
import static net.sf.nightworks.Nightworks.DAM_LIGHTNING;
import static net.sf.nightworks.Nightworks.DAM_POISON;
import static net.sf.nightworks.Nightworks.DAM_SOUND;
import static net.sf.nightworks.Nightworks.DAZE_STATE;
import static net.sf.nightworks.Nightworks.IS_AFFECTED;
import static net.sf.nightworks.Nightworks.IS_NPC;
import static net.sf.nightworks.Nightworks.IS_OBJ_STAT;
import static net.sf.nightworks.Nightworks.ITEM_ARMOR;
import static net.sf.nightworks.Nightworks.ITEM_BLESS;
import static net.sf.nightworks.Nightworks.ITEM_BURN_PROOF;
import static net.sf.nightworks.Nightworks.ITEM_CLOTHING;
import static net.sf.nightworks.Nightworks.ITEM_CONTAINER;
import static net.sf.nightworks.Nightworks.ITEM_CORPSE_NPC;
import static net.sf.nightworks.Nightworks.ITEM_CORPSE_PC;
import static net.sf.nightworks.Nightworks.ITEM_DRINK_CON;
import static net.sf.nightworks.Nightworks.ITEM_FOOD;
import static net.sf.nightworks.Nightworks.ITEM_JEWELRY;
import static net.sf.nightworks.Nightworks.ITEM_NOPURGE;
import static net.sf.nightworks.Nightworks.ITEM_PILL;
import static net.sf.nightworks.Nightworks.ITEM_POTION;
import static net.sf.nightworks.Nightworks.ITEM_SCROLL;
import static net.sf.nightworks.Nightworks.ITEM_STAFF;
import static net.sf.nightworks.Nightworks.ITEM_WAND;
import static net.sf.nightworks.Nightworks.OBJ_DATA;
import static net.sf.nightworks.Nightworks.ROOM_INDEX_DATA;
import static net.sf.nightworks.Nightworks.TARGET_CHAR;
import static net.sf.nightworks.Nightworks.TARGET_OBJ;
import static net.sf.nightworks.Nightworks.TARGET_ROOM;
import static net.sf.nightworks.Nightworks.TO_AFFECTS;
import static net.sf.nightworks.Nightworks.TO_ALL;
import static net.sf.nightworks.Nightworks.TO_CHAR;
import static net.sf.nightworks.Nightworks.TO_ROOM;
import static net.sf.nightworks.Nightworks.UMAX;
import static net.sf.nightworks.Nightworks.URANGE;
import static net.sf.nightworks.Nightworks.WEAR_NONE;
import static net.sf.nightworks.Skill.gsn_poison;
import static net.sf.nightworks.Skill.gsn_scream;
import static net.sf.nightworks.Update.gain_condition;

class Effects {
    static void acid_effect(Object vo, int level, int dam, int target) {
        if (target == TARGET_ROOM) /* nail objects on the floor */ {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;

            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                acid_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR)  /* do the effect on a victim */ {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            /* let's toast some gear */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                acid_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ) /* toast an object */ {
            OBJ_DATA obj = (OBJ_DATA) vo;
            OBJ_DATA t_obj, n_obj;
            int chance;

            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_NOPURGE)
                    || obj.pIndexData.limit != -1
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;

            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            if (IS_OBJ_STAT(obj, ITEM_BLESS)) {
                chance -= 5;
            }

            chance -= obj.level * 2;

            String msg;
            switch (obj.item_type) {
                default:
                    return;
                case ITEM_CONTAINER:
                case ITEM_CORPSE_PC:
                case ITEM_CORPSE_NPC:
                    msg = "{o$p fumes and dissolves.{x";
                    break;
                case ITEM_ARMOR:
                    msg = "{o$p is pitted and etched.{x";
                    break;
                case ITEM_CLOTHING:
                    msg = "{o$p is corroded into scrap.{x";
                    break;
                case ITEM_STAFF:
                case ITEM_WAND:
                    chance -= 10;
                    msg = "{o$p corrodes and breaks.{x";
                    break;
                case ITEM_SCROLL:
                    chance += 10;
                    msg = "{o$p is burned into waste.{x";
                    break;
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            if (obj.carried_by != null) {
                act(msg, obj.carried_by, obj, null, TO_ALL);
            } else if (obj.in_room != null && obj.in_room.people != null) {
                act(msg, obj.in_room.people, obj, null, TO_ALL);
            }

            if (obj.item_type == ITEM_ARMOR)  /* etch it */ {
                AFFECT_DATA paf;
                boolean af_found = false;
                int i;

                affect_enchant(obj);

                for (paf = obj.affected; paf != null; paf = paf.next) {
                    if (paf.location == APPLY_AC) {
                        af_found = true;
                        paf.type = null;
                        paf.modifier += 1;
                        paf.level = UMAX(paf.level, level);
                        break;
                    }
                }

                if (!af_found)
                    /* needs a new affect */ {
                    paf = new AFFECT_DATA();

                    paf.type = null;
                    paf.level = level;
                    paf.duration = -1;
                    paf.location = APPLY_AC;
                    paf.modifier = 1;
                    paf.bitvector = 0;
                    paf.next = obj.affected;
                    obj.affected = paf;
                }

                if (obj.carried_by != null && obj.wear_loc != WEAR_NONE) {
                    for (i = 0; i < 4; i++) {
                        obj.carried_by.armor[i] += 1;
                    }
                }
                return;
            }

            /* get rid of the object */
            if (obj.contains != null)  /* dump contents */ {
                for (t_obj = obj.contains; t_obj != null; t_obj = n_obj) {
                    n_obj = t_obj.next_content;
                    obj_from_obj(t_obj);
                    if (obj.in_room != null) {
                        obj_to_room(t_obj, obj.in_room);
                    } else if (obj.carried_by != null) {
                        obj_to_room(t_obj, obj.carried_by.in_room);
                    } else {
                        extract_obj(t_obj);
                        continue;
                    }

                    acid_effect(t_obj, level / 2, dam / 2, TARGET_OBJ);
                }
            }

            extract_obj(obj);
        }
    }


    static void cold_effect(Object vo, int level, int dam, int target) {
        if (target == TARGET_ROOM) /* nail objects on the floor */ {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;

            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                cold_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR) /* whack a character */ {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            /* chill touch effect */
            if (!saves_spell(level / 4 + dam / 20, victim, DAM_COLD)) {
                AFFECT_DATA af = new AFFECT_DATA();

                act("{v$n turns blue and shivers.{x", victim, null, null, TO_ROOM);
                act("{vA chill sinks deep into your bones.{x", victim, null, null, TO_CHAR);
                af.where = TO_AFFECTS;
                af.type = Skill.gsn_chill_touch;
                af.level = level;
                af.duration = 6;
                af.location = APPLY_STR;
                af.modifier = -1;
                af.bitvector = 0;
                affect_join(victim, af);
            }

            /* hunger! (warmth sucked out */
            if (!IS_NPC(victim)) {
                gain_condition(victim, COND_HUNGER, dam / 20);
            }

            /* let's toast some gear */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                cold_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ) /* toast an object */ {
            OBJ_DATA obj = (OBJ_DATA) vo;
            int chance;

            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_NOPURGE)
                    || obj.pIndexData.limit != -1
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;

            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            if (IS_OBJ_STAT(obj, ITEM_BLESS)) {
                chance -= 5;
            }

            chance -= obj.level * 2;

            String msg;
            switch (obj.item_type) {
                default:
                    return;
                case ITEM_POTION:
                    msg = "{v$p freezes and shatters!{x";
                    chance += 25;
                    break;
                case ITEM_DRINK_CON:
                    msg = "{v$p freezes and shatters!{x";
                    chance += 5;
                    break;
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            if (obj.carried_by != null) {
                act(msg, obj.carried_by, obj, null, TO_ALL);
            } else if (obj.in_room != null && obj.in_room.people != null) {
                act(msg, obj.in_room.people, obj, null, TO_ALL);
            }

            extract_obj(obj);
        }
    }


    static void fire_effect(Object vo, int level, int dam, int target) {

        if (target == TARGET_ROOM)  /* nail objects on the floor */ {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;
            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                fire_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR)   /* do the effect on a victim */ {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            /* chance of blindness */
            if (!IS_AFFECTED(victim, AFF_BLIND)
                    && !saves_spell(level / 4 + dam / 20, victim, DAM_FIRE)) {
                AFFECT_DATA af = new AFFECT_DATA();
                act("{5$n is blinded by smoke!{x", victim, null, null, TO_ROOM);
                act("{5Your eyes tear up from smoke...you can't see a thing!{x",
                        victim, null, null, TO_CHAR);

                af.where = TO_AFFECTS;
                af.type = Skill.gsn_fire_breath;
                af.level = level;
                af.duration = number_range(0, level / 10);
                af.location = APPLY_HITROLL;
                af.modifier = -4;
                af.bitvector = AFF_BLIND;

                affect_to_char(victim, af);
            }

            /* getting thirsty */
            if (!IS_NPC(victim)) {
                gain_condition(victim, COND_THIRST, dam / 20);
            }

            /* let's toast some gear! */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;

                fire_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ)  /* toast an object */ {
            OBJ_DATA obj = (OBJ_DATA) vo;
            OBJ_DATA t_obj, n_obj;
            int chance;

            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_NOPURGE)
                    || obj.pIndexData.limit != -1
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;

            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            if (IS_OBJ_STAT(obj, ITEM_BLESS)) {
                chance -= 5;
            }
            chance -= obj.level * 2;

            String msg;
            if (check_material(obj, "ice")) {
                chance += 30;
                msg = "{i$p melts and evaporates!{x";
            } else {
                switch (obj.item_type) {
                    default:
                        return;
                    case ITEM_CONTAINER:
                        msg = "{i$p ignites and burns!{x";
                        break;
                    case ITEM_POTION:
                        chance += 25;
                        msg = "{i$p bubbles and boils!{x";
                        break;
                    case ITEM_SCROLL:
                        chance += 50;
                        msg = "{i$p crackles and burns!{x";
                        break;
                    case ITEM_STAFF:
                        chance += 10;
                        msg = "{i$p smokes and chars!{x";
                        break;
                    case ITEM_WAND:
                        msg = "{i$p sparks and sputters!{x";
                        break;
                    case ITEM_FOOD:
                        msg = "{i$p blackens and crisps!{x";
                        break;
                    case ITEM_PILL:
                        msg = "{i$p melts and drips!{x";
                        break;
                }
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            if (obj.carried_by != null) {
                act(msg, obj.carried_by, obj, null, TO_ALL);
            } else if (obj.in_room != null && obj.in_room.people != null) {
                act(msg, obj.in_room.people, obj, null, TO_ALL);
            }

            if (obj.contains != null) {
                /* dump the contents */

                for (t_obj = obj.contains; t_obj != null; t_obj = n_obj) {
                    n_obj = t_obj.next_content;
                    obj_from_obj(t_obj);
                    if (obj.in_room != null) {
                        obj_to_room(t_obj, obj.in_room);
                    } else if (obj.carried_by != null) {
                        obj_to_room(t_obj, obj.carried_by.in_room);
                    } else {
                        extract_obj(t_obj);
                        continue;
                    }
                    fire_effect(t_obj, level / 2, dam / 2, TARGET_OBJ);
                }
            }

            extract_obj(obj);
        }
    }

    static void poison_effect(Object vo, int level, int dam, int target) {
        if (target == TARGET_ROOM)  /* nail objects on the floor */ {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;

            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                poison_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR)   /* do the effect on a victim */ {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            /* chance of poisoning */
            if (!saves_spell(level / 4 + dam / 20, victim, DAM_POISON)) {
                AFFECT_DATA af = new AFFECT_DATA();

                send_to_char("{aYou feel poison coursing through your veins.\n{x", victim);
                act("{a$n looks very ill.{x", victim, null, null, TO_ROOM);

                af.where = TO_AFFECTS;
                af.type = gsn_poison;
                af.level = level;
                af.duration = level / 2;
                af.location = APPLY_STR;
                af.modifier = -1;
                af.bitvector = AFF_POISON;
                affect_join(victim, af);
            }

            /* equipment */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                poison_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ)  /* do some poisoning */ {
            OBJ_DATA obj = (OBJ_DATA) vo;
            int chance;


            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_BLESS)
                    || obj.pIndexData.limit != -1
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;
            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            chance -= obj.level * 2;

            switch (obj.item_type) {
                default:
                    return;
                case ITEM_FOOD:
                    break;
                case ITEM_DRINK_CON:
                    if (obj.value[0] == obj.value[1]) {
                        return;
                    }
                    break;
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            obj.value[3] = 1;
        }
    }


    static void shock_effect(Object vo, int level, int dam, int target) {
        if (target == TARGET_ROOM) {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;

            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                shock_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR) {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            /* daze and confused? */
            if (!saves_spell(level / 4 + dam / 20, victim, DAM_LIGHTNING)) {
                send_to_char("{YYour muscles stop responding.\n{x", victim);
                DAZE_STATE(victim, UMAX(12, level / 4 + dam / 20));
            }

            /* toast some gear */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                shock_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ) {
            OBJ_DATA obj = (OBJ_DATA) vo;
            int chance;

            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_NOPURGE)
                    || obj.pIndexData.limit != -1
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;

            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            if (IS_OBJ_STAT(obj, ITEM_BLESS)) {
                chance -= 5;
            }

            chance -= obj.level * 2;

            String msg;
            switch (obj.item_type) {
                default:
                    return;
                case ITEM_WAND:
                case ITEM_STAFF:
                    chance += 10;
                    msg = "$p overloads and explodes!";
                    break;
                case ITEM_JEWELRY:
                    chance -= 10;
                    msg = "$p is fused into a worthless lump.";
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            if (obj.carried_by != null) {
                act(msg, obj.carried_by, obj, null, TO_ALL);
            } else if (obj.in_room != null && obj.in_room.people != null) {
                act(msg, obj.in_room.people, obj, null, TO_ALL);
            }

            extract_obj(obj);
        }
    }

    static void sand_effect(Object vo, int level, int dam, int target) {
        if (target == TARGET_ROOM) /* nail objects on the floor */ {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;

            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                sand_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR)  /* do the effect on a victim */ {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            if (!IS_AFFECTED(victim, AFF_BLIND)
                    && !saves_spell(level / 4 + dam / 20, victim, DAM_COLD)) {
                AFFECT_DATA af = new AFFECT_DATA();
                act("{Y$n is blinded by flying sands!{x", victim, null, null, TO_ROOM);
                act("{YYour eyes tear up from sands...you can't see a thing!{x",
                        victim, null, null, TO_CHAR);

                af.where = TO_AFFECTS;
                af.type = Skill.gsn_sand_storm;
                af.level = level;
                af.duration = number_range(0, level / 10);
                af.location = APPLY_HITROLL;
                af.modifier = -4;
                af.bitvector = AFF_BLIND;

                affect_to_char(victim, af);
            }

            /* let's toast some gear */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                sand_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ) /* toast an object */ {
            OBJ_DATA obj = (OBJ_DATA) vo;
            OBJ_DATA t_obj, n_obj;
            int chance;

            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_NOPURGE)
                    || obj.pIndexData.limit != -1
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;

            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            if (IS_OBJ_STAT(obj, ITEM_BLESS)) {
                chance -= 5;
            }

            chance -= obj.level * 2;

            String msg;
            switch (obj.item_type) {
                default:
                    return;
                case ITEM_CONTAINER:
                case ITEM_CORPSE_PC:
                case ITEM_CORPSE_NPC:
                    chance += 50;
                    msg = "{Y$p is filled with sand and evaporates.{x";
                    break;
                case ITEM_ARMOR:
                    chance -= 10;
                    msg = "{Y$p is etched by sand.{x";
                    break;
                case ITEM_CLOTHING:
                    msg = "{Y$p is corroded by sands.{x";
                    break;
                case ITEM_WAND:
                    chance = 50;
                    msg = "{Y$p mixes with crashing sands.{x";
                    break;
                case ITEM_SCROLL:
                    chance += 20;
                    msg = "{Y$p is surrounded by sand.{x";
                    break;
                case ITEM_POTION:
                    chance += 10;
                    msg = "{Y$p is broken into pieces by crushing sands.{x";
                    break;
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            if (obj.carried_by != null) {
                act(msg, obj.carried_by, obj, null, TO_ALL);
            } else if (obj.in_room != null && obj.in_room.people != null) {
                act(msg, obj.in_room.people, obj, null, TO_ALL);
            }

            if (obj.item_type == ITEM_ARMOR)  /* etch it */ {
                AFFECT_DATA paf;
                boolean af_found = false;
                int i;

                affect_enchant(obj);

                for (paf = obj.affected; paf != null; paf = paf.next) {
                    if (paf.location == APPLY_AC) {
                        af_found = true;
                        paf.type = null;
                        paf.modifier += 1;
                        paf.level = UMAX(paf.level, level);
                        break;
                    }
                }

                if (!af_found)
                    /* needs a new affect */ {
                    paf = new AFFECT_DATA();

                    paf.type = null;
                    paf.level = level;
                    paf.duration = level;
                    paf.location = APPLY_AC;
                    paf.modifier = 1;
                    paf.bitvector = 0;
                    paf.next = obj.affected;
                    obj.affected = paf;
                }

                if (obj.carried_by != null && obj.wear_loc != WEAR_NONE) {
                    for (i = 0; i < 4; i++) {
                        obj.carried_by.armor[i] += 1;
                    }
                }
                return;
            }

            /* get rid of the object */
            if (obj.contains != null)  /* dump contents */ {
                for (t_obj = obj.contains; t_obj != null; t_obj = n_obj) {
                    n_obj = t_obj.next_content;
                    obj_from_obj(t_obj);
                    if (obj.in_room != null) {
                        obj_to_room(t_obj, obj.in_room);
                    } else if (obj.carried_by != null) {
                        obj_to_room(t_obj, obj.carried_by.in_room);
                    } else {
                        extract_obj(t_obj);
                        continue;
                    }

                    sand_effect(t_obj, level / 2, dam / 2, TARGET_OBJ);
                }
            }

            extract_obj(obj);
        }
    }

    static void scream_effect(Object vo, int level, int dam, int target) {

        if (target == TARGET_ROOM)  /* nail objects on the floor */ {
            ROOM_INDEX_DATA room = (ROOM_INDEX_DATA) vo;
            OBJ_DATA obj, obj_next;
            for (obj = room.contents; obj != null; obj = obj_next) {
                obj_next = obj.next_content;
                scream_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_CHAR)   /* do the effect on a victim */ {
            CHAR_DATA victim = (CHAR_DATA) vo;
            OBJ_DATA obj, obj_next;

            if (!saves_spell(level / 4 + dam / 20, victim, DAM_SOUND)) {
                AFFECT_DATA af = new AFFECT_DATA();
                act("$n can't hear anything!", victim, null, null, TO_ROOM);
                act("You can't hear a thing!", victim, null, null, TO_CHAR);

                af.where = TO_AFFECTS;
                af.type = gsn_scream;
                af.level = level;
                af.duration = 0;
                af.location = APPLY_NONE;
                af.modifier = 0;
                af.bitvector = AFF_SCREAM;

                affect_to_char(victim, af);
            }

            /* daze and confused? */
            if (!saves_spell(level / 4 + dam / 20, victim, DAM_SOUND)) {
                send_to_char("You can't hear anything!.\n", victim);
                DAZE_STATE(victim, UMAX(12, level / 4 + dam / 20));
            }

            /* getting thirsty */
            if (!IS_NPC(victim)) {
                gain_condition(victim, COND_THIRST, dam / 20);
            }

            /* let's toast some gear! */
            for (obj = victim.carrying; obj != null; obj = obj_next) {
                obj_next = obj.next_content;

                scream_effect(obj, level, dam, TARGET_OBJ);
            }
            return;
        }

        if (target == TARGET_OBJ)  /* toast an object */ {
            OBJ_DATA obj = (OBJ_DATA) vo;
            OBJ_DATA t_obj, n_obj;
            int chance;

            if (IS_OBJ_STAT(obj, ITEM_BURN_PROOF)
                    || IS_OBJ_STAT(obj, ITEM_NOPURGE)
                    || number_range(0, 4) == 0) {
                return;
            }

            chance = level / 4 + dam / 10;

            if (chance > 25) {
                chance = (chance - 25) / 2 + 25;
            }
            if (chance > 50) {
                chance = (chance - 50) / 2 + 50;
            }

            if (IS_OBJ_STAT(obj, ITEM_BLESS)) {
                chance -= 5;
            }
            chance -= obj.level * 2;
            String msg;
            if (check_material(obj, "ice")) {
                chance += 30;
                msg = "{v$p breaks and evaporates!{x";
            } else if (check_material(obj, "glass")) {
                chance += 30;
                msg = "{v$p breaks into tiny small pieces.{x";
            } else {
                switch (obj.item_type) {
                    default:
                        return;
                    case ITEM_POTION:
                        chance += 25;
                        msg = "{vA vial of $p breaks and the liquid spoils!{x";
                        break;
                    case ITEM_SCROLL:
                        chance += 50;
                        msg = "{v$p breaks into tiny pieces!{x";
                        break;
                    case ITEM_DRINK_CON:
                        msg = "{v$p breaks and liquid spoils!{x";
                        chance += 5;
                        break;
                    case ITEM_PILL:
                        msg = "{v$p breaks into pieces!{x";
                        break;
                }
            }

            chance = URANGE(5, chance, 95);

            if (number_percent() > chance) {
                return;
            }

            if (obj.carried_by != null) {
                act(msg, obj.carried_by, obj, null, TO_ALL);
            } else if (obj.in_room != null && obj.in_room.people != null) {
                act(msg, obj.in_room.people, obj, null, TO_ALL);
            }

            if (obj.contains != null) {
                /* dump the contents */

                for (t_obj = obj.contains; t_obj != null; t_obj = n_obj) {
                    n_obj = t_obj.next_content;
                    obj_from_obj(t_obj);
                    if (obj.in_room != null) {
                        obj_to_room(t_obj, obj.in_room);
                    } else if (obj.carried_by != null) {
                        obj_to_room(t_obj, obj.carried_by.in_room);
                    } else {
                        extract_obj(t_obj);
                        continue;
                    }
                    scream_effect(t_obj, level / 2, dam / 2, TARGET_OBJ);
                }
            }

            extract_obj(obj);
        }
    }
}
