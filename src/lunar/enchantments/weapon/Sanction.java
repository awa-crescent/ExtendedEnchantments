package lunar.enchantments.weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityAttributeModifier;
import lunar.ExtendedEnchantments;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Sanction extends ExtendedMainhandEnchantment {
	private EntityAttributeModifier attrib_modifier = new EntityAttributeModifier(ExtendedEnchantments.plugin_name);

	public Sanction() {
		super("sanction");
		this.display_name_style.setColor("#CC3333");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 54, 30 };
		this.min_cost = new int[] { 50, 24 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:whittle"));
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				attrib_modifier.subBaseValue(damagee, Attribute.GENERIC_ARMOR, damagee.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue() * resolveEnchantmentValue(), true);// 百分比削弱护甲效果，永久降低敌人的护甲
			}
		}
	}

	// 根据附魔等级获取可以削减的百分比护甲
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.02;
		case 2:
			return 0.05;
		case 3:
			return 0.10;
		}
		return 0;
	}
}