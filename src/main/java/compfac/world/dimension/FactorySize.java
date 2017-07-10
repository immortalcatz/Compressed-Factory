package compfac.world.dimension;

public class FactorySize {

	private int x;
	private int y;
	private int z;
	
	public FactorySize(int xSize, int ySize, int zSize, boolean isRealSize) {
		if(isRealSize) {
			this.x = xSize;
			this.y = ySize;
			this.z = zSize;
		} else {
			this.x = this.translateToRealSize(xSize);
			this.y = this.translateToRealSize(ySize);
			this.z = this.translateToRealSize(zSize);
		}
	}

	public FactorySize(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public FactorySize(int x){
		this.x = x;
		this.y = x;
		this.z = x;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getZ(){
		return z;
	}
	public boolean same(FactorySize facS) {
		if((x == facS.getX()) && (y == facS.getY()) && (z == facS.getZ())) return true;
		return false;
	}
	
	private int translateToRealSize(int size) {
		switch(size) {
		case 1:
			return 9;
		case 2:
			return 15;
		case 3:
			return 21;
		case 4:
			return 25;
		case 5:
			return 29;
		case 6:
			return 33;
		case 7:
			return 37;
		case 8:
			return 41;
		case 9:
			return 45;
		}
		return 0;
	}
}
