package me.encast.midinoteblockmusic.command;

import me.encast.midinoteblockmusic.MIDINoteblockMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopSongCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage("§aAttempting to stop currently playing music. If the server was recently reloaded, " +
                    "this task can not be fulfilled.");
            boolean stopped = MIDINoteblockMusic.getInstance().getSongManager().stop(p);
            p.sendMessage("§6" + (stopped ? "Music has been stopped!" : "Music could not be stopped!"));
        } else {
            sender.sendMessage("Only players can use this command.");
        }
        return true;
    }
}
