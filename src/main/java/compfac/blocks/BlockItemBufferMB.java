package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.blocks.tileentities.TileEntityItemBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockItemBufferMB extends BlockMultiBlockPart {
	
	public BlockItemBufferMB(String unlocalizedName, String registryName) {
		super(unlocalizedName, registryName);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityItemBuffer te = (TileEntityItemBuffer) worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, te);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(stack.hasDisplayName()){
			((TileEntityItemBuffer) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
}
