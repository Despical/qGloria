package me.despical.qgloria.enchantments.base;

import me.despical.qgloria.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public abstract class CustomEnchantment extends Enchantment {

	protected static final Main plugin = JavaPlugin.getPlugin(Main.class);

	protected final int maxLevel;
	protected final String name;

	public CustomEnchantment(String namespace, String name, int maxLevel) {
		super(NamespacedKey.minecraft(namespace));
		this.name = name;
		this.maxLevel = maxLevel;
	}

	public void initialize() {
		plugin.getServer().getPluginManager().registerEvents(registerEvents(), plugin);
	}

	public abstract Listener registerEvents();

	public final int getRandomLevel() {
		return ThreadLocalRandom.current().nextInt(maxLevel) + 1;
	}

	@Override
	public @NotNull String getName() {
		return name;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}

	@Override
	public @NotNull Component displayName(int i) {
		return null;
	}

	@Override
	public boolean conflictsWith(@NotNull Enchantment enchantment) {
		return false;
	}

	@Override
	public boolean canEnchantItem(@NotNull ItemStack itemStack) {
		return false;
	}

	@Override
	public @NotNull String translationKey() {
		return null;
	}

	@Override
	public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
		return 0;
	}

	@Override
	public int getMinModifiedCost(int i) {
		return 0;
	}

	@Override
	public int getMaxModifiedCost(int i) {
		return 0;
	}

	@Override
	public int getStartLevel() {
		return 0;
	}
}