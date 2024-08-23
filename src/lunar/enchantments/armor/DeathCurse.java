package lunar.enchantments.armor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class DeathCurse extends ExtendedArmorEnchantment {
	public DeathCurse() {
		super("death_curse");
		this.display_name_style.setColor(FormattingCode.DARK_RED);
		this.level_type = ArmorLevelType.MAX;
		this.setWeight(1);
		this.max_level = 1;
		this.min_level = 1;
		this.max_cost = new int[] { 34, 12 };
		this.min_cost = new int[] { 30, 10 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity dead_entity = event.getEntity();
		LivingEntity killer = dead_entity.getKiller();
		if (killer == null)// 不是被实体击杀导致死亡就直接返回
			return;
		setTriggerEntity(dead_entity);
		if (resolveTrigger()) {
			killer.setHealth(killer.getHealth() * 0.5);
		}
	}
}