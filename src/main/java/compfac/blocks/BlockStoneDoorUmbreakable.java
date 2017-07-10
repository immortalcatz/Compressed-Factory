package compfac.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityEnergyOutlet;
import compfac.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockStoneDoorUmbreakable extends BlockFactoryPart {
	
	public BlockStoneDoorUmbreakable() {
		super("stone_door_umbreakable", "StoneDoorUmbreakable");
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND && heldItem.getItem() == ModItems.breakStoneDoor){
			worldIn.setBlockToAir(pos);
			return true;
		}
		return false;
	}
}
