import java.util.*;


class Process {
    int pid;
    int at;
    int bt;
    int priority;

    public Process(int pid, int at, int bt, int priority) {
        this.pid = pid;
        this.bt = bt;
        this.at = at;
        this.priority = priority;
    }
}

class SRTFPriorityScheduler {
    
    private Process[] processData;
    private int[] weightingTime;
    private int[] remainingTime;
    private int[] combinedWeights;
    private int totalTime;
    
    private int noOfProcess;
    
    private int ALPHA = 7;
    private int BETA = 9;

    public SRTFPriorityScheduler(Process[] processData, int noOfProcess) {
        this.processData = processData;
        this.noOfProcess = noOfProcess;
    }

    public void schedule() {

        remainingTime = new int[noOfProcess];
        combinedWeights = new int [noOfProcess];
        weightingTime = new int [noOfProcess];

        for(int i=0; i<noOfProcess; ++i) {
            remainingTime[i] = processData[i].bt;
            combinedWeights[i] = ALPHA*remainingTime[i] + BETA*processData[i].priority;
        }
        
		int processCompleted = 0;
        int time = 0;
        int minWeight = Integer.MAX_VALUE;

        int shortestProcess = 0;
        int finishTime;

        Boolean isFound = false;

        while(processCompleted != noOfProcess) {
            for(int i=0; i<noOfProcess; ++i) {
                 if((processData[i].at <= time) && (combinedWeights[i]<minWeight)
                    && remainingTime[i]>0 && combinedWeights[i] != -1) {
             			minWeight = combinedWeights[i];
                        shortestProcess = i;
                        isFound = true;
                }
            }
        	

			 if(isFound == false) {
                System.out.println("Process not found for time = " + time );
                time++;
                continue;
            }


            //aging 
            if(time % 10 == 0) {
        		for(int i=0; i<noOfProcess; ++i) {
        			if(i != shortestProcess) {
        				if(processData[i].priority > 1) {
        					processData[i].priority--;
        				}
        			}
        		}
        	}

            if(remainingTime[shortestProcess] != 0)
                remainingTime[shortestProcess]--;
            
        	if(remainingTime[shortestProcess] == 0)
                combinedWeights[shortestProcess] = -1;
            else 
                combinedWeights[shortestProcess] = ALPHA*remainingTime[shortestProcess] + BETA*processData[shortestProcess].priority;
            
            minWeight = combinedWeights[shortestProcess];

            if(minWeight == -1){
                minWeight = Integer.MAX_VALUE;
            }
            

            if(remainingTime[shortestProcess] == 0) {
                System.out.println("Process " + processData[shortestProcess].pid + " finished");

         		processCompleted++;

                finishTime = time+1;
                weightingTime[shortestProcess] = finishTime-processData[shortestProcess].bt - processData[shortestProcess].at;
                
                if(weightingTime[shortestProcess] < 0)
                    weightingTime[shortestProcess] = 0;
            }


            time++;
        }

        totalTime = time;
    }


    public void findAvgTime() {
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        for(int i=0; i<noOfProcess; ++i) {
            totalWaitingTime += weightingTime[i];
            totalTurnAroundTime += weightingTime[i] + processData[i].bt;
        }

        float avgTime = (float)(totalWaitingTime)/noOfProcess;
        float avgTurnTime = (float)(totalTurnAroundTime)/noOfProcess;
        float throwput = (float)(noOfProcess)/totalTime;

        System.out.println("Total waiting time is " + totalWaitingTime);
        System.out.println("Average waiting time of processes is " + avgTime);
        System.out.println("Average turnaround time of processes is " + avgTurnTime);
    	System.out.println("Average throughput time of processes is " + throwput);

    }

}

public class Scheduler {
    
    public static void main(String[] args) {
        System.out.println("Operating System Scheduler");

        int noOfProcess, pid, at, bt, priority;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total no of processes");
        noOfProcess = scanner.nextInt();
        
        Process processData[] = new Process[noOfProcess];

        for(int i=0; i<noOfProcess; ++i) {

            System.out.println(
                "Enter process id, arrival time, burst time and priority of process"
            );

            pid = scanner.nextInt();
            at = scanner.nextInt();
            bt = scanner.nextInt();
            priority = scanner.nextInt();

            Process newProcess = new Process(pid, at, bt, priority);
            processData[i] = newProcess;
        }
        
        SRTFPriorityScheduler sc = new SRTFPriorityScheduler(processData, noOfProcess);
        sc.schedule();
        sc.findAvgTime();
    }

}