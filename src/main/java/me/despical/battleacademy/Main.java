package me.despical.battleacademy;

import lombok.Getter;
import me.despical.battleacademy.commands.Commands;
import me.despical.battleacademy.enchantments.base.EnchantmentManager;
import me.despical.battleacademy.events.EventListener;
import me.despical.battleacademy.handlers.ChatManager;
import me.despical.battleacademy.level.LevelManager;
import me.despical.battleacademy.user.User;
import me.despical.battleacademy.user.UserManager;
import me.despical.commandframework.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.stream.Stream;

@Getter
public class Main extends JavaPlugin {

	private ChatManager chatManager;
    private CommandFramework commandFramework;
	private LevelManager levelManager;
	private UserManager userManager;
	private EnchantmentManager enchantmentManager;

    @Override
    public void onEnable() {
		this.initializeClasses();
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

		User.cooldownHandlerTask();
		EventListener.registerEvents(this);
	}

	private void setupConfigurationFiles() {
		this.saveDefaultConfig();

		Stream.of("messages", "stats", "levels", "menu").filter(fileName -> !new File(getDataFolder(),fileName + ".yml").exists()).forEach(fileName -> this.saveResource(fileName + ".yml", false));
	}

	private void saveAllUserStatistics() {
		for (final var player : getServer().getOnlinePlayers()) {
			final var user = userManager.getUser(player);

			userManager.getUserDatabase().saveStatistics(user);
		}
	}
}