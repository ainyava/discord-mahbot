package handlers;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;
import java.util.concurrent.TimeUnit;

// This handler just check if there is a text channel called bots and a real user tries to send message in that channel
// just delete the sent message and inform user.
public class BotsMessageHandler {
    public BotsMessageHandler(final MessageReceivedEvent event) {

        if(event.getMessage().getChannel().getName().equals("bots")) {
            if(!event.getAuthor().isBot() && !Helpers.isSelf(event) && !Helpers.isForMusicBot(event)){
                event.getMessage().delete().complete();
                event.getMessage().getChannel().sendMessage(event.getAuthor().getAsMention() + ", this channel is not for chat :)").complete().delete().completeAfter(5, TimeUnit.SECONDS);
            }
        }

    }
}
