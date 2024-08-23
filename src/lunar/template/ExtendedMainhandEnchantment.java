package lunar.template;

import lib.crescent.enchantment.template.MainhandEnchantment;
import lunar.ExtendedEnchantments;

public class ExtendedMainhandEnchantment extends MainhandEnchantment {

	public ExtendedMainhandEnchantment(String enchantment_id) {
		super(ExtendedEnchantments.plugin_name, "lunar", enchantment_id, "enchantments.mainhand." + enchantment_id);
	}

}
