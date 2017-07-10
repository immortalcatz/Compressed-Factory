package compfac.blocks;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockController extends BlockMultiBlockPart{

	public BlockController() {
		super("controller", "BlockController");
		this.setHardness(Reference.controllerBlockHardness);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote){
			TileEntity tl = worldIn.getTileEntity(pos);
			if(tl instanceof TileEntityController){
				TileEntityController ctrl = (TileEntityController) tl;
				if(!ctrl.activateFactoryDestruction()) {
					System.out.println("Factory for destruction does not exist");
					return;
				}
				System.out.println("Factory Destruction sucessfull");
				return;
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote && hand == EnumHand.MAIN_HAND){
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityController){
				TileEntityController controller = (TileEntityController) tileEntity;
				if(heldItem != null){
					if(heldItem.getItem() == Items.STICK){
						System.out.println("Controller activated");
						controller.setPlayer(playerIn);
						if(!controller.setdimensionNr(playerIn.dimension)) {
							System.out.println("Dimension not set properly or last level");
							System.out.println("Player is in dimension: " + playerIn.dimension);
							System.out.println("Source dimension was set has: " + controller.getSourceDimension());
							System.out.println("Target dimension was set has: " + controller.getTargetDimension());
							return false;
						}
						controller.onActivation();
						return true;
					} else if(heldItem.getItem() == Items.SHEARS){
						controller.setPlayer(playerIn);
						controller.resize();
						System.out.println("Controller starting resizing");
						if(!controller.setdimensionNr(playerIn.dimension)) {
							System.out.println("Last level");
							return false;
						}
						controller.onActivation();
						return true;
					} else if(heldItem.getItem() == Items.PAPER){
						System.out.println("Controller starting factory Destruction");
						controller.setPlayer(playerIn);
						if(!controller.setdimensionNr(playerIn.dimension)) {
							System.out.println("Last level");
							return false;
						}
						if(!controller.activateFactoryDestruction()){
							System.out.println("Factory for destruction does not exist");
							return false;
						}
						System.out.println("Factory Destruction sucessfull");
						return true;
					} 
				} else {
					controller.setPlayer(playerIn);
					controller.messagePlayer("The Controller is " + (controller.isOff()?"off":"on"), -1);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityController();
	}
	

}
