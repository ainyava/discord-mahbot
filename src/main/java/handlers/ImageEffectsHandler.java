package handlers;

import commands.SpankCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;


// Image manipulation commands are defined here
public class ImageEffectsHandler {
    ImageEffectsHandler(MessageReceivedEvent event) {

        // Create a photo of spank somebody!
        if (Helpers.command(event, "spank")) {
            new SpankCommand(event);
        }
    }
}
