/**
 * @author david benzaquen
 */

import java.util.Random;

public class Main {
    static PidManager pidManager = PidManager.getInstance();

    public static void main(String[] args) {

        int pid = getRandomNumberInRange(300,5000);
        System.out.println("Testing the status of pid "+pid);
        pidManager.pidStatus(pid);
        System.out.print("\n\n");

        /*
        allocate pid
         */
        pid = pidManager.allocate_pid();
        System.out.println("The pid "+pid+ " is allocated");
        pidManager.pidStatus(pid);
        System.out.print("\n\n");
        /*
        release pid
         */
        pidManager.release_pid(pid);
        System.out.println("The pid "+pid+" is released");
        pidManager.pidStatus(pid);


    }

    /**
     *  generates random number in range
     * @param min min range
     * @param max max range
     * @return random number in range
     */
     private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
