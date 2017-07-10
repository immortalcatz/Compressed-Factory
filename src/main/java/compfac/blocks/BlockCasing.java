package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityEnergyOutlet;
import compfac.blocks.tileentities.TileEntityMultiBlockPart;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCasing extends BlockMultiBlockPart {
	
	public BlockCasing() {
		super("casing", "BlockCasing");
		
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityMultiBlockPart();
	}
}
