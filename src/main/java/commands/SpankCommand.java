package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;

import java.io.File;
import java.io.IOException;
import java.util.List;

// An external command that used for create a photo for spank somebody in channel!
// (Edits a photo and put profile photo of two users in channel)
public class SpankCommand {
    public SpankCommand(MessageReceivedEvent event) {
        List<Member> mentions = event.getMessage().getMentionedMembers();
        User u1,u2;
        if (mentions.size() == 1) {
            u1 = event.getAuthor();
            u2 = mentions.get(0).getUser();
        } else {
            u1 = mentions.get(0).getUser();
            u2 = mentions.get(1).getUser();
        }
        Helpers.downloadFile(u1.getAvatarUrl(), String.format("commands/assets/%s.jpg", u1.getId()));
        Helpers.downloadFile(u2.getAvatarUrl(), String.format("commands/assets/%s.jpg", u2.getId()));
        new ExternalCommand(event, "Spank", u1.getId()+" " + u2.getId());
        String file = String.format("commands/assets/%s_spank.jpg", event.getAuthor().getId());
        event.getChannel().sendFile(new File(file)).queue();
        if(new File(file).delete()) {
            System.out.println("DeleteError: " + file);
        }
    }
}
