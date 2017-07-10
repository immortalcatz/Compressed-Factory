package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;

public class BlockGlowstoneUmbreakable extends BlockGlowstone {
	
	public BlockGlowstoneUmbreakable() {
		super(Material.GLASS);
		this.setBlockUnbreakable().setResistance(6000000.0F);
		this.setUnlocalizedName("glowstone_umbreakable");
		this.setRegistryName("GlowstoneUmbreakable");
		this.setCreativeTab(CompressedFactory.FACTORY_TAB);
	}

}
