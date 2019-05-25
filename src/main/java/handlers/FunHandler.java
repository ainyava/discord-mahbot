package handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;

import java.util.Random;


// Fun text commands defined here
public class FunHandler {
    FunHandler(MessageReceivedEvent event) {

        // Create characters that are like penis
        // with random size that specifies mentiond user penis size!
        if (Helpers.command(event, "penis")) {
            Random r = new Random();
            String p = "8";
            for (int i = 1; i <= r.nextInt(20) + 1; i++) {
                p += '=';
            }
            p += 'D';
            String name = event.getAuthor().getName();
            if (event.getMessage().getMentionedMembers().size() > 0) {
                name = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
            }
            event.getChannel().sendMessage(String.format("%s's penis size\n%s", name, p)).queue();

        }
    }
}
