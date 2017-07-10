package compfac.world.dimension;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DimensionTypeFactoryL2 extends WorldProvider{
	
	@Override
	public void createBiomeProvider() {
		this.biomeProvider = new BiomeProviderSingle(Biomes.VOID);
		this.hasNoSky = true;
	}
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.FACTORYL2;
	}
		
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new FactoryChunkProvider(2, worldObj, worldObj.getSeed());
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return true;
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesXZShowFog(int par1, int par2) {
		return false;
	}

}
