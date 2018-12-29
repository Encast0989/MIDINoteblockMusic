package me.encast.midinoteblockmusic.command;

import com.google.common.base.Joiner;
import me.encast.midinoteblockmusic.MIDINoteblockMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class SongCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length >= 1) {
                File data = MIDINoteblockMusic.getInstance().getDataFolder();
                if(!data.exists())
                    data.mkdir();

                File file = new File(data.getAbsoluteFile() +
                        File.separator + Joiner.on(" ").join(args) + ".mid");

                if(file.exists()) {
                    MIDINoteblockMusic.getInstance().getSongManager().play(p, file);
                } else {
                    p.sendMessage("§cFile does not exist!");
                }
            } else {
                p.sendMessage("§cPlease specify a file name (exclude .mid).");
            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }
        return true;
    }
}
