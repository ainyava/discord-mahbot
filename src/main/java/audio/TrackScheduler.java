package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import program.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


// Track manager
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private Queue<TrackInfo> queue;

    TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedList<>();
    }

    // Clear queue
    void clear() {
        player.stopTrack();
        this.queue.clear();
    }

    // Add track to queue
    void add(Member author, String name, AudioTrack track) {
        queue.add(new TrackInfo(author, name, track));
        if(player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
        System.out.println("MUSIC: Added to queue!");
    }

    // Skip track
    void nextTrack() {
        player.stopTrack();
        try {
            TrackInfo info = queue.remove();
            if (queue.isEmpty()) {
                info.getAuthor().getGuild().getAudioManager().closeAudioConnection();
            } else {
                player.playTrack(queue.element().getTrack());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        player.setPaused(true);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        player.setPaused(false);
    }

    // Play next track if available
    // Else close the connection to channel
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        System.out.println("MUSIC: trackEnd fired!");
        try {
            TrackInfo info = queue.remove();
            if (queue.isEmpty()) {
                info.getAuthor().getGuild().getAudioManager().closeAudioConnection();
            } else {
                player.playTrack(queue.element().getTrack());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MUSIC: trackEnd finished!");
    }

    public ArrayList<TrackInfo> getQueue(){
        return new ArrayList<TrackInfo>(queue);
    }
}
