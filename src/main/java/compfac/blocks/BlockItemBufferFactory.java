package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.blocks.tileentities.TileEntityItemBufferFactory;
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

public abstract class BlockItemBufferFactory extends BlockFactoryPart {
	
	public BlockItemBufferFactory(String unlocalizedName, String registryName) {
		super(unlocalizedName, registryName);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
}
