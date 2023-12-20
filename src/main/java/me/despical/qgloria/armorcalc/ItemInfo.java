package me.despical.qgloria.armorcalc;

import org.bukkit.Material;

/**
 * @author Despical
 * <p>
 * Created at 20.12.2023
 */
public class ItemInfo {

	public Material material;
	public float value;

	public String toString(){
		return String.valueOf(value);
	}
}