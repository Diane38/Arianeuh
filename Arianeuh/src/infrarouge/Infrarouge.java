package infrarouge;

import lejos.remote.ev3.RemoteRequestException;
import lejos.ev3.tools.EV3Console;


public class Infrarouge {
	
	private final static EV3Client camera = new EV3Client();
	
	public final static int X_MAX = 300, Y_MAX = 200;
	
	public final static int X_BUT1 = 20, X_BUT2 = 280; // Besoin d'être calibrer
	
	/**
//	 * The margin for error when trying to match 2 object positions.
//	 */
//	public final static int MOVE_TOLERENCE = 5;
//	/**
//	 * The top margin for one object being next to an other.
//	 */
//	public final static int NEXT_TO_UPPER_TOLLERENCE = 4;
//	/**
//	 *  The bottom margin for one object being next to an other.
//	 */
//	public final static int NEXT_TO_LOWER_TOLLERENCE = 2;
//	/**
//	 * Y axis position of yellow line.
//	 */
//	public final static int LINE_Y_YELLOW = 10;
//	/**
//	 * Y axis position of black line.
//	 */
//	public final static int LINE_Y_BLACK = 50;
//	/**
//	 * Y axis position of red line.
//	 */
//	public final static int LINE_Y_RED = 100;
//	/**
//	 * X axis position of green line.
//	 */
//	public final static int LINE_X_GREEN = 10;
//	/**
//	 * X axis position of black line.
//	 */
//	public final static int LINE_X_BLACK = 50;
//	/**
//	 * X axis position of blue line.
//	 */
//	public final static int LINE_X_BLUE = 100;
	
	private static int[][] objPos;
	
	private static int[][] objPosNew;
	
	private static int nbObjet = 11;
	private static int nbPaletHorsJeux = 0;
	
	
	//private static Objet[] allObjet = new Objet[11];
	
	//private static Palet[] paletTab = new Palet[9];
	
	//private static Robot other, me;
	
	
	
	/**
	 * Se connecte à la caméra pour récupérer les données lors de l'instanciation.
	 */
	public Infrarouge() {
		objPos = camera.getData();
	}
	
	
	/**
	 * Test si la partie est terminée. (le nombre de palets hors jeux est égal à 9).
	 * @return Vrais si la partie est terminée. Faux si non.
	 */
	public boolean gameOver() {
		if(nbPaletHorsJeux == 9)
			return true;
		
		return false;
	}
		
	
	// Maybe only update the number of objects at it's second call
	/**
	 * Retourne vraie si le nombre d'objets détecté par la caméra et inférieur au nombre d'objet précédemment stocké. Puis met à jour le nombre d'objets.
	 * @return Vrais si le nombre d'objets et inférieurs au nombre stocké en mémoire. Faux sinon.
	 */
	public static boolean isNbObjReduced() {
		objPosNew = camera.getData();
		if(objPosNew.length < nbObjet) {
			nbObjet = objPosNew.length;
			return true;
		}
		if(nbObjet < 12)
			nbObjet = objPosNew.length;
		return false;
	}
	
	// Maybe wait between this method call and the game over call to not end the game to early if the robots enter the goal zones.
	/**
	 * Met à jour le modèle avec l'information récupérée par la caméra.
	 */
	public static void updateModel() {
		objPos = camera.getData();
		int count = 0;
		for(int i=0;i<objPos.length;i++) {
			if(objPos[i][1] < X_BUT1 || objPos[i][1] > X_BUT2)
				count ++;
		}
		nbPaletHorsJeux = count;
		//EV3Console c = new EV3Console();
		//c.logMessage(null);
	}
	
	/**
	 * Retourne un tableau contenant les coordonnées de l'autre robot.
	 * @return int[]. Tableau contenant les coordonnées de l'autre robot. {x, y}
	 *//*
	public static int[] getOtherPos() {
		return new int[] {other.getX(), other.getY()};
	}	
	*/
	
	
	/**
	 * Retourne un tableau contenant les coordonnées de notre robot.
	 * @return int[]. Tableau contenant les coordonnées de notre robot. {x, y}
	 *//*
	public static int[] getMyPos() {
		return new int[] {me.getX(), me.getY()};
	}
	*/
	
	
	/**
	 * Retourne un tableau contenant les coordonnées du palet le plus proche.
	 * @return int[]. Tableau contenant les coordonnées du palet le plus proche. {x, y}
	 *//*
	public static int[] getClosestPaletPos() {
		Palet out = new Palet(300, 300);
		int distance = 300; // May need changing
		for(int i=0;i<paletTab.length;i++)
			if(paletTab[i].getDistance(me) < distance)
				out = paletTab[i];
		
		if(out.getX() > X_MAX || out.getY() > Y_MAX)
			throw new IllegalArgumentException();
		
		return new int[] {out.getX(), out.getY()};
	}
	*/
	
	
	
	/*
	public static boolean findMe(int[] tab) {
		if(tab.length != 4)
			throw new IllegalArgumentException("findMe() tab wrong size!");
		int yu, xu;
		if(tab[0] < 1000)  // 1000 being the value returned when when wall can not be seen.
			xu = tab[0];
		else
			xu = tab[2];
		if(tab[4] < 1000)
			yu = tab[3];
		else
			yu = tab[2];
		
		int idx = 0;
		int dif = (int)(Math.pow((allObjet[0].getX() - xu),2) + Math.pow((allObjet[0].getY() - yu), 2)); // Calculate sum of squared distances696 
		for(int i=1;i<allObjet.length;i++)
			if((Math.pow((allObjet[i].getX() - xu),2) + Math.pow((allObjet[i].getY() - yu), 2)) < dif) {
				dif = (int)(Math.pow((allObjet[i].getX() - xu),2) + Math.pow((allObjet[i].getY() - yu), 2));
				idx = i;
			}
		
		me = new Robot(allObjet[idx].getX(), allObjet[idx].getY(), true);
		
	}
	
	*/
	
	
	/*
	public void findRobots(int [][] tab) { // Need to deal with different sized tables.
		int idx = 0;
		for(int i=0;i<tab.length;i++) {
			Objet o = new Objet(tab[i][1], tab[i][2]);
			// Runs through object positions and determines if the object is still in the same position or has moved.
			if(allObjet[i].hasMoved(o))
			
			
		}
	}
	*/
	
	
	/**
	 * Returns the Robot whose x coordinate is the closest to the specified x value. Mainly used to find robot with the position of the coloured lines. NULL
	 * @param x Value of the x coordinate with which other object coordinates will be compared.
	 * @return The object that is the closest the specified x coordinates according to the x axis.
	 *//*
	public static Robot findClosestObjX(int x) {
		int smalest = Math.abs(allObjet[0].getX() - x);
		int idx = 0;
		for(int i=1;i<allObjet.length;i++)
			if(Math.abs(allObjet[i].getX() - x) < smalest) {
				smalest = Math.abs(allObjet[i].getX() - x);
				idx = i;
			}
				
		return allObjet[idx];

	}*/
	
	/**
	 * Returns the Robot whose y coordinate is the closest to the specified y value.  Mainly used to find robot with the position of the coloured lines.
	 * @param y Value of the y coordinate with which other object coordinates will be compared.
	 * @return The object that is the closest the specified y coordinates according to the y axis.
	 *//*
	public static Robot findClosestObjY(int y) {
		int smalest = Math.abs(allObjet[0].getX() - y);
		int idx = 0;
		for(int i=1;i<allObjet.length;i++)
			if(Math.abs(allObjet[i].getX() - y) < smalest)
			 {
				smalest = Math.abs(allObjet[i].getX() - y);
				idx = i;
			}
	
		return allObjet[idx];

	}*/




	public static void main(String[] args) {



	}

}
