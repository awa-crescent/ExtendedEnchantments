package lunar.enchantments.weapon.curse;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Sacrifice extends ExtendedMainhandEnchantment {
	public Sacrifice() {
		super("sacrifice");
		this.display_name_style.setColor(FormattingCode.DARK_RED);
		this.setWeight(1);
		this.max_level = 1;
		this.min_level = 1;
		this.max_cost = new int[] { 32, 7 };
		this.min_cost = new int[] { 30, 5 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:life_connection"));
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		LivingEntity damager = EntityUtils.getDamageSourceEntity(event.getDamager());
		if (damager == null)
			return;
		if (!(event.getEntity() instanceof LivingEntity damagee))
			return;
		if (damager instanceof LivingEntity) // 造成伤害的是人形实体，且持有牺牲诅咒的武器，则对造成伤害者等量伤害
			setTriggerEntity(damager);
		else if (damagee instanceof LivingEntity) // 受到伤害的是人形实体，且持有牺牲诅咒的武器，则对造成伤害者等量伤害
			setTriggerEntity(damagee);
		if (resolveTrigger()) {
			damager.damage(event.getDamage());
			DustOptions dust = new DustOptions(Color.RED, 1);
			EntityUtils.spawnParticle(damager, Particle.DUST, 20, 0.5, 1, 0.5, dust);
			EntityUtils.spawnParticle(damagee, Particle.DUST, 20, 0.5, 1, 0.5, dust);
		}
	}
}