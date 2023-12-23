Nightworks is a Java port of Anatolia MUD 3.1.0 C-language codebase
Anatolia 3.1 was released in 2003 and is based on ROM2.4 -> Merc 2 -> Diku

Full **Anatolia** credits can be found [here](https://anatoliamud.sourceforge.net/credits/).

Full **Merc** credits can be found [here](https://github.com/alexmchale/merc-mud/blob/master/README).

Full **ROM** credits can be found [here](https://github.com/tomhickerson/nightworks/blob/master/doc/licence/rom.credits).

And full **Diku** credits can be found [here](https://dikumud.com/credits/).

Requirements:
- Java 8
- Maven 3.5.0 or later

Steps to run:
1. From the command line, go to the root directly for the project
2. type 'mvn clean compile' or 'mvn clean install'
3. type 'java -cp target\classes net.sf.nightworks.Comm'
4. Nightworks is now running on localhost port 4000
