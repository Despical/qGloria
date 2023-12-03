package me.despical.battleacademy.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import me.despical.battleacademy.enchantments.base.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class QuickFeet extends CustomEnchantment {

	public QuickFeet() {
		super("quick_feet", "Quick Feet", 1);
	}

	@Override
	public Listener registerEvents() {
		return new Listener() {

			@EventHandler
			public void onDamage(EntityDamageEvent event) {
				if (!(test(event.getEntity()))) return;

				var player = (Player) event.getEntity();

				if (player.getHealth() <= (player.getMaxHealth() * 40F) / 100F) {
					player.setWalkSpeed(.35F);
				}
			}

			@EventHandler
			public void onRegenerate(EntityRegainHealthEvent event) {
				if (!test(event.getEntity())) return;

				var player = (Player) event.getEntity();

				if (player.getHealth() > (player.getMaxHealth() * 40F) / 100F) {
					player.setWalkSpeed(.2F);
				}
			}

			private boolean test(Entity entity) {
				if (!(entity instanceof Player player)) return false;
				if (!plugin.getUserManager().getUser(player).isEnchantmentCompatible(QuickFeet.this)) return false;
				if (player.getInventory().getBoots() == null) return false;
                return player.getInventory().getBoots().getItemMeta().hasEnchant(QuickFeet.this);
            }
		};
	}

	@Override
	public int getElement() {
		return 4;
	}

	@Override
	public @NotNull EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR_FEET;
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