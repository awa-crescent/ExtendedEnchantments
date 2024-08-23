package lunar.enchantments.sharp_weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import lib.crescent.utils.SkinUtils;
import lib.crescent.utils.format.FormattingCode;
import lib.crescent.utils.format.FormattingStyle;
import lunar.ExtendedEnchantments;
import lunar.template.ExtendedMainhandEnchantment;

public class Beheading extends ExtendedMainhandEnchantment {
	protected FormattingStyle player_skull_style;

	public Beheading() {
		super("beheading");
		setMainhandType(MainhandType.SHARP_WEAPON);
		this.display_name_style.setColor(FormattingCode.DARK_RED);
		this.setWeight(4);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 24, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:capture"));
		this.is_discoverable = true;
		this.is_tradeable = true;
		this.player_skull_style = new FormattingStyle('#' + ExtendedEnchantments.getLocalString("metadata.player_skull.name_color"), ExtendedEnchantments.getLocalString("metadata.player_skull.name_font_style"));
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		LivingEntity killer = entity.getKiller();
		if (killer != null) {
			setTriggerEntity(killer);
			if (resolveTrigger()) {
				switch (entity.getType()) {
				case SKELETON:
					event.getDrops().add(new ItemStack(Material.SKELETON_SKULL));
					break;
				case WITHER_SKELETON:
					event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL));
					break;
				case ZOMBIE:
					event.getDrops().add(new ItemStack(Material.ZOMBIE_HEAD));
					break;
				case PIGLIN:
				case PIGLIN_BRUTE:
					event.getDrops().add(new ItemStack(Material.PIGLIN_HEAD));
					break;
				case CREEPER:
					event.getDrops().add(new ItemStack(Material.CREEPER_HEAD));
					break;
				case PLAYER:
					ItemStack skull = SkinUtils.generatePlayerSkull(entity.getUniqueId(), ExtendedEnchantments.getLocalString("metadata.player_skull.name"), this.player_skull_style);
					event.getDrops().add(skull);
					break;
				case ENDER_DRAGON:
					event.getDrops().add(new ItemStack(Material.DRAGON_HEAD));
					break;
				default:
					return;
				}
			}
		}
	}

	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.05;
		case 2:
			return 0.10;
		case 3:
			return 0.15;
		}
		return 0;
	}
}
