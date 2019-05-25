package handlers;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;

import java.util.concurrent.TimeUnit;

import static program.Main.audioHelper;


// Commands related to media player control
// Commands are so obvious so does not need any extra explanation!
public class MediaPlayerHandler {
    MediaPlayerHandler(MessageReceivedEvent event) {
        if (Helpers.command(event, "leave")) {
            event.getMessage().delete().queue();
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getChannel().sendMessage("**Okay Bye :|**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "join")) {
            event.getMessage().delete().queue();
            event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
            event.getChannel().sendMessage("**Hey, Whats Up ?**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "queue")) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("Up comming : \n" + audioHelper.getQueue()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "skipall")) {
            event.getMessage().delete().queue();
            audioHelper.clear();
            event.getChannel().sendMessage("**All tracks in queue skipped!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "s") || Helpers.command(event, "skip")) {
            event.getMessage().delete().queue();
            audioHelper.nextTrack();
            event.getChannel().sendMessage("**Skip to next track!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "pause")) {
            event.getMessage().delete().queue();
            audioHelper.pause();
            event.getChannel().sendMessage("**Someone told me to shut up!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "resume")) {
            event.getMessage().delete().queue();
            audioHelper.resume();
            event.getChannel().sendMessage("**I got my voice back!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }

        if (Helpers.command(event, "volume")) {
            event.getMessage().delete().queue();
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                int vol = Integer.parseInt(Helpers.getMessageParts(event)[1]);
                audioHelper.setVolume(vol);
                if(vol < 25) {
                    event.getChannel().sendMessage("**Okay, I'm whispering now!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                } else if(vol < 50) {
                    event.getChannel().sendMessage("**Okay, I'll make less noice!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                } else if(vol < 75) {
                    event.getChannel().sendMessage("**Okay, I talk to you normally!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                } else if(vol <= 100) {
                    event.getChannel().sendMessage("**Okay, I can sing now!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                } else {
                    event.getChannel().sendMessage("**Okay, I'll scream!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                }

            } else {
                event.getChannel().sendMessage("**Sorry " + event.getAuthor().getAsMention() + ", You do not have permission to do this!**").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        }

    }
}
