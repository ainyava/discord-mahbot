package audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;


// Custom class contains information of requsted tracks
public class TrackInfo {
    private final Member author;
    private final String name;
    private final AudioTrack track;

    public TrackInfo(Member author, String name, AudioTrack track) {
        this.author = author;
        this.name = name;
        this.track = track;
    }


    public Member getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public AudioTrack getTrack() {
        return track;
    }
}
