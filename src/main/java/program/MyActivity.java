package program;

import javafx.embed.swing.SwingFXUtils;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ActivityFlag;
import net.dv8tion.jda.api.entities.RichPresence;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

public class MyActivity implements Activity {
    @Override
    public boolean isRich() {
        return false;
    }

    @Override
    public RichPresence asRichPresence() {
        return null;
    }

    @Override
    public String getName() {
        return ".help";
    }

    @Override
    public String getUrl() {
        return "http://MahCodes.ir";
    }

    @Override
    public ActivityType getType() {
        return ActivityType.LISTENING;
    }

    @Nullable
    @Override
    public Timestamps getTimestamps() {
        return null;
    }
}
