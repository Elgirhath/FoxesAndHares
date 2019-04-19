public class Fox extends Animal{
    @Override
    protected void setup() {
        System.out.println("The fox has been created!\n");
        Map map = Map.getInstance();
        System.out.println(map.size.x + "\n");
    }
}