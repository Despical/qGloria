package me.despical.qgloria.armorcalc;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Despical
 * <p>
 * Created at 20.12.2023
 */
public class EnchantmentInfo {

	public Enchantment enchantment;
	public Float levelScale;
	public float value;
	public final Map<Integer, Double> levelAssignments;

	public EnchantmentInfo(){
		this.levelAssignments = new HashMap<>();
	}

	public String toString(){
		return Float.toString(value);
	}
}