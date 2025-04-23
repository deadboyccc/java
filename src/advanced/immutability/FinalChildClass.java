package advanced.immutability;

public class FinalChildClass extends FinalBaseClass {
    @Override
    protected void optionalMethod() {
        System.out.println("[Child:optionalMethod] extra stuff!");
        super.optionalMethod();
    }

}
