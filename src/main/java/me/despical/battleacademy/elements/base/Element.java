package me.despical.battleacademy.elements.base;

import me.despical.battleacademy.Main;
import me.despical.battleacademy.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {

	protected static final Main plugin = JavaPlugin.getPlugin(Main.class);

	private final List<Passive> passives;
	protected final int id;
	protected final User user;

	protected int attack, speed, fireDefence, lavaDefense, fallDamage, defense;

	public Element(User user, int id) {
		this.user = user;
		this.id = id;
		this.passives = new ArrayList<>();
	}

	public void addPassive(Passive... passive) {
		passives.addAll(List.of(passive));
	}

	public abstract void registerPassives();

	protected boolean isPassiveEnabled(Passive passive) {
		return user.getLevel() >= passive.getEnableAt();
	}

	public void setSpeed(Player player) {
		player.setWalkSpeed(.2F * ((100F + speed) / 100F));
	}
}