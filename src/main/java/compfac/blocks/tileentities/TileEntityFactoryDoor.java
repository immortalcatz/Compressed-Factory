package compfac.blocks.tileentities;

import compfac.Reference;
import compfac.world.EasyTeleporter;
import compfac.world.dimension.FactoryHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileEntityFactoryDoor extends TileEntityMultiBlockPart{

	private boolean isReturnTeleportal = false;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("isReturnTeleportal", this.isReturnTeleportal);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.isReturnTeleportal = compound.getBoolean("isReturnTeleportal");
	}
	
	public TileEntityFactoryDoor setReturnTeleportal() {
		this.isReturnTeleportal = true;
		return this;
	}

	public void teleport(EntityPlayer playerIn) {
		if(playerIn instanceof EntityPlayerMP) {
			EntityPlayerMP thePlayer = (EntityPlayerMP) playerIn;
			if (!this.setupWasDone()) {
				FactoryHandler.messagePlayer(thePlayer, "Factory not made yet (no setup yet)", -1);
//				System.out.println("controllerIDTeleportal: " + controllerIDTeleportal);
//				System.out.println("controllerTargetDimension: " + controllerTargetDimension);
				return;
			} else if (getController() == null) {
//				System.out.println("Factory no made yet");
				FactoryHandler.messagePlayer(thePlayer, "Factory not made yet  (no controller)", -1);
//				System.out.println("controllerIDTeleportal: " + controllerIDTeleportal);
//				System.out.println("controllerTargetDimension: " + controllerTargetDimension);
				return;
			} else if (this.controllerIsOff() && !this.isReturnTeleportal){
//				System.out.println("controller inactive");
				FactoryHandler.messagePlayer(thePlayer, "Controller is off", -1);
				return;
			}
			if(thePlayer.timeUntilPortal > 0) {
	        	thePlayer.timeUntilPortal = 10;
			} else {
				thePlayer.timeUntilPortal = 10;
				if(getController().getTargetDimension() == 0) {
					FactoryHandler.messagePlayer(thePlayer, "In last dimension, cant go any deeper", -1);
					return;
				}
				if (getController().getId() == -1){
					FactoryHandler.messagePlayer(thePlayer, "Bug: Id in not valid", -1);
					return;
				}
				if (getController().getFactoryHandler() == null){
					FactoryHandler.messagePlayer(thePlayer, "Bug: Cant get handler to tp", -1);
					return;
				}
				BlockPos posTotp;
				if(!isReturnTeleportal){
//					System.out.println("This is a go teleporter");
					FactoryHandler.messagePlayer(thePlayer, "Compressing player...", 1);
					thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, this.getController().getTargetDimension(), new EasyTeleporter(thePlayer.mcServer.worldServerForDimension(this.getController().getTargetDimension())));
					posTotp = getController().getFactoryHandler().getPlaceToTp(getController().getId());
					getController().setPositionBeforeTeleport(thePlayer.getPosition());
					FactoryHandler.messagePlayer(thePlayer, "Player Compressed", 0);
				} else {
//					System.out.println("This is a return teleporter");
					FactoryHandler.messagePlayer(thePlayer, "Decompressing player...", 1);
					thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, getController().getSourceDimension(), new EasyTeleporter(thePlayer.mcServer.worldServerForDimension(getController().getSourceDimension())));
					posTotp = getController().getPositionBeforeTeleport();
					FactoryHandler.messagePlayer(thePlayer, "Player Decompressed", 1);
				}
				thePlayer.setPositionAndUpdate(posTotp.getX(), posTotp.getY(), posTotp.getZ());
			}
		} else {
			System.out.println("Need to change code - Have to tp another way");
		}
	}
}
