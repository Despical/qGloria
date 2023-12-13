package me.despical.qgloria;

import lombok.Getter;
import me.despical.commandframework.CommandFramework;
import me.despical.commons.item.ItemBuilder;
import me.despical.qgloria.commands.Commands;
import me.despical.qgloria.enchantments.base.EnchantmentManager;
import me.despical.qgloria.events.EventListener;
import me.despical.qgloria.handlers.ChatManager;
import me.despical.qgloria.level.LevelManager;
import me.despical.qgloria.user.User;
import me.despical.qgloria.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
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

		User.cooldownHandlerTask();
		EventListener.registerEvents(this);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().getType() != InventoryType.ANVIL) return;

		final var anvil = (AnvilInventory) event.getInventory();

		if (event.getRawSlot() == 1) {
			var item = anvil.getFirstItem();
			System.out.println(1);
			if (item == null) return;

			var secondItem = event.getCurrentItem();

			System.out.println(secondItem.getType().name());

			var result = new ItemBuilder(secondItem).build();
			anvil.setItem(2, result);

			System.out.println("sadsasass");
		}
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