package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;

public class BlockGlassUmbreakable extends BlockGlass {
	
	public BlockGlassUmbreakable() {
		super(Material.GLASS, false);
		this.setBlockUnbreakable().setResistance(6000000.0F);
		this.setUnlocalizedName("glass_umbreakable");
		this.setRegistryName("GlassUmbreakable");
		this.setCreativeTab(CompressedFactory.FACTORY_TAB);
	}

}
