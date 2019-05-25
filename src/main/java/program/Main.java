package program;
import audio.AudioHelper;
import audio.AudioPlayerSendHandler;
import audio.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import handlers.MessageHandler;
import handlers.VoiceJoinHandler;
import handlers.VoiceMoveHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nullable;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static AudioHelper audioHelper = null;

    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {


        // Initiate Bot!
        jda = new JDABuilder(AccountType.CLIENT)
                .setToken(Config.token)
                .build();

        audioHelper = new AudioHelper();

        // Initiate Handlers
        jda.addEventListener(new MessageHandler(jda));
        jda.addEventListener(new VoiceJoinHandler(jda));
        //jda.addEventListener(new VoiceMoveHandler(jda));

        jda.awaitReady();

        jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);

        jda.getPresence().setActivity(new MyActivity());
    }
}
