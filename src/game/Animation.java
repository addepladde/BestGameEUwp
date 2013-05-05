package game;

import java.util.ArrayList;
import org.newdawn.slick.Image;
/** 
 * TAGET FRÅN THENEWBOSTON'S TUTORIAL
 * 
 * USE LIKE THIS
 * 
 * Animation a = new Animation();
 * 
 * a.addScene(pic1, 250); 	SÄg att pic1 och pic2 är våra grejer  när vi går till höger
 * a.addScene(pic2, 250);   då visas de 250 tidsenheter var
 * 
 * 
 * sen skriv en metod vid namn movieLoop typ - som kör hela tiden
 * @author addewp
 *
 */

public class Animation {

	private ArrayList<OneScene> scenes;
	private int sceneIndex;
	private long movieTime;
	private long totalTime;
	
	public Animation() {
		scenes = new ArrayList<OneScene>();
		totalTime = 0;
		start();
	}
	
	public void loop() {
		long startingTime = System.currentTimeMillis();
		long cumulatedTime = startingTime;
		
		while(cumulatedTime - startingTime < 5000) {
			long timePassed = System.currentTimeMillis() - cumulatedTime;
			cumulatedTime += timePassed;
			update(timePassed);
						
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class OneScene {
		Image sprite;
		long endTime;
		
		public OneScene(Image sprite, long endTime) {
			this.sprite = sprite;
			this.endTime = endTime;
		}
	}
	
	/**
	 * Add a scene to the animation
	 * @param image The image to be shown
	 * @param time How long the image will be shown
	 */
	public synchronized void addScene(Image image, long time) {
		totalTime += time;
		scenes.add(new OneScene(image, totalTime));
	}
	
	/**
	 * Start animation from beginning
	 */
	public synchronized void start() {
		movieTime = 0;
		sceneIndex = 0;
	}
	
	/**
	 * Change scenes
	 */
	public synchronized void update(long timePassed) {
		if(scenes.size() <= 1)
			return;
		
		movieTime += timePassed;
		if(movieTime >= totalTime) {
			start();
		}
		
		while(movieTime > getScene(sceneIndex).endTime) {
			sceneIndex++;
		}
		
		
	}
	
	public synchronized Image getImage() {
		if(scenes.size() == 0)
			return null;
		
		return getScene(sceneIndex).sprite;
	}
	
	private OneScene getScene(int index) {
		return (OneScene) scenes.get(index);
	}
}
