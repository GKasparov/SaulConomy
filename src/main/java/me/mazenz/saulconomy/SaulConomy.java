package me.mazenz.saulconomy;

import java.sql.*;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.UUID;

public class SaulConomy extends JavaPlugin {

    public static SaulConomy getInstance;
    public EconomyImplementer economyImplementer;
    private VaultHook vaultHook;
    public final HashMap<UUID, Double> playerBank = new HashMap<>();

    public void onEnable() {
        runOnEnable();
        getConfig().options().copyDefaults(true);
        saveConfig();
        try {
            sql.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(new joinevent(), this);
    }

    private void instanceClasses() {
        getInstance = this;
        economyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
    }

    public void runOnEnable() {
        instanceClasses();
        vaultHook.hook();
        this.getCommand("balance").setExecutor(new balance());
        this.getCommand("givemoney").setExecutor(new givemoney());
    }

    public void onDisable() {
        vaultHook.unhook();
        sql.disconnect();
    }

    public HashMap<UUID, Double> getBalanceMap() {
        return playerBank;
    }
}