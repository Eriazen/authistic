package vecerniv.authistic.listeners;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import vecerniv.authistic.Authistic;

import java.util.ArrayList;
import java.util.List;


public class PlayerJoinListener implements Listener {
    private final Authistic plugin;

    public PlayerJoinListener(Authistic plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Debuff player on join
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getLoginManager().applyLoginPenalty(player);
            }
        }.runTaskLater(plugin, 1L);

        // Init and send join messages
        List<String> messages = new ArrayList<>();
        messages.add("<dark_red>[Authistic] Register using the command:");
        messages.add("<dark_gray>      /register <password>");
        messages.add("<dark_red>[Authistic] Login using the command:");
        messages.add("<dark_gray>      /login <password>");
        event.getPlayer().sendRichMessage(String.join("\n", messages));

        // Log into console
        plugin.getLogger().info("A message was sent to player" + player.getName());
    }
}