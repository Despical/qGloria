package me.despical.battleacademy.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.battleacademy.enchantments.base.CustomEnchantment;
import org.bukkit.Particle;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FlameCircle extends CustomEnchantment {

	public FlameCircle() {
		super("flame_circle", "Flame Circle", 1);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onRightClick(PlayerInteractEvent event) {
				if (!event.getAction().isRightClick()) return;
				if (event.getItem() == null) return;
				if (!event.getItem().getItemMeta().hasEnchant(FlameCircle.this)) return;

				final var player = event.getPlayer();
				final var user = plugin.getUserManager().getUser(event.getPlayer());

				if (!user.isEnchantmentCompatible(FlameCircle.this)) return;
				if (user.getCooldown("flame_circle") > 0) {
					user.sendMessage("element-messages.cooldown-message", user.getCooldown("flame_circle"));
					return;
				}

				user.setCooldown("flame_circle", 10);

				final var location = event.getPlayer().getLocation().clone();
				final var radius = 8.5;

				new BukkitRunnable() {

					int ticks = 0;

					@Override
					public void run() {
						if (++ticks == 140) cancel();

						for (int d = 0; d <= 90; d += 1) {
							var particleLoc = location.clone();
							particleLoc.setX(location.getX() + Math.cos(d) * radius);
							particleLoc.setZ(location.getZ() + Math.sin(d) * radius);

							location.getWorld().spawnParticle(Particle.FLAME, particleLoc, 0);
						}

						for (final var entity : location.getNearbyLivingEntities(radius)) {
							if (entity.equals(player)) continue;

							entity.damage(2.5D);
						}
					}
				}.runTaskTimer(plugin, 1, 1);
			}
		};
	}

	@Override
	public int getElement() {
		return 1;
	}

	@Override
	public @NotNull EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.WEAPON;
	}

	@Override
	public @NotNull EnchantmentRarity getRarity() {
		return EnchantmentRarity.VERY_RARE;
	}

	@Override
	public @NotNull Set<EquipmentSlot> getActiveSlots() {
		return Set.of(EquipmentSlot.HAND);
	}
}