package compfac.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class EasyTeleporter extends Teleporter{

	public EasyTeleporter(WorldServer worldIn) {
		super(worldIn);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
	}
	
	@Override
	public void removeStalePortalLocations(long worldTime) {
	}
	
}
