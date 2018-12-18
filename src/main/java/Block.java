public class Block <T extends Organism>{
    private T being = null;
    private int x, y;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(T being){ this.being = being; }
    public T get(){ return this.being; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
}
