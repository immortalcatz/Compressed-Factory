package compfac.world.dimension;

import compfac.ConfigHandler;
import compfac.Reference;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static void MainRegistry() {
		registerDimension();
	}

	public static final DimensionType FACTORYL1 = DimensionType.register("L1Factory", "_l1factory", Reference.l1FactoryDimensionID, DimensionTypeFactoryL1.class, false);
	public static final DimensionType FACTORYL2 = DimensionType.register("L2Factory", "_l3factory", Reference.l2FactoryDimensionID, DimensionTypeFactoryL2.class, false);
	public static final DimensionType FACTORYL3 = DimensionType.register("L3Factory", "_l3factory", Reference.l3FactoryDimensionID, DimensionTypeFactoryL3.class, false);
	//that last true is too keep the dimension loaded
	public static void registerDimension() {
		DimensionManager.registerDimension(Reference.l1FactoryDimensionID, FACTORYL1);
		DimensionManager.registerDimension(Reference.l2FactoryDimensionID, FACTORYL2);
		DimensionManager.registerDimension(Reference.l3FactoryDimensionID, FACTORYL3);
	}
}
