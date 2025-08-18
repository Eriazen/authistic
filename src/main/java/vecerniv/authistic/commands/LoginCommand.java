package vecerniv.authistic.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
        String name =  player.getName();

        if (!plugin.getPlayerConfig().contains("players." + name)) {
            player.sendRichMessage("<dark_red>[Authistic] You are not registered!");
            player.sendRichMessage("<dark_gray>Use /register <password> to register first.");
            return;
        }

        if (plugin.getLoginManager().isLoggedIn(player)) {
            player.sendRichMessage("<dark_red>[Authistic] You are already logged in!");
            return;
        }

        String storedHash = plugin.getPlayerConfig().getString("players." + name + ".password");

        assert storedHash != null;
        if (!PasswordUtils.checkPassword(args[0], uuidString, storedHash)) {
            player.sendRichMessage("<dark_red>[Authistic] Incorrect password!");
            return;
        }

        player.sendRichMessage("<dark_green>[Authistic] Login successful!");
        plugin.getLoginManager().removeLoginPenalty(player);
    }

    @Override
    public @Nullable String permission() {
        return "authistic.login";
    }
}
