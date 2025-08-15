package vecerniv.authistic.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vecerniv.authistic.Authistic;
import vecerniv.authistic.utils.CheckSender;
import vecerniv.authistic.utils.PasswordUtils;

import java.util.UUID;

public class RegisterCommand implements BasicCommand {
    private final Authistic plugin;

    public RegisterCommand(Authistic plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
        Player player = CheckSender.checkSender(source, args);
        if (player == null) {
            return;
        }

        UUID uuid = player.getUniqueId();
        String uuidString = uuid.toString();

        if (plugin.getPlayerConfig().contains("players." + uuidString)) {
            player.sendRichMessage("<dark_red>[Authistic] You are already registered!");
            player.sendRichMessage("<dark_red>[Authistic] Please login using:");
            player.sendRichMessage("<dark_gray>       /login <password>");
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(args[0], uuidString);

        plugin.getPlayerConfig().set("players." + uuidString + ".password", hashedPassword);
        plugin.savePlayerConfig();

        player.sendRichMessage("<dark_green>[Authistic] You are now registered!");
        plugin.getLoginManager().removeLoginPenalty(player);
    }
}
