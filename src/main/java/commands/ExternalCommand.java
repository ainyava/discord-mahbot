package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// External coommands are commands that written with another programming language
// You can create your own external command that extends this class
// Witch executes a program or an script with the requested parameters
// COMMANDS SHOULD INTRODUCE TO BOT IN CommandsMap.json FILE IN "RESOURCES/MAP"
public class ExternalCommand {
    public ExternalCommand(MessageReceivedEvent event, String command, String params) {
        JSONObject obj = null;
        try {
            Object orgObj = new JSONParser().parse(new FileReader("resources/map/CommandsMap.json"));
            obj = new JSONObject(orgObj.toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        assert obj != null;
        String cmd = String.format("%s %s %s", obj.getString(command), event.getAuthor().getId(), params);
        System.out.println("ExternalCommand: " + cmd);
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("ExternalCommandError:"+command);
        }
    }
}
