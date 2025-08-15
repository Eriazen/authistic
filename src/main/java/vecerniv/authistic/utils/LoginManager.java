package vecerniv.authistic.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import vecerniv.authistic.Authistic;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private final Set<UUID> penalizedPlayers = new HashSet<>();
    private final Set<UUID> loggedInPlayers = new HashSet<>();
    private final Authistic plugin;

    public  LoginManager(Authistic plugin) {
        this.plugin = plugin;
    }

    public void applyLoginPenalty(@NotNull Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10, false, false));
        player.setWalkSpeed(0f);
        player.setInvulnerable(true);
        player.setGameMode(GameMode.ADVENTURE);

        penalizedPlayers.add(player.getUniqueId());
        plugin.getLogger().info("Added player to penalized list.");
    }

    public boolean isPenalized(@NotNull Player player) {
        return penalizedPlayers.contains(player.getUniqueId());
    }

    public boolean isLoggedIn(@NotNull Player player) {
        return loggedInPlayers.contains(player.getUniqueId());
    }

    public void removeLoginPenalty(Player player) {
        if (!isPenalized(player)) {
            return;
        }

        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.setWalkSpeed(0.2f);
        player.setInvulnerable(false);
        player.setGameMode(GameMode.SURVIVAL);

        penalizedPlayers.remove(player.getUniqueId());
        loggedInPlayers.add(player.getUniqueId());
        plugin.getLogger().info("Removed player from penalized list and added to logged in list.");
    }

    public void removeLoggedInPlayer(@NotNull Player player) {
        loggedInPlayers.remove(player.getUniqueId());
        plugin.getLogger().info("Removed player from logged in list.");
    }
}
