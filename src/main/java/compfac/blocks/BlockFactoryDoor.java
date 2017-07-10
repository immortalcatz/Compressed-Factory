package compfac.blocks;

import java.util.Arrays;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.blocks.tileentities.TileEntityFactoryDoor;
import compfac.world.EasyTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockFactoryDoor extends BlockMultiBlockPart {
	
	public BlockFactoryDoor() {
		super("factory_door", "BlockFactoryDoor");
		this.setLightLevel(1.0F);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND){
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityFactoryDoor && heldItem == null){
				TileEntityFactoryDoor door = (TileEntityFactoryDoor) tileEntity;
				door.teleport(playerIn);
				return true;
			}
		} else if(worldIn.isRemote && hand == EnumHand.MAIN_HAND && heldItem != null){
			worldIn.checkLight(pos);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFactoryDoor();
	}

}
