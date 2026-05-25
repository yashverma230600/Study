# LRU Cache — Explained Like You're 10

## What is a Cache?

Imagine your **brain can only remember 3 phone numbers** at a time.

- When someone asks for a number you remember → instant answer (cache hit)
- When you don't remember → you have to look it up (cache miss)

A **cache** is a small, fast storage that keeps frequently used data handy.

---

## What is LRU?

**LRU = Least Recently Used**

When your brain is full (3 numbers max) and you need to remember a new one:
- You **forget the number you haven't used in the longest time**
- That's LRU — throw out the least recently used item

---

## Real-Life Example

You have a cache of size **2** (can hold only 2 items):

```
Step 1: put(1, 1)     → Cache: [1]
Step 2: put(2, 2)     → Cache: [2, 1]       (2 is most recent)
Step 3: get(1)        → Cache: [1, 2]       (1 moves to front, it was just used)
Step 4: put(3, 3)     → Cache is FULL! Remove least used (2)
                      → Cache: [3, 1]
Step 5: get(2)        → returns -1 (2 was evicted!)
```

---

## How Does This Code Work?

We use **two things together**:

### 1. Doubly Linked List (the order tracker)

Think of it like a **line of people**:
- Front of line = most recently used
- Back of line = least recently used
- When someone is used → they move to the front
- When cache is full → person at the back gets kicked out

```
HEAD ↔ [most recent] ↔ [older] ↔ [oldest] ↔ TAIL
```

### 2. HashMap (the fast finder)

A phonebook that tells you **exactly where** each person is standing in the line.
- Without it: you'd have to walk the whole line to find someone (slow)
- With it: instant lookup by key (fast)

---

## Code Breakdown — Step by Step

### Step 1: The Node (a person in line)

```java
class Node {
    int key;
    int val;
    Node prev;   // person behind me
    Node next;   // person in front of me
}
```

Each node knows who's before and after it.

---

### Step 2: Setup (the empty line)

```java
Node head = new Node(-1,-1);   // fake start marker
Node tail = new Node(-1,-1);   // fake end marker
head.next = tail;
tail.prev = head;
```

```
HEAD ↔ TAIL    (empty line, nobody in it yet)
```

---

### Step 3: Adding a Node (new person joins front of line)

```java
private void addNode(Node newNode) {
    Node temp = head.next;
    newNode.next = temp;
    newNode.prev = head;
    head.next = newNode;
    temp.prev = newNode;
}
```

Always adds right after HEAD (= most recently used position).

```
Before: HEAD ↔ A ↔ TAIL
After:  HEAD ↔ NEW ↔ A ↔ TAIL
```

---

### Step 4: Deleting a Node (removing someone from line)

```java
private void deleteNode(Node delNode) {
    Node delPrev = delNode.prev;
    Node delNext = delNode.next;
    delPrev.next = delNext;
    delNext.prev = delPrev;
}
```

Just connects the neighbors to each other, skipping the deleted node.

```
Before: A ↔ B ↔ C
Delete B: A ↔ C
```

---

### Step 5: GET (looking up a value)

```java
public int get(int key) {
    if (map contains key) {
        1. Find the node
        2. Delete it from current position
        3. Add it to front (it was just used!)
        4. Return the value
    }
    return -1;  // not found
}
```

**Key insight**: Every time you access something, it moves to the front.

---

### Step 6: PUT (adding/updating a value)

```java
public void put(int key, int value) {
    if (key already exists) {
        Remove the old node
    }
    if (cache is full) {
        Remove node before TAIL (= least recently used)
    }
    Add new node at front
    Update HashMap
}
```

---

## Visual Walkthrough

```
Cache capacity = 2

put(1,1):  HEAD ↔ [1] ↔ TAIL           Map: {1→Node1}
put(2,2):  HEAD ↔ [2] ↔ [1] ↔ TAIL     Map: {1→Node1, 2→Node2}
get(1):    HEAD ↔ [1] ↔ [2] ↔ TAIL     Map: {1→Node1, 2→Node2}  (1 moved to front)
put(3,3):  Cache full! Remove [2] (before TAIL)
           HEAD ↔ [3] ↔ [1] ↔ TAIL     Map: {1→Node1, 3→Node3}
get(2):    returns -1 (evicted!)
```

---

## Why This Combination?

| Operation | Linked List alone | HashMap alone | Both together |
|-----------|:-:|:-:|:-:|
| Find item | O(n) slow | O(1) fast | O(1) fast |
| Move to front | O(1) fast | Can't do | O(1) fast |
| Remove last | O(1) fast | Can't do | O(1) fast |

**Together = O(1) for everything!**

---

## Summary

```
LRU Cache = HashMap + Doubly Linked List

HashMap   → "Where is this key?" (instant lookup)
LinkedList → "What's the usage order?" (track recency)

Most recent → front (near HEAD)
Least recent → back (near TAIL)

Full? → kick out the one near TAIL
Used? → move to front near HEAD
```
