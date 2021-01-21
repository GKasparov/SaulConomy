package me.mazenz.saulconomy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SaulConomy extends JavaPlugin {

    private Database db;
    public static SaulConomy getInstance;
    public static EconomyImplementer economyImplementer;
    public static VaultHook vaultHook;

    public void onEnable(){
        getConfig().options().copyDefaults(true);
        saveConfig();
        this.db = new SQLite(this);
        this.db.load();
        getCommand("balance").setExecutor(new Balance(db));
        Bukkit.getPluginManager().registerEvents(new onjoin(db), this);

    }

    public Database getRDatabase() {
        return this.db;
    }
    public void instanceClasses() {
        getInstance = this;
        economyImplementer  = new EconomyImplementer();
        vaultHook = new VaultHook();
    }
}