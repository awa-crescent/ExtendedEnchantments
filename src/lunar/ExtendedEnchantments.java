package lunar;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lib.crescent.enchantment.EnchantmentManager;
import lib.crescent.utils.locale.Locale;

public class ExtendedEnchantments extends JavaPlugin {
	public static final String plugin_name = "ExtendedEnchantments";// 要与plugin.yml中的名字保持一致
	public FileConfiguration config;
	private static Locale locale;// locale引用

	public void onEnable() {
		loadConfigs();
		registerEnchantments();
	}

	private void registerEnchantments() {
		EnchantmentManager.registerAll(this, "lunar.enchantments", true);
	}

	private void loadConfigs() {
		config = this.getConfig();
		locale = Locale.loadLocale(plugin_name, "locale", config.getString("locale"));
	}

	public static String getLocalString(String key) {
		return locale.getLocalizedValue(key);
	}
}