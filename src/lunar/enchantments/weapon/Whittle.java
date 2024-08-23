package lunar.enchantments.weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityAttributeModifier;
import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.ExtendedEnchantments;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Whittle extends ExtendedMainhandEnchantment {
	private EntityAttributeModifier attrib_modifier = new EntityAttributeModifier(ExtendedEnchantments.plugin_name);

	public Whittle() {
		super("whittle");
		this.display_name_style.setColor(FormattingCode.DARK_PURPLE);
		this.setWeight(1);
		this.max_level = 5;
		this.min_level = 1;
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:sanction"));
		this.max_cost = new int[] { 55, 15 };
		this.min_cost = new int[] { 50, 10 };
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			this.setTriggerEntity(damager);
			if (this.resolveTrigger()) {
				int ench_level = getLevel();
				double health_limit = 1;// 高于这个限制则会受到削减效果影响
				AttributeInstance max_health = damagee.getAttribute(Attribute.GENERIC_MAX_HEALTH);
				if (ench_level != max_level)
					health_limit = max_health.getBaseValue() * (1 - resolveEnchantmentValue());
				double new_health = damagee.getHealth() - event.getFinalDamage();
				if (new_health > health_limit) {
					attrib_modifier.subBaseValue(damagee, Attribute.GENERIC_MAX_HEALTH, max_health.getBaseValue() - new_health, true);
					damagee.playHurtAnimation(0);
					EntityUtils.spawnParticle(damagee, Particle.CRIT, 80, 1, 1, 1);
					event.setCancelled(true);// 如果减少血量后生命值仍然大于1，则目标减少受到伤害的最大生命值
				}
			}
		}
	}

	// 根据附魔等级获取可以削减的百分比血量
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.15;
		case 2:
			return 0.30;
		case 3:
			return 0.50;
		case 4:
			return 0.70;
		}
		return 0;
	}
}