package compfac;

import compfac.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class FactoryTab extends CreativeTabs{

	public FactoryTab() {
		super("CompressedFactory");
	}

	@Override
	public Item getTabIconItem() {
		return Items.STICK;
	}
	
	

}
