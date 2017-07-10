package compfac.items;

import compfac.CompressedFactory;
import net.minecraft.item.Item;

public abstract class BaseItem extends Item{

	public BaseItem(String unlocalizedName, String registryName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CompressedFactory.FACTORY_TAB);
	}
}
