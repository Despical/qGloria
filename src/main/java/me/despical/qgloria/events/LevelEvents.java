package me.despical.qgloria.events;

import me.despical.qgloria.Main;
import me.despical.qgloria.api.StatsStorage;
import me.despical.qgloria.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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

		final var user = userManager.getUser(player);
		final var killerUser = userManager.getUser(player.getKiller());

		levelManager.addXP(killerUser, user.getStat(StatsStorage.StatisticType.XP) * 3 / 10);
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;

		final var user = userManager.getUser(player);
		final var level = levelManager.getLevel(user).getLevel();
		final var damage = event.getDamage();

		switch (event.getCause()) {
			case LAVA -> event.setDamage(Math.max(damage - levelManager.getModifier("lava-defense", level), 0));
			case FIRE -> event.setDamage(Math.max(damage - levelManager.getModifier("fire-defense", level), 0));
			case FALL -> event.setDamage(Math.max(damage - levelManager.getModifier("fall-damage-defense", level), 0));
			case STARVATION -> event.setDamage(Math.max(damage - levelManager.getModifier("saturation", level), 0));
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player player && event.getDamager() instanceof Player damager)) return;

		var damagerLevel = userManager.getUser(damager).getLevel();
		var attackModifier = levelManager.getModifier("attack", damagerLevel);
		var defenseModifier = levelManager.getModifier("defense", userManager.getUser(player).getLevel());
		var damage = event.getDamage() + attackModifier - defenseModifier;

		if (Utils.isCriticalHit(damager)) damage += levelManager.getModifier("critical-hit", damagerLevel);

		event.setDamage(damage);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;

		final var user = userManager.getUser(player);
		final var level = levelManager.getLevel(user).getLevel();

		event.setFoodLevel(event.getFoodLevel() + (int) levelManager.getModifier("saturation", level));
	}
}