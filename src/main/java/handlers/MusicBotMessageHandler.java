package handlers;

import audio.AudioHelper;
import commands.GetTrackCommand;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.internal.audio.AudioConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import program.Config;
import program.Helpers;
import program.Main;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;


// This class if for commands and actions realted for muisc bots
// If you want to do something when a message sent for music bots
// You can write your action here
public class MusicBotMessageHandler {
    public  MusicBotMessageHandler(final MessageReceivedEvent event) {

        // Check if message is for music bots
        if (Helpers.isForMusicBot(event)) {
            String p = Helpers.getMusicBotPrefix(event);
            String c = Helpers.getCommand(event).substring(p.length());
            // If command is for playing music send avatar of singer!
            if (c.equals("p") || c.equals("play")) {
                //new GetTrackCommand(event, Helpers.getParams(event));
            } else {
                event.getMessage().delete().complete();
            }
        }



        // Delete extra garbage messages sent by music bots!
        {
            JSONObject obj = null;
            try {
                Object orgObj = new JSONParser().parse(new FileReader("resources/map/UsersMap.json"));
                obj = new JSONObject(orgObj.toString());
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

            JSONArray musicBots = obj.getJSONArray("musicBots");
            for (int i = 0; i < musicBots.length(); i++) {
                if (event.getAuthor().getId().equals(((JSONObject) musicBots.get(i)).getString("id"))) {
                    if(!Helpers.getMsg(event).contains("play") && !Helpers.getMsg(event).contains("queue")) {
                        event.getMessage().delete().complete();
                    }
                }
            }
        }

        // If message is for music bots
        if(Helpers.isForMusicBot(event)) {
            String prefix = Helpers.getMusicBotPrefix(event);

            // Not allowed keywords for using to play musics
            // The bot will mute music bot that called for playing something that is not allowed!
            // Censoring is not good but needed for some situations!

            for(String notAllowed: Config.notAllowedWordsToPlayMusic) {
                if(Helpers.getMsg(event).replaceAll(" ", "").contains(notAllowed)) {

                    JSONObject obj = null;
                    try {
                        Object orgObj = new JSONParser().parse(new FileReader("resources/map/UsersMap.json"));
                        obj = new JSONObject(orgObj.toString());
                    } catch (ParseException | IOException e) {
                        e.printStackTrace();
                    }

                    JSONArray musicBots = obj.getJSONArray("musicBots");
                    String botId = null;

                    for(int i=0; i<musicBots.length(); i++) {
                        if(prefix.equals(((JSONObject)musicBots.get(i)).getString("prefix"))) {
                            botId = ((JSONObject)musicBots.get(i)).getString("id");
                        }
                    }

                    System.out.println(botId);

                    event.getGuild().getController().setMute(event.getGuild().getMemberById(botId), true).queue();


                    // For our channel just load moaning sounds are enoying and can be abused to hurt members
                    // So the voice said muted dou to moaning! change it for your own purose!
                    Main.audioHelper.loadItem(new File("resources/events/mute/moan.mp3").getAbsolutePath(), event.getMember(), "Some one played moan!");
                }
            }
        }
    }
}
