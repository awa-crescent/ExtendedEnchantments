package lunar.enchantments.weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class End extends ExtendedMainhandEnchantment {
	public End() {
		super("end");
		this.display_name_style.setColor(FormattingCode.GRAY);
		this.setWeight(2);
		this.max_level = 1;
		this.min_level = 1;
		this.max_cost = new int[] { 25, 15 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:kindness"));
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger())
				if (event.getDamage() > damagee.getHealth())// 原始伤害若大于目标现有生命值则直接造成实体最大生命值的伤害
					event.setDamage(damagee.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		}
	}
}