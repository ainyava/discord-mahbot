package commands;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;

import java.io.File;
import java.io.IOException;

// Get track command is for get PHOTO of ARTIST for a SONG
// For example if you have music bots in your channnel and want to post photo of artist of requested song
// Then call this command when required and this will search for you with An ExternalCommand
public class GetTrackCommand {
    public GetTrackCommand(MessageReceivedEvent event, String query) {
        String qq = "\""+query+"\"";
        String avatar = String.format("commands/assets/%s.jpg", event.getAuthor().getId());
        Helpers.downloadFile(event.getAuthor().getAvatarUrl(), avatar);
        new ExternalCommand(event,"GetTrack", qq);
        String file = String.format("commands/assets/%s_track.jpg", event.getAuthor().getId());
        String str = String.format(":musical_note: %s plays `%s`", event.getMember().getEffectiveName(), query);
        event.getChannel().sendMessage(str).addFile(new File(file)).complete();
        if(!new File(file).delete()) {
            System.out.println("DeleteError: track.jpg");
        }
    }
}
