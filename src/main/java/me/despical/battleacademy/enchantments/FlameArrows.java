package me.despical.battleacademy.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.battleacademy.enchantments.base.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FlameArrows extends CustomEnchantment {

	public FlameArrows() {
		super("flame_arrows", "FlameArrows", 1);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onDamage(EntityShootBowEvent event) {
				if (!(event.getEntity() instanceof Player player)) return; // Check if shooter is a player
				if (event.getBow().getItemMeta() == null) return; // Check if bow has an item meta
				if (!event.getBow().getItemMeta().hasEnchant(FlameArrows.this)) return; // Check if bow has our custom enchantment
				if (!plugin.getUserManager().getUser(player).isEnchantmentCompatible(FlameArrows.this)) return; // Check if user's element is compatible with enchantment
				if (player.getLocation().getNearbyEntitiesByType(Player.class, 8).size() > 2) { // Check if there are enough players nearby
					event.getProjectile().setFireTicks(20 * 10); // Fire the arrow
				}
			}
		};
	}

	@Override
	public int getElement() {
		return 1;
	}

	@Override
	public @NotNull EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.BOW;
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