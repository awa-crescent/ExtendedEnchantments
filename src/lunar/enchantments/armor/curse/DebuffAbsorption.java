package lunar.enchantments.armor.curse;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.effect.EffectUtils;
import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class DebuffAbsorption extends ExtendedArmorEnchantment {
	public DebuffAbsorption() {
		super("debuff_absorption");
		this.display_name_style.setColor(FormattingCode.DARK_GREY);
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 32, 11 };
		this.min_cost = new int[] { 30, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:debuff_transfer", "lunar:state_exchange"));
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger()) {// 四件中任意一个有才能触发效果
				LivingEntity target = EntityUtils.getDamageSourceEntity(event.getDamager());
				if (target == null)
					return;
				damagee.addPotionEffects(EffectUtils.removeAllVanillaNegativePotionEffects(target));
			}
		}
	}

	// 根据附魔等级设置交换概率，每一级增加2.5%
	@Override
	public double getTriggerChance(int sum_level) {
		return 0.05 * sum_level;
	}
}