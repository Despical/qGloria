package me.despical.battleacademy.events;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.handlers.ChatManager;
import me.despical.battleacademy.level.LevelManager;
import me.despical.battleacademy.user.UserManager;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class EventListener implements Listener {

	protected final Main plugin;
	protected final ChatManager chatManager;
	protected final UserManager userManager;
	protected final LevelManager levelManager;

	public EventListener(@NotNull Main plugin) {
		this.plugin = plugin;
		this.chatManager = plugin.getChatManager();
		this.userManager = plugin.getUserManager();
		this.levelManager = plugin.getLevelManager();
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static void registerEvents(final Main plugin) {
		new JoinQuitEvents(plugin);
		new LevelEvents(plugin);
	}
}