package handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import program.Config;
import program.Helpers;
import program.Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


// Commands that give you status and informations
public class StatsHandler {
    StatsHandler(MessageReceivedEvent event) {

        // Get ping of bot
        if (Helpers.command(event, "ping")) {
            event.getChannel().sendMessage(String.format("%s Pong! (%dms)", event.getMessage().getAuthor().getAsMention(), Main.jda.getGatewayPing())).queue();
        }

        // Get id of guild
        if (Helpers.command(event, "id")) {
            System.out.println(event.getGuild().getId());
            event.getChannel().sendMessage(event.getGuild().getId()).queue();
        }

        // This command still does not have all commands of bot so need to develop
        // But in eaither way a web document is better
        // So change it to just send a link of HELP webpage :)
        if (Helpers.command(event, "help") || Helpers.command(event, "h")) {
            String p = Config.prefix;
            String help = "```";
            // Get list of commands
            try {
                Object obj = new JSONParser().parse(new FileReader("resources/map/EffectsMap.json"));
                JSONArray jsonArray = new JSONArray(obj.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    help += p + jo.get("shortcut") + " ";
                }
                help += "```";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage(":question: **Available commands are :**\n" +
                    "`ping` display ping of bot to server.\n" +
                    "`leave` Make bot leave channel.\n" +
                    "**_Music Commands Are :_**\n" +
                    help).queue();
        }
    }
}
