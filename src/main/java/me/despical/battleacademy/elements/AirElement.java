package me.despical.battleacademy.elements;

import me.despical.battleacademy.elements.base.Element;
import me.despical.battleacademy.elements.base.Passive;
import me.despical.battleacademy.user.User;
import me.despical.commons.miscellaneous.AttributeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AirElement extends Element {


	public AirElement(User user, int id) {
		super(user, 4);
		this.fallDamage = -40;
		this.fireDefence = -15;
		this.defense = -10;
		this.speed = 7;
	}

	@Override
	public void registerPassives() {

		var AirSharpness = new Passive("air_sharpness", 20, false);
		AirSharpness.setInitializer(player -> {
			boolean sword1 = player.getItemInHand().getType() == (Material.STONE_SWORD);
			boolean sword2 = player.getItemInHand().getType() == (Material.IRON_SWORD);
			boolean sword3 = player.getItemInHand().getType() == (Material.DIAMOND_SWORD);
			boolean sword4 = player.getItemInHand().getType() == (Material.NETHERITE_SWORD);

			if (sword1 || sword2 || sword3 ||sword4) {
				AttributeUtils.setAttackCooldown(player, player.getAttackCooldown() * (115/100F));
			}
		});

		var fragile = new Passive("fragile", 1, false);
		fragile.setListener(new Listener() {

			@EventHandler
			public void fragile(EntityDamageByEntityEvent e) {
				if (e.getDamager() instanceof Player) return;
				if (!(e.getEntity() instanceof Player)) return;
				if (!isPassiveEnabled(fragile)) {
					return;
				}

				e.setDamage(e.getDamage() * 108/100D);
			}
		});


	}
}
