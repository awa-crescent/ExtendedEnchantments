package lunar.enchantments.armor;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lib.crescent.entity.ArmorLevelType;
import lib.crescent.utils.format.FormattingCode;
import lib.crescent.utils.trigger.TriggerCondition;
import lunar.template.ExtendedArmorEnchantment;

//盾牌反射:受到攻击时有概率将攻击返回给敌对目标,等级越高概率越大
public class Dodge extends ExtendedArmorEnchantment {
	public Dodge() {
		super("dodge");
		this.display_name_style.setColor(FormattingCode.MINECOIN_COPPER);
		this.level_type = ArmorLevelType.SUM;
		this.setWeight(3);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 22, 11 };
		this.min_cost = new int[] { 20, 10 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:phantom", "lunar:exorcism"));
		this.is_discoverable = true;
		this.is_treasure = true;
		this.is_tradeable = true;
		setExtraTriggerCondition(new TriggerCondition<EntityDamageByEntityEvent>() {
			public boolean checkTriggerCondition(EntityDamageByEntityEvent event) {
				switch (event.getDamageSource().getDamageType().getKey().getKey()) {
				case "mob_attack":
				case "mob_attack_no_aggro":
				case "player_attack":
					return true;
				default:
					return false;
				}
			}
		});
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity damagee) {// 受到伤害的是人形实体
			setTriggerEntity(damagee);
			if (resolveTrigger(event)) {
				event.setCancelled(true);
			}
		}
	}

	// 根据附魔等级设置概率，每一级增加10%
	@Override
	public double getTriggerChance(int sum_level) {
		return 0.10 * sum_level;
	}
}