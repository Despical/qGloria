package me.despical.battleacademy.elemental;

import me.despical.commons.compat.XMaterial;
import me.despical.commons.item.ItemBuilder;
import me.despical.inventoryframework.GuiItem;

public class SelectComponents {

	public void injectComponents(SelectMenu menu) {

		var fireItem = new ItemBuilder(XMaterial.CAMPFIRE)
			.name("#ff2600&lFire #ff2600⚔")
			.lore(" ")
			.lore("&7Fire elemental damage is higher than other")
			.lore("&7elements, you take less damage from fire and lava,")
			.lore("&7but you walk slowly and have less defence.")
			.lore("")
			.lore("#ff2600&lSkills:")
			.lore("#ffd930• Fire Arrows: #999999The ability to fire fire arrows at multiple enemies.")
			.lore("#ffd930• Execution: #999999Deal instant damage to enemies with a fiery blow.")
			.lore("#ffd930• Flame Lightning: #999999Launch a high-damage fireball to cover a large area.")
			.lore("#ffd930• Flame Armor: #999999Absorbing attacks by creating fire-resistant armor for a short time.")
			.lore("#ffd930• Exploding Flame: #999999An attack that sprays fire when you hit enemies.")
			.lore("#ffd930• Ring of Flame: #999999Burn and slow enemies by creating a ring of fire around you.")
			.lore("")
			.lore("#ff2600&lCore Values:")
			.lore("#ffd930• Attack: #30ff491.5")
			.lore("#ffd930• Fire Defense: #30ff491.2")
			.lore("#ffd930• Lava Defense: #30ff491.3")
			.lore("#ffd930• Speed: #A932260.9")
			.build();

		menu.pane.addItem(new GuiItem(fireItem), 1,1);
	}


}
