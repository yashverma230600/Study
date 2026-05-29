package ThreadPool;

import java.util.*;
import java.util.concurrent.*;

// Custom Thread Pool — like a restaurant with fixed number of waiters (threads)
// No matter how many orders (tasks) come in, only 'poolSize' waiters work at a time
public class CustomThreadPool {

    // Task queue — like an order counter where tasks wait to be picked up
    BlockingQueue<Runnable> queue;
    // List of worker threads — our fixed set of waiters
    private List<Worker> workers;

    // Constructor — hire 'poolSize' number of waiters and start them
    public CustomThreadPool(int poolSize) {
        queue = new LinkedBlockingQueue<>(); // unlimited capacity queue
        workers = new ArrayList<>();

        // Create and start worker threads
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            worker.start(); // Each worker starts waiting for tasks
            workers.add(worker);
        }
    }

    // Submit a task to the pool — puts it in the queue for workers to pick up
    public void execute(Runnable task) {
        queue.offer(task); // Non-blocking add to queue
    }

    // Worker thread — each worker keeps picking tasks from the queue and executing them
    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) { // Runs forever, waiting for tasks
                try {
                    // queue.take() BLOCKS until a task is available
                    // Worker sleeps here if queue is empty (no CPU waste)
                    Runnable task = queue.take();
                    // Execute the task on this worker thread
                    task.run();
                } catch (InterruptedException e) {
                    // If interrupted, stop this worker gracefully
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }

    // Test class
    public class Main {

        public static void main(String[] args) {

            // Create a pool with 3 threads — only 3 tasks run at a time
            CustomThreadPool pool = new CustomThreadPool(3);

            // Submit 10 tasks — but only 3 run simultaneously
            // Remaining 7 wait in the queue
            for (int i = 1; i <= 10; i++) {

                int taskId = i; // Need final/effectively-final variable for lambda

                pool.execute(() -> {
                    // This runs on one of the 3 worker threads
                    System.out.println(
                            Thread.currentThread().getName()
                                    + " executing Task "
                                    + taskId);

                    try {
                        Thread.sleep(2000); // Simulate work (2 seconds)
                    } catch (Exception e) {
                    }

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " finished Task "
                                    + taskId);
                });
            }
        }
    }
}
