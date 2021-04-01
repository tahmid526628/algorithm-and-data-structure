/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author tahmi
 */

class Q {
    int itemCount = 1;
    boolean isValueSet = false;
    Queue<Integer> itemQueue = new LinkedList<Integer>();
    
    public synchronized void put(int item){
        while(isValueSet){
            try{
                wait();
            }catch(Exception e){}
        }
        System.out.println("Producer produce: " + item);
        itemQueue.add(item);
        itemCount += 1;
        if(itemCount > 10)
            isValueSet = true;
        notify();
    }
    
    public synchronized void get(){
        while(!isValueSet){
            try{
                wait();
            }catch(Exception e){}
        }
        System.out.println(itemQueue);
        System.out.println("Consumer consume: "+ itemQueue.remove());
        itemCount -= 1;
//        System.out.println(itemCount);
        if(itemCount <= 1){       
            System.out.println();
            isValueSet = false;
        }
        notify();
    }
}

class Producer implements Runnable{
    Q q;
    public Producer(Q q){
        this.q = q;
        Thread thread = new Thread(this, "Producer");
        thread.start();
    }
    
    public void run(){
        int i=1;
        while(true){
            q.put(i++);
            try{
                Thread.sleep(1500);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

class Consumer implements Runnable{
    Q q;
    public Consumer(Q q){
        this.q = q;
        Thread thread = new Thread(this, "Producer");
        thread.start();
    }
    
    public void run(){
        while(true){
            q.get();
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

public class ProducerConsumer {
    
    public static void main(String [] args) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
    }
}
