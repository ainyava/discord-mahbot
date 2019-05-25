package handlers;

import audio.AudioHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import program.Main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// When somebody joins to channel do something
public class VoiceJoinHandler extends ListenerAdapter {

    private JDA jda;

    public VoiceJoinHandler(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        if (event.getMember().getId().equals(event.getJDA().getSelfUser().getId())) return;
        if (event.getMember().getUser().isBot()) return;


        // Check if there is a sound for user ?
        // Play someone joined to the channel
        // The Voices are in Persian language but you can change them for your own purpose!
        File f = new File(String.format("resources/voice/users/%s.mp3", event.getMember().getId()));
        if (f.exists()) {
            Main.audioHelper.loadItem(f.getAbsolutePath(), event.getMember(), event.getMember().getEffectiveName());
        } else {
            Main.audioHelper.loadItem(new File("resources/voice/users/someone.mp3").getAbsolutePath(), event.getMember(), event.getMember().getEffectiveName() + " joined!");
        }
        Main.audioHelper.loadItem(new File("resources/voice/joined.mp3").getAbsolutePath(), event.getMember(), event.getMember().getEffectiveName() + " joined!");

    }
}
