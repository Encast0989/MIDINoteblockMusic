package me.encast.midinoteblockmusic;

import lombok.Getter;
import me.encast.midinoteblockmusic.command.SongCommand;
import me.encast.midinoteblockmusic.command.StopSongCommand;
import me.encast.midinoteblockmusic.song.SongManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MIDINoteblockMusic extends JavaPlugin {

    @Getter
    private static MIDINoteblockMusic instance;

    private SongManager songManager;

    @Override
    public void onEnable() {
        instance = this;
        this.songManager = new SongManager();

        getCommand("song").setExecutor(new SongCommand());
        getCommand("stopsong").setExecutor(new StopSongCommand());
    }

    @Override
    public void onDisable() {
    }
}
