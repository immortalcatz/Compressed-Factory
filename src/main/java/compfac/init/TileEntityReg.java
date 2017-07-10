package compfac.init;

import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.blocks.tileentities.TileEntityEnergyOutlet;
import compfac.blocks.tileentities.TileEntityEnergyOutletFactory;
import compfac.blocks.tileentities.TileEntityFactoryDoor;
import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.blocks.tileentities.TileEntityItemBufferFactory;
import compfac.blocks.tileentities.TileEntityMultiBlockPart;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityReg {
	
	public static void doEntitiesRegistration() {
		GameRegistry.registerTileEntity(TileEntityMultiBlockPart.class, Reference.MOD_ID + ".TileEntityMultiBlockPart");
		GameRegistry.registerTileEntity(TileEntityController.class, Reference.MOD_ID + ".TileEntityController");
		GameRegistry.registerTileEntity(TileEntityEnergyOutlet.class, Reference.MOD_ID + ".TileEntityEnergyOutlet");
		GameRegistry.registerTileEntity(TileEntityItemBuffer.class, Reference.MOD_ID + ".TileEntityItemBuffer");
		GameRegistry.registerTileEntity(TileEntityFactoryDoor.class, Reference.MOD_ID + ".TileEntityFactoryDoor");
		
		//copies
		GameRegistry.registerTileEntity(TileEntityEnergyOutletFactory.class, Reference.MOD_ID + ".TileEntityEnergyOutletFactory");
		GameRegistry.registerTileEntity(TileEntityItemBufferFactory.class, Reference.MOD_ID + ".TileEntityItemBufferFactory");
	}

}
