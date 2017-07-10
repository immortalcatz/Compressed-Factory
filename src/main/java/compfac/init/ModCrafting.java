package compfac.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	
	public static void register(){
//		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.cheese), "CCC","CCC","CCC",'C', ModItems.cheese);
//		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.jar), " C ","GGG","CCC",'C',Blocks.COAL_BLOCK,'G', new ItemStack(Blocks.STAINED_GLASS,1,0));
		
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mbCasing), 		"ISI", "SIS", "ISI", 'I', Items.IRON_INGOT, 'S', new ItemStack(Blocks.STONE, 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mbController), 	"RCR", "CDC", "RCR", 'R', Items.REDSTONE, 'C', ModBlocks.mbCasing, 'D', Items.DIAMOND);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mbEnergyOutlet),"RCR", "CBC", "RCR", 'R', Items.REDSTONE, 'C', ModBlocks.mbCasing, 'B', Blocks.REDSTONE_BLOCK);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mbItemInput), 	"GCG", "CGC", " C ", 'G', Items.GOLD_INGOT, 'C', ModBlocks.mbCasing);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mbItemOutput),	" C ", "CGC", "GCG", 'G', Items.GOLD_INGOT, 'C', ModBlocks.mbCasing);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.mbFactoryDoor),	"RCG", "CDC", "ICL", 'R', Items.REDSTONE, 'C', ModBlocks.mbCasing, 'D', Items.DIAMOND, 'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT, 'L', new ItemStack(Items.DYE, 1, 4));
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.breakStoneDoor),	" DD", "DWD", "WD ", 'D', Items.DIAMOND, 'W', Items.STICK);
	}
}
