package me.despical.battleacademy.elements;

import me.despical.battleacademy.elements.base.Element;
import me.despical.battleacademy.elements.base.Passive;
import me.despical.battleacademy.user.User;
import me.despical.commons.miscellaneous.AttributeUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class AirElement extends Element {

	public AirElement(User user) {
		super(user, 4);
		this.fallDamage = -40;
		this.fireDefence = -15;
		this.defense = -10;
		this.speed = 7;
		this.attackSpeed = -15;
	}

	@Override
	public void registerPassives() {
		var airSharpness = new Passive("air_sharpness", 20, false);
		airSharpness.setInitializer(player -> AttributeUtils.setAttackCooldown(player, 4D * (((100D + (double) attackSpeed) / 100D))));

		var fragile = new Passive("fragile", 1, false);
		fragile.setListener(new Listener() {

			@EventHandler
			public void onEntityDamage(EntityDamageByEntityEvent e) {
				if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) return;
				if (!isPassiveEnabled(fragile))	return;

				e.setDamage(e.getDamage() * (108D / 100D));
			}
		});

		var doubleJump = new Passive("double_jump", 1, false);
		doubleJump.setInitializer(player -> {
			plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
				if (player.getGameMode() != GameMode.SURVIVAL) return;

                player.setAllowFlight(!(user.getCooldown("double_jump") > 0));
			}, 20L, 20L);
		});

		doubleJump.setListener(new Listener() {

			@EventHandler
			public void onDoubleJump(PlayerToggleFlightEvent event) {
				final var player = event.getPlayer();

				if (!event.isFlying() || player.getGameMode() != GameMode.SURVIVAL) return;

				final var user = plugin.getUserManager().getUser(player);

				if (user.getCooldown("double_jump") > 0) return;

				event.setCancelled(true);

				user.setCooldown("double_jump", 2);

				player.setFlying(false);
				player.setVelocity(player.getLocation().getDirection().multiply(1.5D).setY(0.7D));
			}
		});

		addPassive(airSharpness, fragile, doubleJump);
	}
}