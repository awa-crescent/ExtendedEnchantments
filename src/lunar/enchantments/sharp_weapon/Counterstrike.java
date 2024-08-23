package lunar.enchantments.sharp_weapon;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lunar.template.ExtendedMainhandEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Counterstrike extends ExtendedMainhandEnchantment {
	public Counterstrike() {
		super("counterstrike");
		setMainhandType(MainhandType.SHARP_WEAPON);
		this.setWeight(3);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 24, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:critical"));
		this.is_discoverable = true;
		this.is_tradeable = true;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) {
				LivingEntity target = null;
				if (!(event.getDamager() instanceof Projectile) && event.getDamager() instanceof LivingEntity damager)// 如果是实体直接造成伤害则返还给该实体，如果造成伤害的实体是抛射物，则无法反伤
					target = damager;
				target.damage(event.getDamage() * resolveEnchantmentValue(), event.getDamageSource());// 攻击者受到全额返还
			}
		}
	}

	// 根据附魔等级设置反伤比例
	@Override
	public double getEnchantmentValue(int level) {
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
}