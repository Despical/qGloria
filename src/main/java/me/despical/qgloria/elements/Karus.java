package me.despical.qgloria.elements;

import me.despical.commons.miscellaneous.AttributeUtils;
import me.despical.qgloria.elements.base.Element;
import me.despical.qgloria.elements.base.Passive;
import me.despical.qgloria.user.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Karus extends Element {

	public Karus(User user) {
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
			public void onEntityDamage(EntityDamageByEntityEvent event) {
				if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) return;
				if (!isPassiveEnabled(fragile))	return;

				event.setDamage(event.getDamage() * (108D / 100D));
			}
		});

		var doubleJump = new Passive("double_jump", 25, false);
		doubleJump.setInitializer(player -> plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			if (player.getGameMode() != GameMode.SURVIVAL) return;

			player.setAllowFlight(!(user.getCooldown("double_jump") > 0));
		}, 20L, 20L));

		doubleJump.setListener(new Listener() {

			@EventHandler
			public void onDoubleJump(PlayerToggleFlightEvent event) {
				if (!isPassiveEnabled(doubleJump)) return;

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

		var swiftFeet = new Passive("swift_feet", 15, false);
		swiftFeet.setListener(new Listener() {

			@EventHandler
			public void onSneak(PlayerToggleSneakEvent event) {
				if (!isPassiveEnabled(swiftFeet)) return;
				var player = event.getPlayer();

				if (player.getFallDistance() < 1) return;

				if (!player.hasPotionEffect(PotionEffectType.SLOW_FALLING))
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 5 * 20, 0, false, false, false));
			}
		});

		addPassive(airSharpness, fragile, doubleJump, swiftFeet);
	}
}