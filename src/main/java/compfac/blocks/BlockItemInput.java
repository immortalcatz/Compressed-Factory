package compfac.blocks;

import compfac.blocks.tileentities.TileEntityItemBuffer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemInput extends BlockItemBufferMB {

	public BlockItemInput() {
		super("item_input", "BlockItemInput");
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityItemBuffer();
	}
}
