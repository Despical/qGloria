package me.despical.qgloria.elements;

import me.despical.qgloria.elements.base.Element;
import me.despical.qgloria.elements.base.Passive;
import me.despical.qgloria.user.User;
import me.despical.commons.number.NumberUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class IceElement extends Element {

	public IceElement(User user) {
		super(user, 2);
		this.speed = 5;
		this.attack = -3;
		this.fireDefence = -7;
		this.lavaDefense = -5;
	}

	@Override
	public List<Enchantment> getEnchantments() {
		return null;
	}

	@Override
	public void registerPassives() {
		var breathUnderwater = new Passive("breath_underwater", 3, false);
		breathUnderwater.setInitializer(player -> {
			if (!isPassiveEnabled(breathUnderwater)) return;

			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 255, false, false, false));
		});

		var depthStrider = new Passive("depth_strider", 7, false);
		depthStrider.setInitializer(player -> {
			int level = 0, userLevel = user.getLevel();

			if (NumberUtils.isBetween(userLevel, 15, 20))
				level = 1;
			else if (NumberUtils.isBetween(userLevel, 21, 25))
				level = 2;

			player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, level, false, false, false));
		});

		addPassive(breathUnderwater, depthStrider);
	}
}