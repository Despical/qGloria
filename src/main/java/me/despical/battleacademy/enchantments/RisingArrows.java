package me.despical.battleacademy.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.battleacademy.enchantments.base.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
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
			public void onShootBow(EntityShootBowEvent event) {
				if (!(event.getEntity() instanceof Player shooter)) return;
				if (event.getBow().getItemMeta() == null) return;
				if (!event.getBow().getItemMeta().hasEnchant(RisingArrows.this)) return;
				if (!plugin.getUserManager().getUser(shooter).isEnchantmentCompatible(RisingArrows.this)) return;

				event.getProjectile().setCustomName("rising_arrow");
			}

			@EventHandler
			public void onDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player shooter && event.getEntity() instanceof Player player)) return;
				if (!plugin.getUserManager().getUser(shooter).isEnchantmentCompatible(RisingArrows.this)) return;
				if (!arrow.getCustomName().equals("rising_arrow")) return;

				player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 1, false, false, false));
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