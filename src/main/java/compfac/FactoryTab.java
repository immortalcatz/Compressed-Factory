package compfac;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class FactoryTab extends CreativeTabs{

	public FactoryTab() {
		super("CompressedFactory");
	}

	@Override
	public Item getTabIconItem() {
		return Items.BAKED_POTATO;
	}
	
	

}
