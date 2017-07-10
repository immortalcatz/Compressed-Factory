package compfac.blocks;

import compfac.blocks.tileentities.TileEntityEnergyOutlet;
import compfac.blocks.tileentities.TileEntityEnergyOutletFactory;
import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.blocks.tileentities.TileEntityItemBufferFactory;
import compfac.world.dimension.FactoryHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockEnergyOutletFactory extends BlockFactoryPart {

	public BlockEnergyOutletFactory() {
		super("energy_outlet_factory", "BlockEnergyOutletFactory");
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEnergyOutletFactory();
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityEnergyOutletFactory && hand == EnumHand.MAIN_HAND && heldItem == null){
				TileEntityEnergyOutletFactory outlet = (TileEntityEnergyOutletFactory) tileEntity;
				FactoryHandler.messagePlayer(playerIn, "Energy Outlet has " + outlet.getEnergyStored() + " energy", -1);
				return true;
			}
		}
		return false;
	}

}
