
package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class ActivityQueue {
    private Queue<String> activityQueue;
    private static final int MAX_SIZE = 5;
    
    public ActivityQueue() {
        this.activityQueue = new LinkedList<>();
    }
    
    public void addActivity(String activity) {
        if (activityQueue.size() >= MAX_SIZE) {
            activityQueue.poll();
        }
        activityQueue.offer(activity);
    }
    
    public ArrayList<String> getActivitiesAsList() {
        return new ArrayList<>(activityQueue);
    }
    
    public int size() {
        return activityQueue.size();
    }
    
    public void clear() {
        activityQueue.clear();
    }
}
