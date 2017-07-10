package compfac.blocks.tileentities;

public class TileEntityMultiBlockPart extends TileEntitySyncController{

	public void controllerStrutureCheck(){
		if(this.setupWasDone())
			this.getController().isStructureStillValid();
	}
}
