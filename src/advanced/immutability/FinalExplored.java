package advanced.immutability;

public class FinalExplored {
    public static void main(String[] args) {
        FinalBaseClass parent = new FinalBaseClass();
        FinalChildClass child = new FinalChildClass();

        FinalBaseClass childRefToAsBase = new FinalChildClass();

        System.out.println("_".repeat(20));
        parent.recommendedMethod();
        System.out.println("_".repeat(20));
        childRefToAsBase.recommendedMethod();
        System.out.println("_".repeat(20));
        child.recommendedMethod();

    }

}
