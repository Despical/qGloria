package me.despical.battleacademy.enchantments.base;

import me.despical.battleacademy.enchantments.FlameArrows;
import me.despical.battleacademy.enchantments.FlameCircle;
import me.despical.battleacademy.enchantments.QuickFeet;
import me.despical.battleacademy.enchantments.RisingArrows;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;

public class EnchantmentManager {

	public static final Enchantment FLAME_ARROWS = new FlameArrows();
	public static final Enchantment QUICK_FEET = new QuickFeet();
	public static final Enchantment RISING_ARROWS = new RisingArrows();
	public static final Enchantment FLAME_CIRCLE = new FlameCircle();

	public static final List<Enchantment> CUSTOM_ENCHANTMENTS = List.of(FLAME_ARROWS, QUICK_FEET, RISING_ARROWS, FLAME_CIRCLE);

	public EnchantmentManager() {
		var enchantments = Arrays.asList(Enchantment.values());

		for (var enchantment : CUSTOM_ENCHANTMENTS) {
			if (!enchantments.contains(enchantment))
				registerEnchantment(enchantment);

			((CustomEnchantment) enchantment).initialize();
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