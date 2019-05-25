package handlers;

import audio.AudioHelper;
import commands.CallGameCommand;
import commands.GetTrackCommand;
import commands.SpankCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.*;
import program.Config;
import program.Helpers;
import program.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static program.Main.audioHelper;


public class MessageHandler extends ListenerAdapter {

    private JDA jda;

    public MessageHandler(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Get Message
        String msg = event.getMessage().getContentRaw();

        // Initialize command groups here
        // If you didnt want any feature just comment the line
        new BotsMessageHandler(event);
        new MusicBotMessageHandler(event);
        new StatsHandler(event);
        new MediaPlayerHandler(event);
        new SoundEffectsHandler(event);
        new ImageEffectsHandler(event);
        new FunHandler(event);
        new GamesHandler(event);
    }


}
