package me.despical.qgloria;

import lombok.Getter;
import me.despical.commandframework.CommandFramework;
import me.despical.qgloria.commands.Commands;
import me.despical.qgloria.enchantments.base.EnchantmentManager;
import me.despical.qgloria.events.EventListener;
import me.despical.qgloria.handlers.ChatManager;
import me.despical.qgloria.handlers.PlaceholderManager;
import me.despical.qgloria.level.LevelManager;
import me.despical.qgloria.user.User;
import me.despical.qgloria.user.UserManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.stream.Stream;

@Getter
public class Main extends JavaPlugin implements Listener {

	private ChatManager chatManager;
    private CommandFramework commandFramework;
	private LevelManager levelManager;
	private UserManager userManager;
	private EnchantmentManager enchantmentManager;

    @Override
    public void onEnable() {
		this.initializeClasses();
    	getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		saveAllUserStatistics();
	}

	private void initializeClasses() {
		this.setupConfigurationFiles();

		this.chatManager = new ChatManager(this);
		this.commandFramework = new CommandFramework(this);
		this.levelManager = new LevelManager(this);
		this.userManager = new UserManager(this);
		this.enchantmentManager = new EnchantmentManager();

		new Commands(this);

		if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI"))
			new PlaceholderManager(this);

		User.cooldownHandlerTask();
		EventListener.registerEvents(this);
	}

	private void setupConfigurationFiles() {
		Stream.of("messages", "stats", "levels", "menu").filter(fileName -> !new File(getDataFolder(),fileName + ".yml").exists()).forEach(fileName -> this.saveResource(fileName + ".yml", false));
	}

	private void saveAllUserStatistics() {
		for (final var player : getServer().getOnlinePlayers()) {
			final var user = userManager.getUser(player);

			userManager.getUserDatabase().saveStatistics(user);
		}
	}
}