package compfac.blocks;

import compfac.Reference;
import compfac.blocks.tileentities.TileEntityMultiBlockPart;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockMultiBlockPart extends BlockBase{

	public BlockMultiBlockPart(String unlName, String regName) {
		super(Material.GROUND, unlName, regName);
		this.setHardness(Reference.multiblockBlockHardness);
		this.setResistance(Reference.multiblockBlockResistence);
		this.setHarvestLevel("pickaxe", Reference.multiblockBlockHarvestLevel);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		((TileEntityMultiBlockPart) worldIn.getTileEntity(pos)).controllerStrutureCheck();
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

}
