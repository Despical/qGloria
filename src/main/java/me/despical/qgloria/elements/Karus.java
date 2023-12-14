package me.despical.qgloria.elements;

import me.despical.qgloria.elements.base.Element;
import me.despical.qgloria.user.User;

public class Karus extends Element {

	public Karus(User user) {
		super(user, 4);
		this.fallDamage = -40;
		this.fireDefence = -15;
		this.defense = -10;
		this.speed = 7;
		this.attackSpeed = -15;
	}

	@Override
	public void registerPassives() {
	}
}