package me.despical.qgloria.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.qgloria.enchantments.base.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CriticalDamage extends CustomEnchantment {

	public CriticalDamage() {
		super("critical_damage", "Kritik Vuruş İhtimali", 2);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onEntityDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Player damager && event.getEntity() instanceof Player)) return;

				var item = damager.getInventory().getItemInMainHand();

				if (item.getType() == Material.AIR) return;
				if (!item.containsEnchantment(CriticalDamage.this)) return;

				int level = item.getEnchantmentLevel(CriticalDamage.this);
				double criticalDamage = ThreadLocalRandom.current().nextDouble(level * 2);

				if (criticalDamage <= level) {
					event.setDamage(event.getDamage() + criticalDamage);
				}
			}
		};
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