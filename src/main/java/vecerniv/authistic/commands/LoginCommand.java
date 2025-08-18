package vecerniv.authistic.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vecerniv.authistic.Authistic;
import vecerniv.authistic.utils.CheckSender;
import vecerniv.authistic.utils.LoginManager;
import vecerniv.authistic.utils.PasswordUtils;

import java.time.Duration;
import java.util.UUID;

public class LoginCommand implements BasicCommand {
    private final Authistic plugin;
    private final LoginManager loginManager;

    public LoginCommand(Authistic plugin) {
        this.plugin = plugin;
        this.loginManager = plugin.getLoginManager();
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

        if (loginManager.isLoggedIn(player)) {
            player.sendRichMessage("<dark_red>[Authistic] You are already logged in!");
            return;
        }

        String storedHash = plugin.getPlayerConfig().getString("players." + name + ".password");
        assert storedHash != null;

        if (!PasswordUtils.checkPassword(args[0], uuidString, storedHash)) {
            player.sendRichMessage("<dark_red>[Authistic] Incorrect password!");
            loginManager.updateLoginAttempts(player);

            if (loginManager.getLoginAttempts(player) >= 3) {
                player.ban("Too many login attempts!", Duration.ofMinutes(30),
                        "Authistic", true);
                loginManager.resetLoginAttempts(player);
                return;
            }
            return;
        }

        player.sendRichMessage("<dark_green>[Authistic] Login successful!");
        loginManager.removeLoginPenalty(player);
        loginManager.resetLoginAttempts(player);
    }

    @Override
    public @Nullable String permission() {
        return "authistic.login";
    }
}
