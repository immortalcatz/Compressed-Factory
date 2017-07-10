package compfac;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static void init(File file) {
		config = new Configuration(file);
		syncConfig();
	}
	
	public static void syncConfig() {
		String category;
		
		category = "Dimension";
		config.addCustomCategoryComment(category, "Dimension Settings (the ids need to be diferent)");
		Reference.l1FactoryDimensionID = config.getInt("L1DimensionsId", category, 1701, 1000, 6000, "The id of the dimension that contains the level 1 factories.");
		Reference.l2FactoryDimensionID = config.getInt("L2DimensionsId", category, 1702, 1000, 6000, "The id of the dimension that contains the level 2 factories.");
		Reference.l3FactoryDimensionID = config.getInt("L3DimensionsId", category, 1703, 1000, 6000, "The id of the dimension that contains the level 3 factories.");
		
		category = "Blocks";
		config.addCustomCategoryComment(category, "Block characteristics");
		Reference.controllerBlockHardness = config.getFloat("ControllerBlockHardness", category, 4F, 1F, 5F, "The amount of time the controller block need to break (greater number = harder to break).");
		Reference.multiblockBlockHardness = config.getFloat("MultiblockBlockHardness", category, 1.5F, 1F, 5F, "The amount of time the blocks from the multiblock need to break (greater number = harder to break).");
		Reference.multiblockBlockResistence = config.getFloat("MultiblockBlockResistance", category, 10F, 5F, 20F, "The resistance the blocks from the multiblock have to explosions (greater number = greater resistance).");
		Reference.multiblockBlockHarvestLevel = config.getInt("MultiblockBlockHarvestLevel", category, 2, 0, 3, "The pickaxe level needed to harvest the block (0 - wood, 1 - stone, 2 - iron, 3 - diamond)");
		
		Reference.itemBufferInventorySize = config.getInt("ItemBufferInventorySize", category, 10, 3, 54, "The number of slots in the Item buffer inventory.");
		Reference.itemBufferStackLimit = config.getInt("ItemBufferStackLimit", category, 16, 5, 64, "The limit number of items each inventory slot can hold.");
		
		Reference.energyOutletCapacity = config.getInt("EnergyOutletCapacity", category, 10000, 10, 1000000000, "The capacity and max transfer rate of the Energy outlet");
		Reference.energyOutletMaxExtract = Reference.energyOutletCapacity;
		Reference.energyOutletMaxReceive = Reference.energyOutletCapacity;
		
		category = "MultiBlock";
		config.addCustomCategoryComment(category, "MultiBlock characteristics");
		Reference.energyToCompress = config.getInt("EnergyToCompress", category, 0, 0, 1000000000, "Energy needed to compress the space and create the compressed factory");
		Reference.energyToMaintainCompression = config.getInt("EnergyToMaintainCompression", category, 0, 0, 1000000000, "Energy needed to keep the space compressed");
		Reference.energyOutletMandatory = (Reference.energyToCompress != 0 || Reference.energyToMaintainCompression != 0);
		
		category = "Chat";
		config.addCustomCategoryComment(category, "Chat configurations");
		Reference.chatMessagesLvl  = config.getInt("ChatMessagesLvl", category, 1, -1, 1, "The chat level: -1 - minimal chat messages, 0 - reduced chat messages, 1 - all chat messages");
		config.save();
	}
}
