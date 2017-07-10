package compfac.blocks;

import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.blocks.tileentities.TileEntityItemBufferFactory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemInputFactory extends BlockItemBufferFactory {

	public BlockItemInputFactory() {
		super("item_input_factory", "BlockItemInputFactory");
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityItemBufferFactory();
	}
}
