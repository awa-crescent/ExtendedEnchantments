package lunar.enchantments.weapon.curse;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

/**
 * 仁慈：持有该附魔的武器攻击敌人，如果这次攻击完成以后敌人将死亡，则取消此次攻击
 */
public class Kindness extends ExtendedMainhandEnchantment {
	public Kindness() {
		super("kindness");
		this.display_name_style.setColor(FormattingCode.MATERIAL_GOLD);
		this.setWeight(2);
		this.max_level = 1;
		this.min_level = 1;
		this.max_cost = new int[] { 25, 7 };
		this.min_cost = new int[] { 20, 5 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:end"));
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				if (event.getFinalDamage() >= damagee.getHealth())
					event.setCancelled(true);
			}
		}
	}
}