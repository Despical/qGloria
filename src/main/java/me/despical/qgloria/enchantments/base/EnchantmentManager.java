package me.despical.qgloria.enchantments.base;

import me.despical.qgloria.enchantments.*;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class EnchantmentManager {

	public static final Enchantment LIFE_STEAL = new LifeSteal();
	public static final Enchantment ATTACK_POWER = new AttackPower();
	public static final Enchantment CRITICAL_DAMAGE = new CriticalDamage();
	public static final Enchantment BLEEDING = new Bleeding();
	public static final Enchantment PLAYER_SLOWING = new PlayerSlowing();
	public static final Enchantment BLOCKING = new Blocking();

	public static final List<Enchantment> CUSTOM_ENCHANTMENTS = List.of(LIFE_STEAL, ATTACK_POWER, CRITICAL_DAMAGE, BLEEDING, PLAYER_SLOWING, BLOCKING);
	public static final Supplier<Enchantment> RANDOM_ENCHANTMENT = () -> CUSTOM_ENCHANTMENTS.get(ThreadLocalRandom.current().nextInt(6));

	public EnchantmentManager() {
		for (var enchantment : CUSTOM_ENCHANTMENTS) {
			if (Enchantment.getByName(enchantment.getName()) == null)
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