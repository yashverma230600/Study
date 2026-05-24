# Static Block in Java

Think of a Java class like a **restaurant blueprint**.

Before customers (objects) come in, some setup must happen once for the whole restaurant:

- turn on lights
- prepare kitchen
- load menu

That "one-time setup" in Java is done using **static blocks / static initializers**.

---

## Static Variable First

A static thing belongs to the **class itself**, not to individual objects.

```java
class Shop {
    static String owner = "Yash";
}
```

`owner` is shared by all objects.

---

## What is a Static Block?

A static block is a special block of code that runs:

- automatically
- only once
- when the class is loaded into memory

### Syntax:

```java
static {
    // code
}
```

---

## Simple Example

```java
class Demo {

    static {
        System.out.println("Static block executed");
    }

    public static void main(String[] args) {
        System.out.println("Main method executed");
    }
}
```

**Output:**

```
Static block executed
Main method executed
```

### Why?

Because Java loads the class first → runs static block → then starts `main()`.

---

## Real-Life Analogy

Imagine opening Netflix app.

Before you use it:

- app loads settings
- connects to server
- checks login

That startup work is like a **static initializer**.

---

## Why Use Static Blocks?

Usually for:

- initializing static variables
- loading configuration
- database driver loading (older Java)
- one-time setup logic

### Example:

```java
class Config {

    static String url;

    static {
        url = "mysql://localhost:3306";
        System.out.println("Config loaded");
    }
}
```

---

## Important Characteristics

| Feature | Static Block |
|---------|-------------|
| Runs automatically | Yes |
| Runs once | Yes |
| Belongs to class | Yes |
| Executes before objects | Yes |
| Can access static variables | Yes |

---

## Multiple Static Blocks

Java runs them **top to bottom**.

```java
class Test {

    static {
        System.out.println("First");
    }

    static {
        System.out.println("Second");
    }

    public static void main(String[] args) {
        System.out.println("Main");
    }
}
```

**Output:**

```
First
Second
Main
```

---

## Static Block vs Constructor

| Static Block | Constructor |
|-------------|------------|
| Runs once | Runs every object creation |
| For class setup | For object setup |
| Executes before object exists | Executes after object creation |
| Uses `static` | No `static` |

---

## Easy Memory Trick

- **Static block** = class startup code
- **Constructor** = object startup code

---

## Interview-Level Understanding

When JVM loads a class:

1. Static variables get memory
2. Static blocks execute
3. `main()` runs
4. Objects may get created later

So static initialization happens at **class-loading time**.

---

## Tiny Advanced Note

This is also called a **static initializer** because it initializes class-level data.

```java
static int number;

static {
    number = 100;
}
```

The block initializes the static variable.

---

## One Very Important Rule

Static blocks **cannot** directly use non-static variables.

### Wrong:

```java
int age = 10;

static {
    System.out.println(age); // ERROR
}
```

Because non-static things belong to objects, and objects don't exist yet.
