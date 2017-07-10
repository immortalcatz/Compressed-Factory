package compfac.blocks;

import net.minecraft.block.material.Material;

public abstract class BlockFactoryPart extends BlockBase{

	public BlockFactoryPart(String unlName, String regName) {
		super(Material.ROCK, unlName, regName);
		this.setBlockUnbreakable().setResistance(6000000.0F);
	}

}
