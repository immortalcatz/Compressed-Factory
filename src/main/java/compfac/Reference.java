package compfac;

public class Reference {

	public static final String MOD_ID = "compfac";
	public static final String NAME = "Compressed Factory";
	public static final String VERSION = "1.0";
	public static final String ACCEPTED_VERSIONS = "[1.10.2]";
	
	public static final String CLIENT_PROXY_CLASS = "compfac.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "compfac.proxy.ServerProxy";
	
	
	//
//	public static final int minCompresserDimension = 3;
//	public static final int maxCompresserDimension = 11;
//	public static final int minInteriorCompresserDimension = 1;
//	public static final int maxInteriorCompresserDimension = 9;
//	public static final int minFactoryDimension = 9;
	
	//this are all final in the way that they are only set in configHandler
	//and it should remain that way (if this are changed by accident it will be fun to debug :) oh boi it will)
	// default value = 45 (not customizable yet)
	public static int maxFactoryDimension = 45;
	
	// default value = 10
	public static int itemBufferInventorySize;
	// default value = 16
	public static int itemBufferStackLimit;
	
	// default value = 10000
	public static int energyOutletCapacity;
	// default value = 10000
	public static int energyOutletMaxReceive;
	// default value = 10000
	public static int energyOutletMaxExtract;
	
	// default value = 1701, 1702, 1703
	public static int l1FactoryDimensionID;
	public static int l2FactoryDimensionID;
	public static int l3FactoryDimensionID;
	
	// default value = 1.5F
	public static float multiblockBlockHardness;
	// default value = 4.0F
	public static float controllerBlockHardness;
	// default value = 10.0F
	public static float multiblockBlockResistence;
	// default value = 2 (iron)
	public static int multiblockBlockHarvestLevel;
	
	// default value = 0
	public static int energyToMaintainCompression;
	// default value = 0
	public static int energyToCompress;
	// default value = false
	public static boolean energyOutletMandatory;
	
	//-1 - minimal chat messages
	//0 - reduced chat messages
	//1 - all chat messages (default)
	public static int chatMessagesLvl;
	
	// default value = false
	public static boolean expertCasingRecipe;
}
