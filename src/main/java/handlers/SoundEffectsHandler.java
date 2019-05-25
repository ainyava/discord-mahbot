package handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import program.Helpers;
import program.Main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


// This class search for commands in SoundsMap and if there is a command mapped to a sound
// the bot will play that sound :)
// For example: fart is mapped to fart sound :D

public class SoundEffectsHandler {
    SoundEffectsHandler(MessageReceivedEvent event) {
        // Check if command is shortcut for playing sound effect
        try {
            Object obj = new JSONParser().parse(new FileReader("resources/map/SoundsMap.json"));
            JSONArray jsonArray = new JSONArray(obj.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                if (Helpers.command(event, jo.getString("shortcut"))) {
                    event.getMessage().delete().queue();
                    String path = String.format("resources/sounds/%s", jo.getString("file"));
                    String name = "**" + jo.getString("shortcut") + "** requested by **"+event.getMember().getEffectiveName()+"**";
                    event.getChannel().sendMessage(name).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                    Main.audioHelper.loadItem(new File(path).getAbsolutePath(), event.getMember(), name);
                    break;
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
