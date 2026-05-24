package StaticBlock;
/*What is a Static Block?

A static block is a special block of code that runs:

automatically
only once
when the class is loaded into memory
*/

class StaticBlock {
     int age=10;
    static {
        System.out.println("This is a static block.");
        System.out.println("This block runs only once when the class is loaded.");
    }

    public static void main(String[] args) {
        System.out.println("This is the main method.");
    }
}