package lunar.enchantments.weapon.curse;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Cursed extends ExtendedMainhandEnchantment {
	public Cursed() {
		super("cursed");
		this.display_name_style.setColor(FormattingCode.DARK_GREY);
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 24, 22 };
		this.min_cost = new int[] { 20, 20 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:revenge"));
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = true;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof LivingEntity damager && event.getEntity() instanceof LivingEntity damagee) {// 人形实体造成伤害，受到伤害的是任意实体
			setTriggerEntity(damager);
			if (resolveTrigger()) {
				List<Entity> nearby_entities = damagee.getNearbyEntities(5, 5, 5);
				if (nearby_entities.size() < 5) {
					int ench_level = getLevel();// 根据魔咒等级召唤不同强度的敌对生物
					switch (ench_level) {
					case 1:
						damagee.getWorld().spawn(damagee.getLocation(), Zombie.class);
					case 2:
						damagee.getWorld().spawn(damagee.getLocation(), Skeleton.class);
					case 3:
						damagee.getWorld().spawn(damagee.getLocation(), WitherSkeleton.class);
					}
				}

			}
		}
	}

	// 根据附魔等级不同获取触发概率
	@Override
	public double getTriggerChance(int level) {
		switch (level) {
		case 1:
			return 0.05;
		case 2:
			return 0.15;
		case 3:
			return 0.30;
		}
		return 0;
	}
}