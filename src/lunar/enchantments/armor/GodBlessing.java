package lunar.enchantments.armor;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class GodBlessing extends ExtendedArmorEnchantment {
	public GodBlessing() {
		super("god_blessing");
		this.display_name_style.setColor(FormattingCode.MATERIAL_GOLD);
		this.level_type = ArmorLevelType.MAX;
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 44, 12 };
		this.min_cost = new int[] { 40, 10 };
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) {
				if (event.getFinalDamage() > damagee.getHealth()) {
					damagee.setHealth(2);
					damagee.playHurtAnimation(0);
					damagee.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 100, 0, false, false, false));
					EntityUtils.spawnParticle(damagee, Particle.WHITE_ASH, 80, 1, 1, 1);
					damagee.getWorld().playSound(damagee, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 0);
					event.setCancelled(true);
				}
			}
		}
	}

	// 根据附魔等级设置交换概率，每一级增加10%
	@Override
	public double getTriggerChance(int max_level) {
		return 0.10 * max_level;
	}
}