package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import program.Main;

import java.net.URL;


// Audio helper class helps other part of the application
// easily request for playing a sound
public class AudioHelper {

    // Player manager for sure manage the players! (Does not support discord sharding)
    AudioPlayerManager playerManager;

    // Create audio player (one player because not support sharding)
    AudioPlayer audioPlayer;

    // Track scheduler useful to create queue and manage player tracks.
    TrackScheduler trackScheduler;

    public AudioHelper() {

        // Initiate player manager
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(playerManager);

        audioPlayer = playerManager.createPlayer();
        trackScheduler = new TrackScheduler(audioPlayer);
        audioPlayer.addListener(trackScheduler);

    }

    // Each part of the app wants to play something call this method and done :)
    public void loadItem(String file, Member author, String name) {
        AudioManager guildAudioManager = author.getGuild().getAudioManager();
        if(!guildAudioManager.isConnected()) {
            guildAudioManager.openAudioConnection(author.getVoiceState().getChannel());
            guildAudioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
        }
        playerManager.loadItem(file, new MyAudioLoadResultHandler(trackScheduler, author, name));
    }

    // Control: next track
    public void nextTrack() {
        trackScheduler.nextTrack();
    }

    // Control: clear queue
    public void clear() {
        trackScheduler.clear();
    }

    // Control: pause player
    public void pause() {
        audioPlayer.setPaused(true);
    }

    // Control: resume player
    public void resume() {
        audioPlayer.setPaused(false);
    }

    // Control: set volume of player
    public void setVolume(int volume) {
        audioPlayer.setVolume(volume);
    }

    // Get player queue
    public String getQueue(){
        String res = "";
        for(TrackInfo info : trackScheduler.getQueue()) {
            res += info.getName() + "\n";
        }
        return res;
    }
}
