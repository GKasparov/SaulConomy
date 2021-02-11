package me.mazenz.saulconomy;

import me.mazenz.saulconomy.commands.Balance;
import me.mazenz.saulconomy.commands.Pay;
import me.mazenz.saulconomy.commands.Saul;
import me.mazenz.saulconomy.commands.SetBalance;
import me.mazenz.saulconomy.vault.SaulVaultEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public class SaulConomy extends JavaPlugin {

    private Database db;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Logger logger = this.getLogger();
        new UpdateChecker(this, 88223).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("The latest version of SaulConomy is running (0.3)");
            } else {
                logger.info("There is a new version of SaulConomy. https://www.spigotmc.org/resources/saulconomy.88223/");
            }
        });

        this.db = new Database(this);
        this.db.load();

        // Vault integration
        SaulVaultEconomy economy = new SaulVaultEconomy(db);
        Bukkit.getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);

        // Commands
        getCommand("balance").setExecutor(new Balance(economy));
        getCommand("setbalance").setExecutor(new SetBalance(economy));
        getCommand("pay").setExecutor(new Pay(economy, this));
        getCommand("saul").setExecutor(new Saul(this));
    }

    @Override
    public void onDisable() {
        try {
            db.getConnection().close();
        } catch (SQLException exception) {
            db.report(exception);
        }
    }
}
