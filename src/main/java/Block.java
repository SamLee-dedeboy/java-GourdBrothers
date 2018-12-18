public class Block <T extends Organism>{
    private T being = null;
    public static int size = 80;
    private int x, y;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(T being){ this.being = being; }
    public T getBeing(){ return this.being; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
}
