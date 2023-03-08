package com.crepes.butter.peanut;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Water extends Entity {

	public GameScene gameScene;
	
	public float posX;
	public float posY;
	
	public int squigglyCounter;
	
	public WaterDirection direction;
	public boolean running;
	public float speed;
	
	public BuildingBlock currentBlock;
	public boolean newBlock;
	
	public ArrayList<CachedWaterBlock> water;
	public CachedWaterBlock placeHolder;
	
	public Water(GameScene gameScene) {
		
		this.gameScene = gameScene;
		
		if(gameScene.emitter.typeIndex == 0) {
			
			direction = WaterDirection.LEFT;
			
			this.x = gameScene.emitter.x + 0.25f;
			this.sizeX = -0.125f;
			
		} else {
			
			direction = WaterDirection.RIGHT;
			
			this.x = gameScene.emitter.x + 0.75f;
			this.sizeX = 0.125f;
		}
		
		this.y = gameScene.emitter.y + 0.5f - 3f/32f;
		this.sizeY = 6f/32f;
		
		squigglyCounter = 0;
		
		newBlock = false;
		
		speed = 0.5f;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Water.png");
		this.sprite.setTexture(texture);
		
		water = new ArrayList<CachedWaterBlock>();
	}
	
	public void reset() {
		
		if(gameScene.emitter.typeIndex == 0) {
			
			direction = WaterDirection.LEFT;
			
			this.x = gameScene.emitter.x + 0.25f;
			this.sizeX = -0.125f;
			
		} else {
			
			direction = WaterDirection.RIGHT;
			
			this.x = gameScene.emitter.x + 0.75f;
			this.sizeX = 0.125f;
		}
		
		this.y = gameScene.emitter.y + 0.5f - 3f/32f;
		this.sizeY = 6f/32f;
		
		squigglyCounter = 0;
		
		newBlock = false;
		running  = false;
		
		water.clear();
	}
	
	public void cacheWaterBlock() {
		
		placeHolder = new CachedWaterBlock(x, y, sizeX, sizeY);
		gameScene.addActor(placeHolder);
		water.add(placeHolder);
	}
	
	public void changeDirection(WaterDirection direction) {
		
		posX = (posX) + (int) ((posX - (int) posX) * 32) / 32;
		posY = (posY) + (int) ((posY - (int) posY) * 32) / 32;
		
		sizeX = posX - x;
		sizeY = posY - y;
		
		cacheWaterBlock();
		
		switch(direction) {
		
		case UP:
			
			if(this.direction == WaterDirection.LEFT) {
				
				this.x = posX;
				this.y = posY - 6f/32f;
				
			} else {
				
				this.x = posX - 6f/32f;
				this.y = posY - 6f/32f;
			}
			
			this.sizeX = 6f/32f;
			this.sizeY = 6f/32f;
			
			break;
		
		case DOWN:
			
			if(this.direction == WaterDirection.LEFT) {
				
				this.x = posX;
				this.y = posY;
				
			} else {
				
				this.x = posX - 6f/32f;
				this.y = posY;
			}
			
			this.sizeX = 6f/32f;
			this.sizeY = -6f/32f;
			
			break;
			
		case LEFT:
			
			if(this.direction == WaterDirection.UP) {
				
				this.x = posX;
				this.y = posY - 6f/32f;
				
			} else {
				
				this.x = posX;
				this.y = posY;
			}
			
			this.sizeX = -6f/32f;
			this.sizeY = 6f/32f;
			
			break;
			
		case RIGHT:
			
			if(this.direction == WaterDirection.UP) {
				
				this.x = posX - 6f/32f;
				this.y = posY - 6f/32f;
				
			} else {
				
				this.x = posX - 6f/32f;
				this.y = posY;
			}
			
			this.sizeX = 6f/32f;
			this.sizeY = 6f/32f;
			
			break;
			
		default:
			break;
		}
		
		this.direction = direction;
	}
	
	@Override
	public void act(float delta) {
		
		if(!gameScene.isPaused()  && gameScene.isLevelStarted()) {
		
		posX = this.x + this.sizeX;
		posY = this.y + this.sizeY;
		
		if(posX >= 2 && posX <= 17 && posY >= 2 && posY <= 14) {
		
			if(gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)] != null && currentBlock != null)
				if(!gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)].equals(currentBlock)) {
				
					// && !gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)].equals(gameScene.emitter) TODO Why was this in here?
					
					newBlock = true;
					squigglyCounter = 0;
					
				} else {
					
					newBlock = false;	
				}
			
			currentBlock = gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)];
			
		} else {
			
			currentBlock = null;
		}
		
		if(currentBlock == null) {
			
			running = false;
			gameScene.setLevelEnded(true);
			
		} else if(currentBlock.beingReplaced) {
			
				running = false;
				gameScene.setLevelEnded(true);
		}
		
		if(running) {
			
			switch(direction) {
		
			case UP:
			
				this.sizeY += delta * speed;
				
				if(newBlock)
					if(!currentBlock.hasDownEntrance) {
						
						running = false;
						gameScene.setLevelEnded(true);
						
					} else {
						
						currentBlock.watered = true;
					}
				
				if(currentBlock.type != null)
					switch(currentBlock.type) {
					
					case HSQUIGGLY:
						
						if(squigglyCounter == 1 && posY >= currentBlock.y + 27f/32f) {
							
							posY = currentBlock.y + 27f/32f;
							
							changeDirection(WaterDirection.RIGHT);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 3 && posY >= currentBlock.y + 27f/32f) {
							
							posY = currentBlock.y + 27f/32f;
							
							changeDirection(WaterDirection.LEFT);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 5 && posY >= currentBlock.y + 19f/32f) {
							
							posY = currentBlock.y + 19f/32f;
							
							changeDirection(WaterDirection.RIGHT);
							squigglyCounter++;
						}
						
						break;
						
					case VSQUIGGLY:
						
						if(squigglyCounter == 0 && posY >= currentBlock.y + 14f/32f) {
							
							posY = currentBlock.y + 14f/32f;
							
							changeDirection(WaterDirection.LEFT);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 2 && posY >= currentBlock.y + 24f/32f) {
							
							posY = currentBlock.y + 24f/32f;
							
							changeDirection(WaterDirection.RIGHT);
							squigglyCounter++;
						}
						
						break;
						
					default:
						
						if(posY >= currentBlock.y + 0.5f + 3f/32f) {
							
							posY = currentBlock.y + 0.5f + 3f/32f;
							
							if(!currentBlock.hasUpExit) {
								
								if(currentBlock.hasLeftExit)
									changeDirection(WaterDirection.LEFT);
								else
									changeDirection(WaterDirection.RIGHT);
							}
						}
						
						break;
					}
			
				break;
		
			case DOWN:
			
				this.sizeY -= delta * speed;
				
				if(newBlock)
					if(!currentBlock.hasUpEntrance) {
						
						running = false;
						gameScene.setLevelEnded(true);
						
					} else {
						
						currentBlock.watered = true;
					}
				
				if(currentBlock.type != null)
					switch(currentBlock.type) {
					
					case HSQUIGGLY:
						
						if(squigglyCounter == 1 && posY <= currentBlock.y + 5f/32f) {
							
							posY = currentBlock.y + 5f/32f;
							
							changeDirection(WaterDirection.LEFT);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 3 && posY <= currentBlock.y + 5f/32f) {
							
							posY = currentBlock.y + 5f/32f;
							
							changeDirection(WaterDirection.RIGHT);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 5 && posY <= currentBlock.y + 13f/32f) {
							
							posY = currentBlock.y + 13f/32f;
							
							changeDirection(WaterDirection.LEFT);
							squigglyCounter++;
						}
						
						break;
						
					case VSQUIGGLY:
						
						if(squigglyCounter == 0 && posY <= currentBlock.y + 18f/32f) {
							
							posY = currentBlock.y + 18f/32f;
							
							changeDirection(WaterDirection.LEFT);
							squigglyCounter--;
						}
						
						if(squigglyCounter == -2 && posY <= currentBlock.y + 8f/32f) {
							
							posY = currentBlock.y + 8f/32f;
							
							changeDirection(WaterDirection.RIGHT);
							squigglyCounter--;
						}
						
						break;
						
					default:
						
						if(posY <= currentBlock.y + 0.5f - 3f/32f) {
							
							posY = currentBlock.y + 0.5f - 3f/32f;
							
							if(!currentBlock.hasDownExit) {
								
								if(currentBlock.hasLeftExit)
									changeDirection(WaterDirection.LEFT);
								else
									changeDirection(WaterDirection.RIGHT);
							}
						}
						
						break;
					}
				
				break;
			
			case LEFT:
			
				this.sizeX -= delta * speed;
				
				if(newBlock)
					if(!currentBlock.hasRightEntrance) {
						
						running = false;
						gameScene.setLevelEnded(true);
						
					} else {
						
						currentBlock.watered = true;
					}
				
				if(currentBlock.type != null)
					switch(currentBlock.type) {
					
					case HSQUIGGLY:
						
						if(squigglyCounter == 0 && posX <= currentBlock.x + 21f/32f) {
							
							posX = currentBlock.x + 21f/32f;
							
							changeDirection(WaterDirection.DOWN);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 2 && posX <= currentBlock.x + 13f/32f) {
							
							posX = currentBlock.x + 13f/32f;
							
							changeDirection(WaterDirection.UP);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 4 && posX <= currentBlock.x + 5f/32f) {
							
							posX = currentBlock.x + 5f/32f;
							
							changeDirection(WaterDirection.DOWN);
							squigglyCounter++;
						}
						
						break;
						
					case VSQUIGGLY:
						
						if(squigglyCounter == 1 && posX <= currentBlock.x + 4f/32f) {
							
							posX = currentBlock.x + 4f/32f;
							
							changeDirection(WaterDirection.UP);
							squigglyCounter++;
						}
						
						if(squigglyCounter == -1 && posX <= currentBlock.x + 4f/32f) {
							
							posX = currentBlock.x + 4f/32f;
							
							changeDirection(WaterDirection.DOWN);
							squigglyCounter--;
						}
						
						break;
						
					default:
						
						if(posX <= currentBlock.x + 0.5f - 3f/32f) {
							
							posX = currentBlock.x + 0.5f - 3f/32f;
							
							if(!currentBlock.hasLeftExit) {
								
								if(currentBlock.hasUpExit)
									changeDirection(WaterDirection.UP);
								else
									changeDirection(WaterDirection.DOWN);
							}
						}
						
						break;
					}
			
				break;
			
			case RIGHT:
			
				this.sizeX += delta * speed;
				
				if(newBlock)
					if(!currentBlock.hasLeftEntrance) {
						
						running = false;
						gameScene.setLevelEnded(true);
						
					} else {
						
						currentBlock.watered = true;
					}
				
				if(currentBlock.type != null)
					switch(currentBlock.type) {
					
					case HSQUIGGLY:
						
						if(squigglyCounter == 0 && posX >= currentBlock.x + 11f/32f) {
							
							posX = currentBlock.x + 11f/32f;
							
							changeDirection(WaterDirection.UP);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 2 && posX >= currentBlock.x + 19f/32f) {
							
							posX = currentBlock.x + 19f/32f;
							
							changeDirection(WaterDirection.DOWN);
							squigglyCounter++;
						}
						
						if(squigglyCounter == 4 && posX >= currentBlock.x + 27f/32f) {
							
							posX = currentBlock.x + 27f/32f;
							
							changeDirection(WaterDirection.UP);
							squigglyCounter++;
						}
						
						break;
						
					case VSQUIGGLY:
						
						if(squigglyCounter == 3 && posX >= currentBlock.x + 19f/32f) {
							
							posX = currentBlock.x + 19f/32f;
							
							changeDirection(WaterDirection.UP);
							squigglyCounter++;
						}
						
						
						if(squigglyCounter == -3 && posX >= currentBlock.x + 19f/32f) {
							
							posX = currentBlock.x + 19f/32f;
							
							changeDirection(WaterDirection.DOWN);
							squigglyCounter--;
						}
						break;
						
					default:
						
						if(posX >= currentBlock.x + 0.5f + 3f/32f) {
							
							posX = currentBlock.x + 0.5f + 3f/32f;
							
							if(!currentBlock.hasRightExit) {
								
								if(currentBlock.hasUpExit)
									changeDirection(WaterDirection.UP);
								else
									changeDirection(WaterDirection.DOWN);
								}
							}
						
						break;
					}
				
					break;
			
				default:
					break;	
				}
			
			for(CachedWaterBlock wb : water) {
				
				if(!wb.looped) {
					
					if((wb.x + wb.sizeX) > wb.x) {
						
						if((wb.y + wb.sizeY) > wb.y) {
							
							if(posX > wb.x && posX < wb.x + wb.sizeX && posY > wb.y && posY < wb.y + wb.sizeY) {
								gameScene.loopsManager.loops++;
								wb.looped = true;
							}
							
							} else {
								
								if(posX > wb.x && posX < wb.x + wb.sizeX && posY < wb.y && posY > wb.y + wb.sizeY) {
									gameScene.loopsManager.loops++;
									wb.looped = true;
								}
							}
						
					} else {
							
						if((wb.y + wb.sizeY) > wb.y) {
								
							if(posX < wb.x && posX > wb.x + wb.sizeX && posY > wb.y && posY < wb.y + wb.sizeY) {
								gameScene.loopsManager.loops++;
								wb.looped = true;
							
							} else {
								
								if(posX < wb.x && posX > wb.x + wb.sizeX && posY < wb.y && posY > wb.y + wb.sizeY) {
									gameScene.loopsManager.loops++;
									wb.looped = true;
								}
									}
								}
							}
						}
					}
				}
			}
		}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		batch.begin();
		
		for(CachedWaterBlock wb : water)
			batch.draw(wb.sprite, wb.getX(), wb.getY(), wb.getWidth(), wb.getHeight());
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
