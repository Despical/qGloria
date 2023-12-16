package me.despical.qgloria.events;

import me.despical.qgloria.Main;
import me.despical.qgloria.api.StatsStorage;
import me.despical.qgloria.menus.SelectMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvents extends EventListener {

	public JoinQuitEvents(Main plugin) {
		super(plugin);
	}

	@EventHandler
	public void onJoinEvent(final PlayerJoinEvent event) {
		final var player = event.getPlayer();
		final var user = plugin.getUserManager().getUser(player);

		plugin.getUserManager().loadStatistics(user);

		if (user.getStat(StatsStorage.StatisticType.ELEMENT) == 0) {
			new SelectMenu(plugin, player);
		}
	}

	@EventHandler
	public void onQuitEvent(final PlayerQuitEvent event) {
		this.handleQuitEvent(event.getPlayer());
	}

	@EventHandler
	public void onKickEvent(final PlayerKickEvent event) {
		this.handleQuitEvent(event.getPlayer());
	}

	private void handleQuitEvent(final Player player) {
		final var user = plugin.getUserManager().getUser(player);
		plugin.getUserManager().removeUser(user);
	}
}