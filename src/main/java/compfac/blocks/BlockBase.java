package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {

	public BlockBase(Material material, String unlName, String regName) {
		super(material);
		this.setUnlocalizedName(unlName);
		this.setRegistryName(regName);
		this.setCreativeTab(CompressedFactory.FACTORY_TAB);
	}

}
