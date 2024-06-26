package net.sf.nightworks;

class Telnet {
/*
 * Definitions for the TELNET protocol.
 */

    static final byte IAC = (byte) 255;  /* interpret as command: */
    static final byte DONT = (byte) 254;    /* you are not to use option */
    static final byte DO = (byte) 253; /* please, you use option */
    static final byte WONT = (byte) 252;    /* I won't use option */
    static final byte WILL = (byte) 251;    /* I will use option */
    static final byte SB = (byte) 250;  /* interpret as subnegotiation */
    static final byte GA = (byte) 249;  /* you may reverse the line */
    static final byte EL = (byte) 248;  /* erase the current line */
    static final byte EC = (byte) 247;  /* erase the current character */
    static final byte AYT = (byte) 246;  /* are you there */
    static final byte AO = (byte) 245;  /* abort output--but let prog finish */
    static final byte IP = (byte) 244;  /* interrupt process--permanently */
    static final byte BREAK = (byte) 243;     /* break */
    static final byte DM = (byte) 242;   /* data mark--for connect. cleaning */
    static final byte NOP = (byte) 241;   /* nop */
    static final byte SE = (byte) 240;  /* end sub negotiation */
    static final byte EOR = (byte) 239;            /* end of record (transparent mode) */

    static final byte SYNCH = (byte) 242;     /* for telfunc calls */

    static final String telcmds[] = {"SE", "NOP", "DMARK", "BRK", "IP", "AO", "AYT", "EC",
            "EL", "GA", "SB", "WILL", "WONT", "DO", "DONT", "IAC"};

    /* telnet options */
    static final byte TELOPT_BINARY = 0;  /* 8-bit data path */
    static final byte TELOPT_ECHO = 1; /* echo */
    static final byte TELOPT_RCP = 2;  /* prepare to reconnect */
    static final byte TELOPT_SGA = 3; /* suppress go ahead */
    static final byte TELOPT_NAMS = 4; /* approximate message size */
    static final byte TELOPT_STATUS = 5;  /* give status */
    static final byte TELOPT_TM = 6; /* timing mark */
    static final byte TELOPT_RCTE = 7;/* remote controlled transmission and echo */
    static final byte TELOPT_NAOL = 8; /* negotiate about output line width */
    static final byte TELOPT_NAOP = 9; /* negotiate about output page size */
    static final byte TELOPT_NAOCRD = 10; /* negotiate about CR disposition */
    static final byte TELOPT_NAOHTS = 11; /* negotiate about horizontal tabstops */
    static final byte TELOPT_NAOHTD = 12; /* negotiate about horizontal tab disposition */
    static final byte TELOPT_NAOFFD = 13; /* negotiate about formfeed disposition */
    static final byte TELOPT_NAOVTS = 14; /* negotiate about vertical tab stops */
    static final byte TELOPT_NAOVTD = 15; /* negotiate about vertical tab disposition */
    static final byte TELOPT_NAOLFD = 16; /* negotiate about output LF disposition */
    static final byte TELOPT_XASCII = 17;  /* extended ascic character set */
    static final byte TELOPT_LOGOUT = 18; /* force logout */
    static final byte TELOPT_BM = 19;/* byte macro */
    static final byte TELOPT_DET = 20; /* data entry terminal */
    static final byte TELOPT_SUPDUP = 21;  /* supdup protocol */
    static final byte TELOPT_SUPDUPOUTPUT = 22;  /* supdup output */
    static final byte TELOPT_SNDLOC = 23; /* send location */
    static final byte TELOPT_TTYPE = 24;  /* terminal type */
    static final byte TELOPT_EOR = 25;/* end or record */
    static final byte TELOPT_EXOPL = (byte) 255;/* extended-options-list */

    static final byte NTELOPTS = (1 + TELOPT_EOR);
    static final String telopts[] = {
            "BINARY", "ECHO", "RCP", "SUPPRESS GO AHEAD", "NAME",
            "STATUS", "TIMING MARK", "RCTE", "NAOL", "NAOP",
            "NAOCRD", "NAOHTS", "NAOHTD", "NAOFFD", "NAOVTS",
            "NAOVTD", "NAOLFD", "EXTEND ASCII", "LOGOUT", "BYTE MACRO",
            "DATA ENTRY TERMINAL", "SUPDUP", "SUPDUP OUTPUT",
            "SEND LOCATION", "TERMINAL TYPE", "END OF RECORD"
    };

    /* sub-option qualifiers */
    static final int TELQUAL_IS = 0;   /* option is... */
    static final int TELQUAL_SEND = 1;   /* send option */

    static String toColor(char colorCode) {
        String color;
        switch (colorCode) {
            case 'x':
                color = "\033[m";
                break;

            case 'd':
                color = "\033[0;30m";
                break;

            case 'r':
                color = "\033[0;31m";
                break;

            case 'g':
                color = "\033[0;32m";
                break;

            case 'y':
                color = "\033[0;33m";
                break;

            case 'b':
                color = "\033[0;34m";
                break;

            case 'm':
                color = "\033[0;35m";
                break;

            case 'c':
                color = "\033[0;36m";
                break;

            case 'w':
                color = "\033[0;37m";
                break;

            case 'e':
                // dark blue background
                color = "\033[0;44m";
                break;

            case 'f':
                // dark cyan background
                color = "\033[0;46m";
                break;

            case 'h':
                // return background to default
                color = "\033[0;49m";
                break;

            // brighter
            case 'D':
                color = "\033[1;30m";
                break;

            case 'R':
                color = "\033[1;31m";
                break;

            case 'G':
                color = "\033[1;32m";
                break;

            case 'Y':
                color = "\033[1;33m";
                break;

            case 'B':
                color = "\033[1;34m";
                break;

            case 'M':
                color = "\033[1;35m";
                break;

            case 'C':
                color = "\033[1;36m";
                break;

            case 'W':
                color = "\033[1;37m";
                break;

            // special
            case '*':
                color = "\007";
                break;

                // trying some 256 colors here - i for indigo-violet-ish
            case 'i':
                color = "\033[38;5;135m";
                break;

            case 'o':
                // peach orange
                color = "\033[38;5;166m";
                break;

            case 'a':
                // very light green, change to olive green
                color = "\033[38;5;106m";
                break;

            case 'v':
                // very light blue
                color = "\033[38;5;39m";
                break;

            case '1':
                // darkest gray, good for disappearing
                color = "\033[38;5;239m";
                break;

            case '2':
                color = "\033[38;5;241m";
                break;

            case '3':
                color = "\033[38;5;243m";
                break;

            case '4':
                color = "\033[38;5;245m";
                break;

            case '5':
                color = "\033[38;5;246m";
                break;

            case '6':
                color = "\033[38;5;247m";
                break;

            case '7':
                color = "\033[38;5;248m";
                break;

            case '8':
                color = "\033[38;5;250m";
                break;

            case '9':
                color = "\033[38;5;252m";
                break;

            default:
                //todo: warn
                color = "";
                break;
        }
        return color;
    }
}
