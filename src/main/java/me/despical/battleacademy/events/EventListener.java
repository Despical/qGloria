package me.despical.battleacademy.events;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.events.level.LevelEvents;
import me.despical.battleacademy.handlers.ChatManager;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class EventListener implements Listener {

	@NotNull
	protected final Main plugin;

	@NotNull
	protected final ChatManager chatManager;

	public EventListener(@NotNull Main plugin) {
		this.plugin = plugin;
		this.chatManager = plugin.getChatManager();
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static void registerEvents(final Main plugin) {
		new JoinQuitEvents(plugin);
		new LevelEvents(plugin);
	}
}