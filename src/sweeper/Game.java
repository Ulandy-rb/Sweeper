package sweeper;

import java.util.zip.CheckedOutputStream;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public void Start(){
        bomb.start();
        flag.start();
        state = GameState.played;
    }

    public GameState getState() {
        return state;
    }

    public Game(int Cols, int Rows, int Bombs)
    {
        Ranges.setSize(new Coord(Cols, Rows));
        bomb = new Bomb(Bombs);
        flag = new Flag();
    }

    public Box getBox(Coord coord){
        if(flag.get(coord)==Box.opened)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if(gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        if(state == GameState.played) return false;
        Start();
        return true;
    }

    private void checkWinner(){
        if(state == GameState.played)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                state = GameState.winner;
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)){
            case opened: setOpenedToClosedBoxesAroundNumber(coord);return;
            case flaged: return;
            case closed:
                switch (bomb.get(coord)){
                    case zero: openBoxesAround(coord);return;
                    case bomb: openBombs(coord); return;
                    default: flag.setOpenedToBox(coord); return;
                }
        }
    }

    private void openBombs(Coord bombs) {
        state = GameState.bombed;
        flag.setBombedToBox(bombs);
        for(Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.bomb)
                flag.setOpenedToClosedBombBox (coord);
            else
                flag.setNobombToFlagedSafeBox (coord);
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for(Coord around: Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver ()) return;
        flag.setToggleToBox (coord);

    }

    private void setOpenedToClosedBoxesAroundNumber (Coord coord)
    {
        if (bomb.get(coord) != Box.bomb)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.closed)
                        openBox(around);
    }
}
