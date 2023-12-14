package me.despical.qgloria.elements;

import me.despical.qgloria.elements.base.Element;
import me.despical.qgloria.elements.base.Passive;
import me.despical.qgloria.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Kurian extends Element {

	public Kurian(User user) {
		super(user, 1);
		this.defense = 5;
		this.fallDamage = -10;
		this.speed = 5;
	}

	@Override
	public void registerPassives() {
		var lifeStealing = new Passive("lifestealing", 0, false);
		lifeStealing.setListener(new Listener() {

			@EventHandler
			public void onDamage(EntityDamageByEntityEvent event) {
				if (!(event.getDamager() instanceof Player damager && event.getEntity() instanceof Player player)) return;
				if (!isPassiveEnabled(lifeStealing)) return;

				if (ThreadLocalRandom.current().nextInt(100) <= 5) {
					damager.setHealth(damager.getHealth() + ThreadLocalRandom.current().nextDouble(1));
				}
			}
		});

		addPassive(lifeStealing);
	}
}