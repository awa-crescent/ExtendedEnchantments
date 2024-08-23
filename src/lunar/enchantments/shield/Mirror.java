package lunar.enchantments.shield;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lunar.template.ExtendedOffhandEnchantment;

//镜像:生命值低于某个限度时，受到攻击时有概率将攻击返回给敌对目标,而自己不受伤害
public class Mirror extends ExtendedOffhandEnchantment {
	public Mirror() {
		super("mirror");
		this.display_name_style.setColor("#ccccff");
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 55, 15 };
		this.min_cost = new int[] { 50, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:reflection"));
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) {// 副手装盾才能返还攻击
				if (damagee.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * resolveEnchantmentValue() > damagee.getHealth()) {
					LivingEntity target = EntityUtils.getDamageSourceEntity(event.getDamager());
					target.damage(event.getDamage(), event.getDamageSource());// 攻击者受到全额返还
					EntityUtils.spawnParticle(target, Particle.CRIT, 40, 2, 2, 2);
					event.setCancelled(true);
				}
			}
		}
	}

	// 根据附魔等级设置触发概率
	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.10;
		case 2:
			return 0.20;
		case 3:
			return 0.30;
		}
		return 0;
	}

	// 根据附魔等级设置生命阈值比例
	@Override
	public double getEnchantmentValue(int level) {
		switch (level) {
		case 1:
			return 0.30;
		case 2:
			return 0.50;
		case 3:
			return 0.70;
		}
		return 0;
	}
}