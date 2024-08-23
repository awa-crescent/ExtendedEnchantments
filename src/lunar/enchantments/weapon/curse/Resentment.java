package lunar.enchantments.weapon.curse;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

public class Resentment extends ExtendedMainhandEnchantment {
	private static HashMap<UUID, Integer> kill_counter = new HashMap<>();

	public Resentment() {
		super("resentment");
		this.display_name_style.setColor(FormattingCode.DARK_RED);
		this.setWeight(1);
		this.max_level = 4;
		this.min_level = 1;
		this.max_cost = new int[] { 32, 16 };
		this.min_cost = new int[] { 30, 14 };
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity dead_entity = event.getEntity();
		LivingEntity killer = dead_entity.getKiller();
		if (killer == null)// 不是被实体击杀导致死亡就直接返回
			return;
		setTriggerEntity(killer);
		if (resolveTrigger()) {
			UUID uuid = killer.getUniqueId();
			if (kill_counter.get(uuid) == null)
				kill_counter.put(uuid, 0);
			int counter = kill_counter.get(uuid) + 1;
			double stage_1 = resolveEnchantmentValue();// weakness
			double stage_2 = stage_1 * 1.5;// poisonous
			double stage_3 = stage_1 * 2;// wither
			double stage_4 = stage_1 * 3;// dead
			if (counter >= stage_1 && counter < stage_2) {
				killer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, PotionEffect.INFINITE_DURATION, -1, true, false, false));
			} else if (counter >= stage_2 && counter < stage_3) {
				killer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, PotionEffect.INFINITE_DURATION, 1, true, false, false));
			} else if (counter >= stage_3 && counter < stage_4) {
				killer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, PotionEffect.INFINITE_DURATION, 2, true, false, false));
			} else if (counter >= stage_4) {
				killer.setHealth(0);
				kill_counter.put(uuid, 0);
				return;
			}
			kill_counter.put(uuid, counter);
		}
	}

	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 12;
		case 2:
			return 8;
		case 3:
			return 4;
		}
		return 2;
	}
}