package me.despical.battleacademy.events.level;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.api.StatsStorage;
import me.despical.battleacademy.events.EventListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class LevelEvents extends EventListener {

	public LevelEvents(@NotNull Main plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final var player = event.getPlayer();

		if (player.getKiller() == null) return;

		final var user = plugin.getUserManager().getUser(player);
		final var killerUser = plugin.getUserManager().getUser(player.getKiller());

		plugin.getLevelManager().addXP(killerUser, user.getStat(StatsStorage.StatisticType.XP) * 3 / 10);
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;

		final var user = plugin.getUserManager().getUser(player);
		final var levelManager = plugin.getLevelManager();
		final var level = levelManager.getLevel(user).getLevel();
		final var damage = event.getDamage();

		switch (event.getCause()) {
			case LAVA -> event.setDamage(Math.max(damage - levelManager.getModifier("lava-defense", level), 0));
			case FIRE -> event.setDamage(Math.max(damage - levelManager.getModifier("fire-defense", level), 0));
		}
	}
}