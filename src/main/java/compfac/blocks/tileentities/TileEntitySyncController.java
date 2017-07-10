package compfac.blocks.tileentities;

import compfac.world.dimension.FactoryHandler;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntitySyncController extends TileEntityCompressorBase{

	private boolean setupWasDone = false;
	private boolean controllerIsOn = false;
	private int controllerID;
	private int controllerTargetDimension;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("setupWasDone", this.setupWasDone);
		if (setupWasDone) {
				compound.setBoolean("controllerIsOn", this.controllerIsOn);
				compound.setInteger("controllerID", controllerID);
				compound.setInteger("controllerTargetDimension", controllerTargetDimension);
		}
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.setupWasDone = compound.getBoolean("setupWasDone");
		if(setupWasDone){
			this.controllerIsOn = compound.getBoolean("controllerIsOn");
			this.controllerID = compound.getInteger("controllerID");
			this.controllerTargetDimension = compound.getInteger("controllerTargetDimension");
		}
	}
	
	public void setController(TileEntityController control){
		this.controllerID = control.getId();
		this.controllerTargetDimension = control.getTargetDimension();
		this.controllerIsOn = true;
		setupWasDone = true;
//		System.out.println("Controller was set with id: " + controllerID + " and targetDimension: " + controllerTargetDimension);
	}
	
	public void setController(int controllerId, FactoryHandler handler){
		this.setController(handler.getController(controllerId));
	}
	
	public TileEntityController getController() {
		if(setupWasDone){
			TileEntityController control = TileEntityController.getcontroller(controllerID, controllerTargetDimension);
			if(control  == null){
				System.out.println("Controller is null");
				this.setupWasDone = false;
			}
			return control;
		} else {
			System.out.println("Setup has not been done yet");
			return null;
		}
	}
	
	public void setControllerStatus(boolean status){
		this.controllerIsOn = status;
	}
	
	public boolean controllerIsOff(){
		return !controllerIsOn;
	}
	
	public boolean setupWasDone(){
		return this.setupWasDone;
	}
}
