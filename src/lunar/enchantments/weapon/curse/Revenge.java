package lunar.enchantments.weapon.curse;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import lib.crescent.entity.EntityUtils;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

public class Revenge extends ExtendedMainhandEnchantment {
	public Revenge() {
		super("revenge");
		this.display_name_style.setColor(FormattingCode.DARK_RED);
		this.setWeight(1);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 32, 16 };
		this.min_cost = new int[] { 30, 14 };
		this.exclusive_set = new TreeSet<String>(Set.of("lunar:cursed"));
		this.is_curse = true;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		LivingEntity dead_entity = event.getEntity();
		if (EntityUtils.isHumanlikeEntity(dead_entity)) {// 人形实体造成伤害，受到伤害的是任意实体
			LivingEntity killer = dead_entity.getKiller();
			if (killer == null)// 不是被实体击杀导致死亡就直接返回
				return;
			setTriggerEntity(killer);
			if (resolveTrigger()) {
				int ench_level = getLevel();
				switch (ench_level) {
				case 1:
					dead_entity.getWorld().spawn(dead_entity.getLocation(), Zombie.class);
				case 2:
					dead_entity.getWorld().spawn(dead_entity.getLocation(), Skeleton.class);
				case 3:
					dead_entity.getWorld().spawn(dead_entity.getLocation(), WitherSkeleton.class);
				}
			}
		}
	}
}