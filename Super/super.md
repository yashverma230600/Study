# `super` Keyword in Java

## Code

```java
public class Animal {
    int age = 5;
}

class Dog extends Animal {
    int age = 3;

    void display() {
        System.out.println("Age of Dog: " + age);          // Dog's age
        System.out.println("Age of Animal: " + super.age); // Animal's age
    }
}

class SuperTest {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.display();
    }
}
```

---

## Output

```
Age of Dog: 3
Age of Animal: 5
```

---

## Execution Flow

1. `main()` creates a `Dog` object
2. `Dog` extends `Animal` → Animal's constructor runs first, sets `age = 5`
3. Dog's own `age = 3` shadows the parent's `age`
4. `dog.display()` is called:
   - `age` → refers to Dog's `age` (3)
   - `super.age` → refers to Animal's `age` (5)

---

## What is `super`?

`super` is a reference to the **parent class**. It is used to:

| Usage | Example |
|-------|---------|
| Access parent's variable | `super.age` |
| Call parent's method | `super.methodName()` |
| Call parent's constructor | `super()` |

---

## Why Use `super` Here?

When child and parent have a **variable with the same name** (variable shadowing), `super` lets you access the parent's version.

```
Dog object memory:
├── Animal's age = 5   ← accessed via super.age
└── Dog's age = 3      ← accessed via age
```

---

## Key Rules

- `super` can only be used in a **subclass**
- `super()` (constructor call) must be the **first statement** in a constructor
- Cannot use `super` in a `static` context
