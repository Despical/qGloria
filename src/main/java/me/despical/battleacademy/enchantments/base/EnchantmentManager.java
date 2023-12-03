package me.despical.battleacademy.enchantments.base;

import me.despical.battleacademy.enchantments.FlameArrows;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;

public class EnchantmentManager {

	public static final Enchantment FLAME_ARROWS = new FlameArrows();
	public static final List<Enchantment> CUSTOM_ENCHANTMENTS = List.of(FLAME_ARROWS);

	public EnchantmentManager() {
		var enchantments = Arrays.asList(Enchantment.values());

		for (var enchantment : CUSTOM_ENCHANTMENTS) {
			if (!enchantments.contains(enchantment))
				registerEnchantment(enchantment);

			((CustomEnchantment) FLAME_ARROWS).initialize();
		}
	}

	private void registerEnchantment(Enchantment enchantment) {
		try {
			var field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, true);

			Enchantment.registerEnchantment(enchantment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}