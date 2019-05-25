package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import program.Helpers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Usage of this command is for when you have a GP, Channel in telegram
// SO you sent them a photo of game and players online
// They know you want to play and join you :)
// EDIT PYTHON SCRIPT FOR TELEGRAM PART
public class CallGameCommand {
    public CallGameCommand(MessageReceivedEvent event, String game) {

        Map<String, String> games = new HashMap<String, String>() {{
            put("cs", "Counter Strike");
            put("dota", "Dota2");
        }};

        String avatar = String.format("commands/assets/%s.jpg", event.getAuthor().getId());
        Helpers.downloadFile(event.getAuthor().getAvatarUrl(), avatar);
        String str = event.getMember().getEffectiveName() + " " + game + " ";
        List<Member> mentions = event.getMessage().getMentionedMembers();
        if (mentions.size() > 0) {
            for (Member member : mentions) {
                avatar = String.format("commands/assets/%s.jpg", member.getId());
                Helpers.downloadFile(member.getUser().getAvatarUrl(), avatar);
                str += member.getId() + " " + member.getEffectiveName() + " ";
            }
        }
        new ExternalCommand(event, "CallForGame", str);
        String file = String.format("commands/assets/%s_GameSend.jpg", event.getAuthor().getId());
        game = games.getOrDefault(game, game);
        String msg = String.format("@here, %s need you to play :video_game: **%s** ", event.getMember().getEffectiveName(), game);
        event.getChannel().sendMessage(msg).addFile(new File(file)).complete();
        if (!new File(file).delete()) {
            System.out.println("DeleteError: GameSend.jpg");
        }
    }
}
