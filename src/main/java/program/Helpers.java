package program;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class Helpers {

    public static String getMsg(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().toLowerCase();
    }

    public static boolean isSelf(MessageReceivedEvent event) {
        return event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId());
    }

    public static boolean isForMusicBot(MessageReceivedEvent event) {
        boolean is = false;
        for (String prefix : Config.musicBotsPrefixes) {
            if (getMsg(event).substring(0, prefix.length()).equals(prefix)) {
                is = true;
                break;
            }
        }
        return is;
    }

    public static String getMusicBotPrefix(MessageReceivedEvent event) {
        for(String prefix: Config.musicBotsPrefixes) {
            if (getMsg(event).substring(0, prefix.length()).equals(prefix)) {
                return prefix;
            }
        }
        return "";
    }

    public static String[] getMessageParts(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split(" ");
    }

    public static String getCommand(MessageReceivedEvent event){
        return getMessageParts(event)[0].toLowerCase();
    }

    public static String getParams(MessageReceivedEvent event) {
        String[] parts = getMessageParts(event);
        return String.join(" ", Arrays.asList(parts).subList(1, parts.length));
    }

    public static boolean command(MessageReceivedEvent event, String cmd) {
        return getCommand(event).toLowerCase().equals(cmd) || (getCommand(event).toLowerCase().substring(0, Config.prefix.length()).equals(Config.prefix) && getCommand(event).toLowerCase().substring(Config.prefix.length()).equals(cmd));
    }

    public static void downloadFile(String fileUrl, String fileOutput) {
        try{
            URL url = new URL(fileUrl);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream(fileOutput);
            fos.write(response);
            fos.close();
        } catch (MalformedURLException e) {
            System.out.print("DownloadError: Url broken!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("DownloadError: IO Exception!");
            e.printStackTrace();
        }
    }
}
