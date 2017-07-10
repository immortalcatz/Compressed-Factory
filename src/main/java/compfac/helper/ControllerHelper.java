package compfac.helper;

import compfac.blocks.tileentities.TileEntityController;
import compfac.world.dimension.FactoryHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class ControllerHelper {

	private BlockPos controllerPos;
	private int inDimension;
	
	public ControllerHelper(TileEntityController controller) {
		this.controllerPos = controller.getPos();
		this.inDimension = controller.getSourceDimension();
	}
	public ControllerHelper(NBTTagCompound compound) {
		this.readFromNBT(compound);
	}
	
	public TileEntityController getController() {
		return (TileEntityController) FactoryHandler.worldServerForDimension(inDimension).getTileEntity(controllerPos);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		FactoryHandler.writeBlockPosToNBT(compound, "controllerHelperPos", controllerPos);
		compound.setInteger("dimensionControllerHelper", inDimension);
		return compound;
	}
	
	private void readFromNBT(NBTTagCompound compound) {
		this.controllerPos = FactoryHandler.readBlockPosFromNBT(compound, "controllerHelperPos");
		this.inDimension = compound.getInteger("dimensionControllerHelper");
	}
}
