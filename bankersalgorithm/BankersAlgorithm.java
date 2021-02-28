/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankersalgorithm;

import java.util.Scanner;

/**
 *
 * @author tahmi
 */
public class BankersAlgorithm {

    /**
     * @param args the command line arguments
     */
    
    static boolean checkProcessDone(boolean[] safelyDone){
        for(boolean b: safelyDone){
            if(!b)
                return false;
        }
        return true;
    }
    
    static boolean checkProcessAlreadyDone(boolean[] safeSequence, int current){
        if(safeSequence[current])
            return true;
        return false;
    }
    
    static void findSafeSystem(int numberOfProcess, int numberOfResource, int[] available, int[][] remainingNeed, int[][] allocation, int[] safeSequence){
        int cnt = 0;
        boolean[] safeSequenceForCheck = new boolean[numberOfProcess];
        
        // maximum iteration is the number of process the system has
        for(int i=0; i<numberOfProcess; i++){
            for(int j=0; j<numberOfProcess; j++){
                boolean completelyDone = false;
                boolean[] safelyDone = new boolean[numberOfResource];
                
                if(!checkProcessAlreadyDone(safeSequenceForCheck, j)){
                    for(int k=0; k<numberOfResource; k++){
                        if(available[k] >= remainingNeed[j][k]){
                            safelyDone[k] = true;
                        }else{
                            safelyDone[k] = false;                        
                        }
                    }
                    
                    // available meet maxNeed
                    completelyDone = checkProcessDone(safelyDone);
                    if(completelyDone){
                        safeSequence[cnt] = j+1;
                        safeSequenceForCheck[j] = true;
                        cnt++;
                        for(int k=0; k<numberOfResource; k++){
                            available[k] += allocation[j][k]; // get back the resources
                        }
                    }else{
                        safeSequenceForCheck[j] = false;
                    } 
                }             
            }
            
            // all done then break
            if(checkProcessDone(safeSequenceForCheck)){
                break;
            }
            
//            for(boolean b : safeSequenceForCheck){
//                System.out.print(b + "\t");
//            }
//            System.out.print("\n");
        }
        
        // check system
        if(checkProcessDone(safeSequenceForCheck)){
            System.out.println("The system is currently in safe state");
            printSafeSequence(safeSequence, numberOfProcess);
        }else{
            System.out.println("Deadlock in the system");
        }
    }
    
    static void printSafeSequence(int[] safeSequence, int numberOfProcess){
        System.out.print("Safe Sequence: ");
        for(int i=0; i<numberOfProcess; i++){
            System.out.print("p" + safeSequence[i] + "\t");
        }
    }    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int numberOfProcess = scanner.nextInt();
        System.out.print("Enter number of resources: ");
        int numberOfResource = scanner.nextInt();
        int[] resources = new int[numberOfResource];
        
        int[][] allocation = new int [numberOfProcess][numberOfResource];
        int[][] maxNeed = new int[numberOfProcess][numberOfResource]; 
        int[][] remainingNeed = new int[numberOfProcess][numberOfResource]; // remaing need = max need - allocation
        int[] available = new int[numberOfProcess]; // available = total resourcess - current total allocation
        
        int[] safeSequence = new int[numberOfProcess];
        
        // input resource
        System.out.println("Enter the "+ numberOfResource +" resources in the system: ");
        for(int i=0; i<numberOfResource; i++){
            resources[i] = (scanner.hasNext() == true ? scanner.nextInt():null);
        }
        
        System.out.println();
        // input allocations
        for(int i=0; i<numberOfProcess; i++){
            System.out.println("Enter allocated resource of process: " + (i+1));
            for(int j=0; j<numberOfResource; j++){
                allocation[i][j] = (scanner.hasNext() == true ? scanner.nextInt():null);
            }
        }

        System.out.println();
        // input max need
        for(int i=0; i<numberOfProcess; i++){
            System.out.println("Enter maximum resources needed of process: " + (i+1));
            for(int j=0; j<numberOfResource; j++){
                maxNeed[i][j] = (scanner.hasNext() == true ? scanner.nextInt():null);
            }
        }

        
        // calculate available
        for(int i=0; i<numberOfResource; i++){
            int total = 0;
            for(int j=0; j<numberOfProcess; j++){
                total += allocation[j][i];
            }
            available[i] = resources[i] - total;
        }
        
        // calculate remaining need
        for(int i=0; i<numberOfProcess; i++){
            for(int j=0; j<numberOfResource; j++){
                remainingNeed[i][j] = maxNeed[i][j] - allocation[i][j];
            }
        }
        
        findSafeSystem(numberOfProcess, numberOfResource, available, remainingNeed, allocation, safeSequence);
    }
}



//        allocation[0][0] = 1;
//        allocation[0][1] = 0;
//        allocation[0][2] = 0;
//
//        allocation[1][0] = 5;
//        allocation[1][1] = 1;
//        allocation[1][2] = 1;
//
//        allocation[2][0] = 2;
//        allocation[2][1] = 1;
//        allocation[2][2] = 1;
//
//        allocation[3][0] = 0;
//        allocation[3][1] = 0;
//        allocation[3][2] = 2;


//        maxNeed[0][0] = 3;
//        maxNeed[0][1] = 2;
//        maxNeed[0][2] = 2;
//        
//        maxNeed[1][0] = 6;
//        maxNeed[1][1] = 1;
//        maxNeed[1][2] = 3;
//        
//        maxNeed[2][0] = 3;
//        maxNeed[2][1] = 1;
//        maxNeed[2][2] = 4;
//        
//        maxNeed[3][0] = 4;
//        maxNeed[3][1] = 2;
//        maxNeed[3][2] = 2;