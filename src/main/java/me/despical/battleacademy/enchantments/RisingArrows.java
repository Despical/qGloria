package me.despical.battleacademy.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.battleacademy.enchantments.base.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RisingArrows extends CustomEnchantment {

	public RisingArrows() {
		super("rising_arrows", "RisingArrows", 1);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player shooter && event.getEntity() instanceof Player player)) return;
				if (arrow.getItemStack().getItemMeta() == null) return;
				if (!arrow.getItemStack().getItemMeta().hasEnchant(RisingArrows.this)) return;
				if (!plugin.getUserManager().getUser(shooter).isEnchantmentCompatible(RisingArrows.this)) return;

				player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 0, false, false, false));
			}
		};
	}

	@Override
	public int getElement() {
		return 4;
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

