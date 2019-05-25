# DiscordMahbot
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Discord Mahbot is a bot for discord written with JAVA and JDA library.
  - Playing sounds and fun voices
  - Image manipulation and Music bot artist images
  - Moderator
  - your own commands can add easily!


### Configuration
Before build and run you need to configre your token and prefix and etc.

**Edit the `Config.java` file and put your token there.**

Alose some external commands in python folder need tokens too

**edit them on `Config.py` file too.**


### Build and Run
Use maven for dependency managemanet and build
So
1. Download source code: `git clone https://github.com/mahcodes/discord-mahbot`
2. `cd disocrd-mahbot`
3. **Install Dependencides:** `mvn install`
4. **Build::** `mvn package`
this will generate a `mahbot-with-dependencies.jar` file that can easily run by
`java -jar mahbot-with-dependencies.jar`

**
Note: when you run the jar file it cant find resources folder


So you need to copy for create symlink of `src/main/resources` folder in your `target` folder (build folder that maven creates)


Also copy or symlink Python folder in `target/commands` folder to recognize external commands.
**

### Contribution
Contributions are wellcome so please:

_**Give star if you liked this project**_

_**Pull request your own codes**_

_**Create issues and comments to fix bugs and feature request**_


##### **Thats it, Good Luck**