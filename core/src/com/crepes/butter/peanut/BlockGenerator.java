package com.crepes.butter.peanut;

public class BlockGenerator {

	public static int index;
	
	public static BuildingBlock randomBlock;
	
	public static BuildingBlock generateRandomBlock() {
		
		index = (int) (Math.random() * 100 + 1);
			
		if(index <= 14) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.ULELBOW);
			return randomBlock;
		}
			
		if(index <= 28) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.URELBOW);
			return randomBlock;
		}
			
		if(index <= 42) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.DLELBOW);
			return randomBlock;
		}
			
		if(index <= 56) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.DRELBOW);
			return randomBlock;
		}
			
		if(index <= 70) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.HSTRAIGHT);
			return randomBlock;
		}
			
		if(index <= 84) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.VSTRAIGHT);
			return randomBlock;
		}
			
		if(index <= 92) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.CROSS);
			return randomBlock;
		}
		
		if(index <= 96) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.HSQUIGGLY);
			return randomBlock;
		}
			
		if(index <= 100) {
			
			randomBlock = new BuildingBlock(BuildingBlockType.VSQUIGGLY);
			return randomBlock;
		}
		
		return randomBlock;
	}
}
