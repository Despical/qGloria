package me.despical.battleacademy.elements;

import me.despical.battleacademy.elements.base.Element;
import me.despical.battleacademy.elements.base.Passive;
import me.despical.battleacademy.user.User;
import me.despical.commons.miscellaneous.AttributeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AirElement extends Element {

	public AirElement(User user) {
		super(user, 4);
		this.fallDamage = -40;
		this.fireDefence = -15;
		this.defense = -10;
		this.speed = 7;
		this.attackSpeed = -15;
	}

	@Override
	public void registerPassives() {
		var airSharpness = new Passive("air_sharpness", 1, false); // level - 20
		airSharpness.setInitializer(player -> AttributeUtils.setAttackCooldown(player, 4D * (((100D + (double) attackSpeed) / 100D))));

		var fragile = new Passive("fragile", 1, false);
		fragile.setListener(new Listener() {

			@EventHandler
			public void onEntityDamage(EntityDamageByEntityEvent e) {
				if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) return;
				if (!isPassiveEnabled(fragile))	return;

				e.setDamage(e.getDamage() * (108D / 100D));
			}
		});

		addPassive(airSharpness, fragile);
	}
}