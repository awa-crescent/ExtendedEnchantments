package lunar.enchantments.armor;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.utils.trigger.TriggerCondition;
import lunar.template.ExtendedArmorEnchantment;

public class Exorcism extends ExtendedArmorEnchantment {
	public Exorcism() {
		super("exorcism");
		this.display_name_style.setColor("#004374");
		this.level_type = ArmorLevelType.MAX;
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 22, 11 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:phantom", "lunar:dodge"));
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
		setExtraTriggerCondition(new TriggerCondition<EntityDamageByEntityEvent>() {
			public boolean checkTriggerCondition(EntityDamageByEntityEvent event) {
				switch (event.getDamageSource().getDamageType().getKey().getKey()) {
				case "magic":
				case "wither":
				case "dragon_breath":
				case "indirect_magic":
				case "thorns":
				case "sonic_boom":
				case "unattributed_fireball":
					return true;
				default:
					return false;
				}
			}
		});
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger(event)) {
				event.setDamage(event.getDamage() * (1 - resolveEnchantmentValue()));
			}
		}
	}

	// 根据附魔等级设置交换概率，每一级增加10%
	@Override
	public double getEnchantmentValue(int max_level) {
		switch (max_level) {
		case 1:
			return 0.10;
		case 2:
			return 0.20;
		case 3:
			return 0.30;
		}
		return 0;
	}
}