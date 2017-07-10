package compfac.world.dimension;

public class FactoryBigChunk {
	
	private int jumpX = 0;
	private int originX = 0;// origin point is this: X 0 0 this is the x of chunks not blocks
							 //						  0 0 0
	private int originZ = 0;//						  0 0 0
	//im going to hardcode at the fault of better idea, max factories per level is gonna be 36
	// please close your eyes when seeing this class
	// cause thats the way it is prettier
	// nvm i fix :)
	
	//changed nr factories to id, cause this receives the id and translates to coordinates
	public FactoryBigChunk(int id) {
		//hardcode fix trial nr 1
		
//		bigSquare is the aglomerate of 4 factories
//		smallSquare is square number in the bigSquare
		int bigSquare = id/4;
		int smallSquare = id%4;
		int sideSize = 1;
		int layerNr = 0;
		
		// center square special case
		if(bigSquare == 0){
			originX = 0;
			originZ = 0;
			
			switch(smallSquare) {
			case 0: 
				originX -= 3;
				originZ -= 3;
				break;
			case 1:
				originZ -= 3;
				break;
			case 3:
				originX -= 3;
				break;
			}
			
//			System.out.printf("Originx: %d, Originz: %d %n", originX, originZ);
			return;
		}
		
		int totalLayerSquareCount=0;
		int currentLayerSquareCount = 0;
		int startLayerNumber = 1;
		while(bigSquare > totalLayerSquareCount) {
			startLayerNumber += totalLayerSquareCount;
			currentLayerSquareCount = sideSize * 4 + 4;
			totalLayerSquareCount += currentLayerSquareCount;
			sideSize += 2;
			layerNr++;
		}
		
		int xDiference = 0;
		int zDiference = 0;
		
		if(startLayerNumber + sideSize > bigSquare){
			xDiference +=  bigSquare - startLayerNumber;
		} else {
			startLayerNumber += sideSize-1;
			xDiference += sideSize-1;
			if(startLayerNumber + sideSize > bigSquare){
				zDiference +=  bigSquare - startLayerNumber;
			} else {
				startLayerNumber += sideSize-1;
				zDiference += sideSize-1;
				if(startLayerNumber + sideSize > bigSquare){
					xDiference -=  bigSquare - startLayerNumber;
				} else {
					startLayerNumber += sideSize-1;
					xDiference -= sideSize-1;
					if(startLayerNumber + sideSize > bigSquare){
						zDiference -=  bigSquare - startLayerNumber;
					} else {
						System.out.println("This is awkward :/");
					}
				}
			}
		}
		
		originX = -6 * layerNr + xDiference * 6;
		originZ = -6 * layerNr + zDiference * 6;
		
		switch(smallSquare) {
		case 0: 
			originX -= 3;
			originZ -= 3;
			break;
		case 1:
			originZ -= 3;
			break;
		case 3:
			originX -= 3;
			break;
		}
		
//		System.out.printf("Originx: %d, Originz: %d %n", originX, originZ);
		
//		if(id == 0){
//			originX = -3;
//			originZ = -3;
//		}
//		else if(id == 1){
//			originX = 0;
//			originZ = -3;
//		}
//		else if(id == 2){
//			originX = 0;
//			originZ = 0;
//		}
//		else if(id == 3){
//			originX = -3;
//			originZ = 0;
//		}
//		else if(id == 4){
//			originX = -6;
//			originZ = -6;
//		}
//		else if(id == 5){
//			originX = -3;
//			originZ = -6;
//		}
//		else if(id ==6){
//			originX = 0;
//			originZ = -6;
//		}
//		else if(id == 7){
//			originX = 3;
//			originZ = -6;
//		}
//		else if(id == 8){
//			originX = 3;
//			originZ = -3;
//		}
//		else if(id == 9){
//			originX = 3;
//			originZ = 0;
//		}
//		else if(id == 10){
//			originX = 3;
//			originZ = 3;
//		}
//		else if(id == 11){
//			originX = 0;
//			originZ = 3;
//		}
//		else if(id == 12){
//			originX = -3;
//			originZ = 3;
//		}
//		else if(id == 13){
//			originX = -6;
//			originZ = 3;
//		}
//		else if(id == 14){
//			originX = -6;
//			originZ = 0;
//		}
//		else if(id == 15){
//			originX = -6;
//			originZ = -3;
//		}
//		else if(id == 16){
//			originX = -9;
//			originZ = -9;
//		}
//		else if(id == 17){
//			originX = -6;
//			originZ = -9;
//		}
//		else if(id == 18){
//			originX = -3;
//			originZ = -9;
//		}
//		else if(id == 19){
//			originX = 0;
//			originZ = -9;
//		}
//		else if(id == 20){
//			originX = 3;
//			originZ = -9;
//		}
//		else if(id == 21){
//			originX = 6;
//			originZ = -9;
//		}
//		else if(id == 22){
//			originX = 6;
//			originZ = -6;
//		}
//		else if(id == 23){
//			originX = 6;
//			originZ = -3;
//		}
//		else if(id == 24){
//			originX = 6;
//			originZ = 0;
//		}
//		else if(id == 25){
//			originX = 6;
//			originZ = 3;
//		}
//		else if(id == 26){
//			originX = 6;
//			originZ = 6;
//		}
//		else if(id == 27){
//			originX = 3;
//			originZ = 6;
//		}
//		else if(id == 28){
//			originX = 0;
//			originZ = 6;
//		}
//		else if(id == 29){
//			originX = -3;
//			originZ = 6;
//		}
//		else if(id == 30){
//			originX = -6;
//			originZ = 6;
//		}
//		else if(id == 31){
//			originX = -9;
//			originZ = 6;
//		}
//		else if(id == 32){
//			originX = -9;
//			originZ = 3;
//		}
//		else if(id == 33){
//			originX = -9;
//			originZ = 0;
//		}
//		else if(id == 34){
//			originX = -9;
//			originZ = -3;
//		}
//		else if(id == 35){
//			originX = -9;
//			originZ = -6;
//		}
//		else{
//			System.out.println("Too much factories, cant handle");
//		}
		// i hope you had your eyes close
		// oh the shame
	}
	
	public int getX(){
		return this.originX;
	}
	public int getZ(){
		return this.originZ;
	}
	public int getXCord(){
		return this.originX*16;
	}
	public int getZCord(){
		return this.originZ*16;
	}
}
