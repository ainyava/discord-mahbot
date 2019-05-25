package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;
import program.Main;


// This class is handler for when audio track loaded
// So we should specity what to do when a track loaded!
class MyAudioLoadResultHandler implements AudioLoadResultHandler {


    Member author;
    String name;
    TrackScheduler trackScheduler;

    public MyAudioLoadResultHandler(TrackScheduler trackScheduler, Member author, String name) {
        this.trackScheduler = trackScheduler;
        this.author = author;
        this.name = name;
    }

    // Add loaded track to queue
    @Override
    public void trackLoaded(AudioTrack track) {
        trackScheduler.add(author, name, track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

    }

    @Override
    public void noMatches() {
        System.out.println("[AudioPlayer]: Track not found !");
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        System.out.println("[AudioPlayer]: Failed to load track !");
    }
}