package sweeper;

import java.util.zip.CheckedOutputStream;

public class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs)
    {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }
    void start(){
        bombMap = new Matrix(Box.zero);
        for(int i =0; i<totalBombs;i++)
            placeBomb();
    }

    private void fixBombsCount()
    {
        int maxBomobs = Ranges.getSize().x * Ranges.getSize().y /2;
        if(totalBombs>maxBomobs)
            totalBombs = maxBomobs;
    }

    Box get (Coord coord)
    {
        return bombMap.get(coord);
    }

    private void placeBomb ()
    {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (bombMap.get(coord) == Box.bomb)
                continue;
            bombMap.set(coord, Box.bomb);
            incNumberAroundBomb (coord);
            break;
        }
    }

    private void incNumberAroundBomb (Coord coord){
        for(Coord around: Ranges.getCoordsAround(coord))
            if(bombMap.get(around)!= Box.bomb)
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
