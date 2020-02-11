/**
 * @author david benzaquen
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * The PidManager manages the use of PIDs to prevent multiple instances handing out the same pid the class is singleton
 */
public class PidManager {
    /**
     * minimum allocatable pid
     */
    private final int MIN_PID = 300;
    /**
     * maximum allocatable pid
     */
    private final int MAX_PID = 5000;
    /**
     * singleton helper
     */
    private static PidManager singletonInstance = null;
    /**
     * this is the data structure that  holds the pid it is thread safe
     */
    private ConcurrentHashMap<Integer, AtomicBoolean> pidMap;

    /**
     * constructor for Pidmanager calls {@link #allocate_pid()}
     */
    private PidManager(){
        allocate_map();
    }
    /**
     * This is a singleton helper class that insure that a instance is a singleton instance
     * @return a instance of the PidManager class
     */
    public static PidManager getInstance(){
        if (singletonInstance == null){
            singletonInstance = new PidManager();
        }

        return singletonInstance;
    }

    /**
     * creates and initializes a data structure for representing pids
     * @return 1 if unable to allocate map and -1 if unable to allocate map
     */
    private int allocate_map(){
        pidMap = new ConcurrentHashMap<Integer, AtomicBoolean>(MAX_PID-MIN_PID);
        try {
            for (int i = MIN_PID; i < MAX_PID; i++) {
                pidMap.put(i, new AtomicBoolean(false));

            }
        }catch (Exception e){
            return -1;
        }

        return 1;
    }

    /**
     * allocates and returns pid
     * @return returns the pid allocation is successful  or -1 if allocation fails
     */
    protected int allocate_pid(){
        for (Map.Entry<Integer,AtomicBoolean> entry : pidMap.entrySet()){
            if (entry.getValue().compareAndSet(false,true)){
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     *  releases pid
     * @param pid process pid
     */
    public void release_pid(int pid ){
        if (isAllocated(pid)){
            pidMap.put(pid, new AtomicBoolean(false));
        }
    }

    /**
     * prints out the current status of a pid
     * @param pid process pid
     */
    public void pidStatus (int pid){
        if (isAllocated(pid)) {
            System.out.println("Process "+pid+" is currently in use.");
        }
        else System.out.println("Process "+pid+" is not in use.");
    }



    /**
     *checks if a pid is allocated
     * @param pid the pid that allocation is checked for
     * @return True if the pid is allocated and false if is not allocated
     */
    public boolean isAllocated(int pid){
        if ((pidMap.get(pid)).get()){//get the value of the key and converts it to standard bool
            return true;
        }
        else return false;
    }


}
