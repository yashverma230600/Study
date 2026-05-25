package Super;


    public class Animal {
        int age = 5;

    
        
    }
    class Dog extends Animal {
        int age = 3;

        void display() {
            System.out.println("Age of Dog: " + age); // Refers to Dog's age
            System.out.println("Age of Animal: " + super.age); // Refers to Animal's age using super
        }
    }


class SuperTest {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.display();
    }
}
