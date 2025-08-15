package vecerniv.authistic.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vecerniv.authistic.Authistic;
import vecerniv.authistic.utils.CheckSender;
import vecerniv.authistic.utils.PasswordUtils;

import java.util.UUID;

public class LoginCommand implements BasicCommand {
    private final Authistic plugin;

    public LoginCommand(Authistic plugin) {
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

        if (!plugin.getPlayerConfig().contains("players." + uuidString)) {
            player.sendRichMessage("<dark_red>[Authistic] You are not registered!");
            player.sendRichMessage("<dark_gray>Use /register <password> to register first.");
            return;
        }

        String storedHash = plugin.getPlayerConfig().getString("players." + uuidString + ".password");
        //String inputHash = PasswordUtils.hashPassword(args[0], uuidString);

        if (!PasswordUtils.checkPassword(args[0], uuidString, storedHash)) {
            player.sendRichMessage("<dark_red>[Authistic] Incorrect password!");
            return;
        }

        player.sendRichMessage("<dark_green>[Authistic] Login successful!");
        plugin.getLoginManager().removeLoginPenalty(player);
    }
}
