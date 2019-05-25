package handlers;

import commands.CallGameCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;


// Any commands related to games are defined here
public class GamesHandler {
    GamesHandler(MessageReceivedEvent event) {

        // Call for game (in Telegram) (ExternalCommand)
        if(Helpers.command(event, "dota") || Helpers.command(event, "cs")) {
            new CallGameCommand(event, Helpers.getCommand(event));
        }
    }
}
