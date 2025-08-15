package vecerniv.authistic;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import vecerniv.authistic.commands.LoginCommand;
import vecerniv.authistic.commands.RegisterCommand;
import vecerniv.authistic.listeners.LockdownListener;
import vecerniv.authistic.listeners.PlayerJoinListener;
import vecerniv.authistic.listeners.PlayerQuitListener;
import vecerniv.authistic.utils.LoginManager;

import java.io.File;
import java.io.IOException;



public final class Authistic extends JavaPlugin {
    private File playerFile;
    private FileConfiguration playerConfig;
    LoginManager loginManager = new LoginManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin has been enabled!");



        registerCommand("register", new RegisterCommand(this));
        registerCommand("login", new LoginCommand(this));

        createPlayerFile();

        new PlayerJoinListener(this);
        getLogger().info("Join listener is running!");
        new PlayerQuitListener(this);
        getLogger().info("Quit listener is running!");
        new LockdownListener(this);
        getLogger().info("Lockdown listener is running!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin has been disabled!");
    }

    public FileConfiguration getPlayerConfig() {
        return playerConfig;
    }

    public void savePlayerConfig() {
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            getLogger().severe(e.getMessage());
        }
    }

    private void createPlayerFile() {
        playerFile = new File(getDataFolder(), "players.yml");
        if (!playerFile.exists()) {
            playerFile.getParentFile().mkdirs();
            saveResource("players.yml", false);
        }
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }
}
