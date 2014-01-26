package mapping;

public class Tile {
    private int id;
    private boolean passable;

    public Tile(int id){
        this.id=id;
    }
    public int getID(){ return id; }
    public String getIDtoString(){ return id < 10 ? "0"+id : ""+id; } //0 to 99 digits

    //setters
    public void setID(int id){
        this.id=id;
        setProperties();
    }
    public void setProperties(){
        switch(id){
            //specify passable objects
            case(0): passable=true;  break;
            case(5): passable=true; break;
            case(6): passable=true; break;
            case(7): passable=true; break;
            case(8): passable=true; break;
            case(9): passable=true; break;
            case(10): passable=true; break;
            case(11): passable=true; break;
            case(12): passable=true; break;
            case(16):passable=true; break;
            default: passable=false; break;
        }
    }
    //getters
    public boolean isPassable(){ return passable; }
}
