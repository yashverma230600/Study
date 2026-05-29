# Custom Thread Pool — Explained Line by Line

## The Big Picture

Imagine a **Zomato kitchen** with only **3 cooks** but **10 orders** come in.
- Orders wait in a queue
- Each cook picks one order, finishes it, then picks the next
- Only 3 orders are being prepared at any time

That's exactly what this program does with threads.

---

## Code

```java
package ThreadPool;

import java.util.*;
import java.util.concurrent.*;

public class CustomThreadPool {

    BlockingQueue<Runnable> queue;
    private List<Worker> workers;

    public CustomThreadPool(int poolSize) {
        queue = new LinkedBlockingQueue<>();
        workers = new ArrayList<>();

        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            worker.start();
            workers.add(worker);
        }
    }

    public void execute(Runnable task) {
        queue.offer(task);
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = queue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
```

---

## Line by Line Breakdown

### Setup

```java
package ThreadPool;
```
Folder name. Java uses this to organize files.

```java
import java.util.*;
import java.util.concurrent.*;
```
Importing tools — `List`, `ArrayList`, `BlockingQueue`, `LinkedBlockingQueue`.

---

### Variables

```java
BlockingQueue<Runnable> queue;
```
**The order counter.** A thread-safe queue that holds `Runnable` tasks (jobs to do). Multiple threads can use it without crashing.

```java
private List<Worker> workers;
```
**The list of cooks.** Keeps track of all worker threads.

---

### Constructor (Setting up the kitchen)

```java
public CustomThreadPool(int poolSize) {
```
"I want a kitchen with `poolSize` cooks."

```java
queue = new LinkedBlockingQueue<>();
```
Create the order counter. Unlimited space — orders keep piling up.

```java
workers = new ArrayList<>();
```
Empty list — we'll fill it with workers now.

```java
for (int i = 0; i < poolSize; i++) {
    Worker worker = new Worker();
    worker.start();
    workers.add(worker);
}
```
**Hire `poolSize` cooks and start them immediately.** Each `worker.start()` creates a new thread that begins running the `run()` method.

---

### Execute (Place an order)

```java
public void execute(Runnable task) {
    queue.offer(task);
}
```
**"Here's a new order, put it on the counter."**
- `offer()` adds the task to the queue
- Doesn't wait, doesn't block — just drops it and moves on

---

### Worker (The Cook)

```java
private class Worker extends Thread {
```
Each Worker IS a thread. It's an inner class living inside CustomThreadPool.

```java
public void run() {
    while (true) {
```
**Cook's life: work forever.** Keep looking for orders until interrupted.

```java
Runnable task = queue.take();
```
**THE MAGIC LINE.**
- If queue has a task → pick it up immediately
- If queue is EMPTY → **sleep here and wait** (no CPU wasted)
- `take()` is a blocking call — pauses the thread until something appears

```java
task.run();
```
**Do the work.** Execute the task on this worker thread.

```java
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    break;
}
```
**If someone says "stop!" (interrupts):** re-set interrupt flag, break out of loop → thread dies.

---

### Main (Customer placing 10 orders)

```java
CustomThreadPool pool = new CustomThreadPool(3);
```
**Open a kitchen with 3 cooks.** 3 threads alive and waiting.

```java
for (int i = 1; i <= 10; i++) {
```
**Place 10 orders.**

```java
int taskId = i;
```
Save the number — lambdas need effectively-final variables.

```java
pool.execute(() -> { ... });
```
Each order: print "executing", sleep 2 sec (fake work), print "finished".

---

## Runtime Visualization

```
Time 0s:  Thread-0 → Task 1, Thread-1 → Task 2, Thread-2 → Task 3
          (Tasks 4-10 waiting in queue)

Time 2s:  Thread-0 → Task 4, Thread-1 → Task 5, Thread-2 → Task 6
          (Tasks 7-10 waiting)

Time 4s:  Thread-0 → Task 7, Thread-1 → Task 8, Thread-2 → Task 9
          (Task 10 waiting)

Time 6s:  Thread-0 → Task 10
          Thread-1, Thread-2 → waiting (queue.take() blocks)

Time 8s:  All tasks done. Threads still alive, waiting forever.
```

---

## Key Components

| Component | Role | Real-life |
|-----------|------|-----------|
| `BlockingQueue` | Holds pending tasks | Order counter |
| `Worker` thread | Picks & executes tasks | Cook |
| `execute()` | Submits a task | Customer places order |
| `queue.take()` | Wait until task available | Cook waiting for next order |
| `queue.offer()` | Add task without waiting | Put order slip on counter |

---

## Why `BlockingQueue` instead of normal `Queue`?

| Feature | Normal Queue | BlockingQueue |
|---------|:---:|:---:|
| Thread-safe | No | Yes |
| Blocks when empty | No (returns null) | Yes (`take()` waits) |
| Multiple threads can use | Crashes/bugs | Safe |

---

## Important: This runs forever!

After all 10 tasks finish, worker threads **don't die** — they block on `queue.take()` forever.

**Fix:** Add a shutdown method:
```java
public void shutdown() {
    for (Worker worker : workers) {
        worker.interrupt();
    }
}
```

---

## Summary

```
Thread Pool = Fixed workers + Task Queue

Queue full of tasks → workers pick one by one
Workers idle → they sleep (no CPU waste)
New task arrives → sleeping worker wakes up

Think: 3 cooks, 10 orders, queue on the counter.
```
