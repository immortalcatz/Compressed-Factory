package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.blocks.tileentities.TileEntityEnergyOutlet;
import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.world.dimension.FactoryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class BlockEnergyOutlet extends BlockMultiBlockPart {
	
	public BlockEnergyOutlet() {
		super("energy_outlet", "BlockEnergyOutlet");
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEnergyOutlet();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityEnergyOutlet && hand == EnumHand.MAIN_HAND && heldItem == null){
				TileEntityEnergyOutlet outlet = (TileEntityEnergyOutlet) tileEntity;
				FactoryHandler.messagePlayer(playerIn, "Energy Outlet has " + outlet.getEnergyStored() + " energy", -1);
				return true;
			}
		}
		return false;
	}
}
