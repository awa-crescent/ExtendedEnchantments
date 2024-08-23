package lunar.enchantments.armor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.entity.EntityUtils;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class ThunderCounterstrike extends ExtendedArmorEnchantment {
	public ThunderCounterstrike() {
		super("thunder_counterstrike");
		this.display_name_style.setColor("#5d0eb9");
		this.level_type = ArmorLevelType.MAX;
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 44, 22 };
		this.min_cost = new int[] { 40, 20 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee && event.getDamager() instanceof Projectile projectile) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) {
				LivingEntity damager = EntityUtils.getDamageSourceEntity(projectile);
				damager.getWorld().strikeLightning(damager.getLocation());
			}
		}
	}

	// 根据附魔等级设置交换概率，每一级增加10%
	@Override
	public double getTriggerChance(int max_level) {
		return 0.15 * max_level;
	}
}