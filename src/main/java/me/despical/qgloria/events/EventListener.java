package me.despical.qgloria.events;

import me.despical.qgloria.Main;
import me.despical.qgloria.handlers.ChatManager;
import me.despical.qgloria.level.LevelManager;
import me.despical.qgloria.user.UserManager;
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