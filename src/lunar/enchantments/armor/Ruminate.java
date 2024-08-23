package lunar.enchantments.armor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedArmorEnchantment;

//哀歌
public class Ruminate extends ExtendedArmorEnchantment {
	public Ruminate() {
		super("ruminate");
		this.setArmorType(ArmorType.HEAD);
		this.level_type = ArmorLevelType.MAX;
		this.display_name_style.setColor(FormattingCode.GREEN);
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 22, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.is_discoverable = true;
		this.is_treasure = false;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		setTriggerEntity(player);
		if (resolveTrigger()) {
			ItemStack item = event.getItem();
			if (item.getItemMeta().hasFood() || item.getType().equals(Material.POTION)) {
				item.setAmount(item.getAmount() + 1);
				player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, (int) (resolveEnchantmentValue() * 20), -1, false, false, false));
			}
		}
	}

	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.1;
		case 2:
			return 0.2;
		case 3:
			return 0.3;
		}
		return 0;
	}

	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 5;
		case 2:
			return 3;
		case 3:
			return 1;
		}
		return 0;
	}
}