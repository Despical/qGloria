package me.despical.battleacademy.elements.base;

import lombok.Getter;
import me.despical.battleacademy.Main;
import me.despical.battleacademy.user.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {

	protected static final Main plugin = JavaPlugin.getPlugin(Main.class);

	private @Getter final List<Passive> passives;
	protected @Getter final int id;
	protected final User user;

	protected int attack, speed, fireDefence, lavaDefense, fallDamage, defense, attackSpeed;

	public Element(User user, int id) {
		this.user = user;
		this.id = id;
		this.passives = new ArrayList<>();
	}

	public void addPassive(Passive... passive) {
		passives.addAll(List.of(passive));
	}

	public abstract void registerPassives();

	public void initialize() {
		adjustSpeed();
		registerPassives();

		passives.forEach(passive -> {
			passive.registerEvents();

			if (passive.getInitializer() != null)
				passive.getInitializer().accept(user.getPlayer());
		});
	}

	protected boolean isPassiveEnabled(Passive passive) {
		return user.getLevel() >= passive.getEnableAt();
	}

	private void adjustSpeed() {
		user.getPlayer().setWalkSpeed(.2F * ((100F + speed) / 100F));
	}
}