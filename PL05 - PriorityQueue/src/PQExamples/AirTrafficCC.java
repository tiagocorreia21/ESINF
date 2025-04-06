package PQExamples;

import Priority_queue.HeapPriorityQueue;
import Priority_queue.Entry;

public class AirTrafficCC {

    private HeapPriorityQueue<Integer, String> cc;
    int timeslot = 5;  // time slot allocated to land each plane

    public AirTrafficCC(Integer[] p, String[] f) {
        this.cc = new HeapPriorityQueue(p, f);
    }

    public String nextPlaneLanding() {
        Entry<Integer, String> e = cc.removeMin();
        return e.getValue();
    }

    public void addPlane2Queue(String id, Integer pr) {
        cc.insert(pr, id);
    }

    public Entry<Integer, String> clearPlane4Landing() {
        return cc.removeMin();
    }

    public Integer nrPlanesWaiting() {
        return cc.size();
    }

    public Integer testRemove() {
        Entry<Integer, String> e = cc.removeMin();
        return e.getKey();
    }
}
