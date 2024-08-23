package lunar.template;

import lib.crescent.enchantment.template.OffhandEnchantment;
import lunar.ExtendedEnchantments;

public class ExtendedOffhandEnchantment extends OffhandEnchantment {

	public ExtendedOffhandEnchantment(String enchantment_id) {
		super(ExtendedEnchantments.plugin_name, "lunar", enchantment_id, "enchantments.offhand." + enchantment_id);
	}
}
