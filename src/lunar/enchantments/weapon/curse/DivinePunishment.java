package lunar.enchantments.weapon.curse;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class DivinePunishment extends ExtendedMainhandEnchantment {
	public DivinePunishment() {
		super("divine_punishment");
		this.display_name_style.setColor(FormattingCode.GOLD);
		this.setWeight(3);
		this.max_level = 4;
		this.min_level = 1;
		this.max_cost = new int[] { 24, 12 };
		this.min_cost = new int[] { 20, 10 };
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity dead_entity = event.getEntity();
		LivingEntity killer = dead_entity.getKiller();
		if (killer == null)// 不是被实体击杀导致死亡就直接返回
			return;
		setTriggerEntity(killer);
		if (resolveTrigger()) {
			killer.getWorld().strikeLightning(killer.getLocation());
			EntityUtils.spawnParticle(killer, Particle.ELECTRIC_SPARK, 80, 1, 1, 1);
		}
	}

	@Override
	public double getTriggerChance(int max_level) {
		return 0.25 * max_level;
	}
}