package ConstructorCallingInSameClass;

public class constructorCalling {

    constructorCalling(){
        System.out.println("This is the default constructor.");
    }

    constructorCalling(int x){
        this();
        System.out.println("This is the parameterized constructor with value: " + x);
    }

    public static void main(String[] args) {
        new constructorCalling(5);
        System.out.println("This is the main method.");
    }
    
}
