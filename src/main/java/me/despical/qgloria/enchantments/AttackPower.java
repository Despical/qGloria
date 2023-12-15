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
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class AttackPower extends CustomEnchantment {

	public AttackPower() {
		super("critical_damage", "Kritik Vurus Ihtimali", 2);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onEntityDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Player damager && event.getEntity() instanceof Player)) {
					return;
				}

				var item = damager.getInventory().getItemInMainHand();

				if (item.getType() == Material.AIR) {
					return;
				}

				if (!item.getEnchantments().containsKey(AttackPower.this)) {
					return;
				}

				int level = item.getEnchantments().get(AttackPower.this);
				double additionalDamage = ThreadLocalRandom.current().nextDouble(level * 2 + 1);

				event.setDamage(event.getDamage() + additionalDamage / 9D);
			}
		};
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