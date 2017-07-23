package compfac.init;

import compfac.Reference;
import compfac.items.ItemBreakStoneDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

	public static Item breakStoneDoor;
	
	public static void init(){
		initItems();
		register();
	}
	
	private static void initItems(){
		breakStoneDoor = new ItemBreakStoneDoor();
	}
	
	private static void register(){
//		GameRegistry.register(breakStoneDoor);
	}
	
	public static void registerRenders(){
//		registerRender(breakStoneDoor);
	}
	
	private static void registerRender(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
