package me.despical.battleacademy.elements;

import me.despical.battleacademy.elements.base.Element;
import me.despical.battleacademy.elements.base.Passive;
import me.despical.battleacademy.enchantments.base.EnchantmentManager;
import me.despical.battleacademy.user.User;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class FireElement extends Element {

	public FireElement(User user) {
		super(user, 1);
		this.attack = 5;
		this.speed = -3;
	}

	@Override
	public List<Enchantment> getEnchantments() {
		return List.of(EnchantmentManager.FLAME_ARROWS);
	}

	@Override
	public void registerPassives() {
		var flameArmor = new Passive("flame_armor", 3, false);
		flameArmor.setListener(new Listener() {

			@EventHandler
			public void onDamage(EntityDamageEvent event) {
				if (!(event.getEntity() instanceof Player)) return;
				if (!isPassiveEnabled(flameArmor)) return;

				switch (event.getCause()) {
					case FIRE, FIRE_TICK, LAVA -> event.setCancelled(true);
				}
			}
		});

		addPassive(flameArmor);
	}
}