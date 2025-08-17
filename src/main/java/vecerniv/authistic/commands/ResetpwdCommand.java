package vecerniv.authistic.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vecerniv.authistic.Authistic;

import java.util.Arrays;
import java.util.Collection;

public class ResetpwdCommand implements BasicCommand {
    private final Authistic plugin;

    public ResetpwdCommand(Authistic plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
        CommandSender sender = source.getSender();
        if (args.length != 1) {
            sender.sendRichMessage("<dark_red>[Authistic] Usage: /resetpwd <player>]");
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        String name = player.getName();

        if (!plugin.getPlayerConfig().contains("players." + name)) {
            sender.sendRichMessage("<dark_red>[Authistic] " + name + " is not registered");
            sender.sendRichMessage("<dark_red>[Authistic] Please login using:");
            sender.sendRichMessage("<dark_gray>       /login <password>");

            return;
        }

        try {
            if (plugin.getPlayerConfig().contains("players." + name)) {
                plugin.deletePlayerConfig(player);
            }
        } catch (RuntimeException e) {
            plugin.getLogger().severe(e.getMessage());
        }
        source.getSender().sendRichMessage("<dark_red>[Authistic] " + name + "'s has been reset");
    }

    @Override
    public @Nullable String permission() {
        return "authistic.resetpwd";
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack source, String @NotNull [] args) {
        if (args.length == 0) {
            return Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList();
        }

        return Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getName)
                .filter(name -> name.toLowerCase().startsWith(args[args.length-1].toLowerCase()))
                .toList();
    }
}
