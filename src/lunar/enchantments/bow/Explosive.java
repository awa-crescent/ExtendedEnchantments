package lunar.enchantments.bow;

import lib.crescent.tag.Tag;
import lib.crescent.utils.format.FormattingCode;
import lunar.template.ExtendedMainhandEnchantment;

//雷击:攻击时有概率引发雷击,等级越高概率越大
public class Explosive extends ExtendedMainhandEnchantment {

	public Explosive() {
		super("explosive");
		this.display_name_style.setColor(FormattingCode.hexColor("#FF6600"));
		this.setWeight(2);
		this.max_level = 3;
		this.min_level = 1;
		this.max_cost = new int[] { 34, 12 };
		this.min_cost = new int[] { 30, 10 };
		this.supported_items = Tag.BOW_ENCHANTABLE;
		this.primary_items = Tag.BOW_ENCHANTABLE;
		this.is_discoverable = true;
		this.is_tradeable = false;
	}/*
		 * 
		 * public static ArrayList<Arrow> explosive_arrows = new ArrayList<>();
		 * 
		 * @EventHandler public void onEntityShootBow(EntityShootBowEvent e) { if
		 * (e.getEntityType() == EntityType.PLAYER) { TippedArrow arrow = (TippedArrow)
		 * e.getProjectile(); arrow.setColor(Color.BLACK); arrows.add(arrow); } }
		 * 
		 * @EventHandler public void onEntityHit(ProjectileHitEvent e) { if
		 * (e.getEntityType() == EntityType.TIPPED_ARROW) { TippedArrow arrow =
		 * (TippedArrow) e.getEntity(); World world = arrow.getWorld();
		 * world.spawnParticle(Particle.CLOUD, arrow.getLocation(), 20);
		 * arrows.remove(arrow); } }
		 * 
		 * public void tick() { for (Arrow arrow : arrows) { if (arrow.isOnGround() ||
		 * arrow.isDead() || arrow.isInBlock()) { arrows.remove(arrow); return; } else {
		 * World world = arrow.getWorld(); world.spawnParticle(Particle.BARRIER,
		 * arrow.getLocation(), 10); } } }
		 * 
		 * public void onEnable() { new BukkitRunnable() {
		 * 
		 * @Override public void run() { tick(); } }.runTaskTimerAsynchronously(this, 0,
		 * 5); }
		 */
}