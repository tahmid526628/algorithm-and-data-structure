/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readerwriterproblem;

/**
 *
 * @author tahmi
 */
import java.util.concurrent.Semaphore;

class ReaderWriter{

    static Semaphore mutex = new Semaphore(1);
    static Semaphore dbMutex = new Semaphore(1);
    static int readCount = 0;

    static class Read implements Runnable {
        @Override
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    dbMutex.acquire();
                }
                mutex.release();

                //enter to access database(critical section)
                System.out.println(Thread.currentThread().getName() + " is reading");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " has finished reading");

                //exit from critical section(database)
                mutex.acquire();
                readCount--;
                if(readCount == 0) {
                    dbMutex.release();
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                dbMutex.acquire();
                System.out.println(Thread.currentThread().getName() + " is writing");
                Thread.sleep(1500);
                System.out.println(Thread.currentThread().getName() + " has finished writing");
                dbMutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Read read = new Read();
        Write write = new Write();
        Thread t1 = new Thread(read);
        t1.setName("Reader 1");
        Thread t2 = new Thread(write);
        t2.setName("Writer 1");
        Thread t3 = new Thread(write);
        t3.setName("Writer 2");
        Thread t4 = new Thread(read);
        t4.setName("Reader 2");
        Thread t5 = new Thread(write);
        t5.setName("Writer 3");
        t1.start();
        t3.start();
        t2.start();
        t4.start();
        t5.start();
    }
}