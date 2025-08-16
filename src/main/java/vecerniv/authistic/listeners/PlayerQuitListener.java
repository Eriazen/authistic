package vecerniv.authistic.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import vecerniv.authistic.Authistic;

public class PlayerQuitListener implements Listener {
    private final Authistic plugin;

    public PlayerQuitListener(Authistic plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getLoginManager().removeLoggedInPlayer(player);
        plugin.getLogger().info(player.getName() + " removed from logged in list.");
    }
}