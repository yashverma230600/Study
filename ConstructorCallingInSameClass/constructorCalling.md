# Constructor Calling in Same Class

## Code

```java
public class constructorCalling {

    constructorCalling(){
        System.out.println("This is the default constructor.");
    }

    constructorCalling(int x){
        this();
        System.out.println("This is the parameterized constructor with value: " + x);
    }

    public static void main(String[] args) {
        constructorCalling obj = new constructorCalling(5);
        System.out.println("This is the main method.");
    }
}
```

---

## Execution Flow

1. `main()` starts
2. `new constructorCalling(5)` is called → invokes the **parameterized constructor** `constructorCalling(int x)`
3. Inside the parameterized constructor, `this()` is called → invokes the **default constructor** `constructorCalling()`
4. Default constructor prints: `This is the default constructor.`
5. Control returns to the parameterized constructor, prints: `This is the parameterized constructor with value: 5`
6. Control returns to `main()`, prints: `This is the main method.`

---

## Output

```
This is the default constructor.
This is the parameterized constructor with value: 5
This is the main method.
```

---

## Key Concept: `this()`

- `this()` calls **another constructor in the same class**.
- It must be the **first statement** in a constructor.
- Used to avoid code duplication (constructor chaining).

---

## Constructor Chaining Flow

```
main()
  └── constructorCalling(5)   [parameterized]
        └── this()            [calls default constructor]
              └── prints "default constructor"
        └── prints "parameterized constructor with value: 5"
  └── prints "main method"
```

---

## Rules for `this()`

| Rule | Description |
|------|-------------|
| Must be first statement | Cannot have any code before `this()` |
| Can only call one constructor | Cannot use `this()` twice in same constructor |
| Avoids recursion | Cannot create circular constructor calls |
| Same class only | Use `super()` for parent class constructors |
