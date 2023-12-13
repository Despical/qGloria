package me.despical.qgloria.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.qgloria.enchantments.base.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Blocking extends CustomEnchantment {

	public Blocking() {
		super("blocking", "Hasar Bloklama", 1);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onEntityDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Player damager && event.getEntity() instanceof Player)) return;

				var armors = damager.getInventory().getArmorContents();

				if (armors.length == 0) return;

				boolean hasEnchant = false;

				for (var armor : armors) {
					if (!armor.containsEnchantment(Blocking.this)) continue;
					hasEnchant = true;
				}

				if (!hasEnchant) return;

				if (ThreadLocalRandom.current().nextInt(100) <= 5)
					event.setDamage(0);
			}
		};
	}

	@Override
	public @NotNull EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR;
	}

	@Override
	public @NotNull EnchantmentRarity getRarity() {
		return EnchantmentRarity.VERY_RARE;
	}

	@Override
	public @NotNull Set<EquipmentSlot> getActiveSlots() {
		return Set.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
	}
}