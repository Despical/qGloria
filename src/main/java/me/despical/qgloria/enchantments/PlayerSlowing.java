package me.despical.qgloria.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.qgloria.enchantments.base.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
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

public class PlayerSlowing extends CustomEnchantment {

	public PlayerSlowing() {
		super("player_slowing", "Oyuncu Yavaşlatma", 2);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onEntityDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Player damager && event.getEntity() instanceof LivingEntity entity)) return;

				var item = damager.getInventory().getItemInMainHand();

				if (item.getType() == Material.AIR) return;
				if (!item.getEnchantments().containsKey(PlayerSlowing.this)) return;

				int level = item.getEnchantments().get(PlayerSlowing.this);
				int slowness = ThreadLocalRandom.current().nextInt(level * (3 + level)) + 1;
				int chance = ThreadLocalRandom.current().nextInt(100 / level);

				if (chance <= 55) {
					if (!entity.hasPotionEffect(PotionEffectType.SLOW))
						entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slowness * 20, level - 1, false, false, false));
				}
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
		return Set.of(EquipmentSlot.FEET);
	}
}