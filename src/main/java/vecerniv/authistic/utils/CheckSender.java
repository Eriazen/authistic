package vecerniv.authistic.utils;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CheckSender {
    public static Player checkSender(CommandSourceStack source, String [] args) {
        Object sender = source.getSender();

        if (!(sender instanceof Player player)) {
            if (sender instanceof ConsoleCommandSender cs) {
                cs.sendRichMessage("<red>Only players can run this command!");
            }
            return null;
        }

        if (args.length != 1) {
            player.sendRichMessage("<red>Invalid arguments!");
            return null;
        }

        return player;
    }
}
