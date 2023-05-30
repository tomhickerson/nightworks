package net.sf.nightworks.enums;

public enum PlayerMessage {
	
	YOU_HAVE_BEEN_KILLED(1, "You have been {RKILLED{x!\n"),
	WEAPON_DOING_GREAT(2, "Your weapon whispers, '{cYou're doing great!{x'\n"),
	WEAPON_GOOD_WORK(3, "Your weapon whispers, '{cKeep up the good work!{x'\n"),
	WEAPON_CAN_DO_IT(4, "Your weapon whispers, '{cYou can do it!{x'\n"),
	WEAPON_RUN_AWAY(5, "Your weapon whispers, '{rRun away! Run away!{x'\n"),
	WEAPON_GLOWS_BLUE(6, "Your weapon glows {cblue.{x\n"),
	GODS_ARE_FURI(7, "The gods are infuriated!\n"),
	CONSUMNED_BY_RAGE(8, "Your pulse races as you are consumed by rage!\n"),
	TATOO_GLOWS_RED(9, "{rThe tattoo on your shoulder glows red.{x\n"),
	TATOO_GLOWS_BLUE(10, "{cThe tattoo on your shoulder glows blue.{x\n"),
	WELCOME_TO_SPELLBOUND(11, "\nWelcome to Multi User Dungeon of {RS{rp{oe{Yl{yl{Gb{go{vu{in{cd{x. Enjoy!!...\n"),
	HIT_RETURN_TO_CONT(12, "[Hit Return to Continue]\n"),
	GODS_FROWN(13, "The gods frown upon your actions.\n"),
	SMB_BEGINS_WHITE(14, "$p begins to shine a bright white.\n"),
	SMB_GLOWS_BLUE(15, "$p starts to glow with a blue aura.\n"),
	EXCAL_ACID_SPRAY(16, "Acid sprays from the blade of Excalibur.\n"),
	CONSUMED_BY_ANGER(17, "You are consumed by feelings of Anger!\n"),
	CONSUMED_BY_LUST(18, "You are consumed by feelings of Lust!\n"),
	CONSUMED_BY_ENVY(19, "You are consumed by feelings of Envy!\n"),
	FEEL_BY_SLOTH(20, "You are overcome with feelings of Sloth!\n"),
	SWELL_BY_PRIDE(21, "You swell with feelings of Pride!\n"),
	CONSUMED_BY_AVARICE(22, "You are overcome with feelings of Avarice!\n"),
	CONSUMED_BY_GLUTTONY(23, "You are overcome with feelings of Gluttony!\n"),
	FEEL_BY_FAITH(24,"You feel the virtue of Faith flow through you...\n"),
	FEEL_BY_HOPE(25, "You feel the virtue of Hope flow through you...\n"),
	FEEL_BY_COMPASSION(26, "You feel the virtue of Compassion flow through you...\n"),
	FEEL_BY_HUMILITY(27, "You feel the virtue of Humility flow through you...\n"),
	FEEL_BY_JUSTICE(28, "You feel the virtue of Justice flow through you...\n"),
	FEEL_BY_SACRIFICE(29, "You feel the virtue of Sacrifice flow through you...\n"),
	FEEL_BY_FORTITUDE(30, "You feel the virtue of Fortitude flow through you...\n");



	PlayerMessage(int id, String message) {
		this.id = id;
		this.message = message;
	}
	
	private final int id;
	private final String message;
	
	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
}
