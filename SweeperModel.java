import java.io.Serializable;

public class SweeperModel implements Serializable { 
    private int GRID_WIDTH;
    private int GRID_HEIGHT;
    private int NUM_OF_BOMBS;

    static final int TILE_WIDTH = 30;
    static final int TILE_HEIGHT = 30;

    public Tile[][] tileGrid;

    private boolean firstClick = true;

    public SweeperModel(String difficulty){
        setDifficulty(difficulty);

        tileGrid = new Tile[GRID_HEIGHT][GRID_WIDTH];

        for(int i = 0; i < GRID_HEIGHT; i++)
            for(int k = 0; k < GRID_WIDTH; k++)
                tileGrid[i][k] = new Tile(k,i);

        for(int i = 0; i < NUM_OF_BOMBS; i++){
            int randX;
            int randY;

            do{
            randX = (int)(Math.random()*(GRID_WIDTH));
            randY = (int)(Math.random()*(GRID_HEIGHT));
            }while(tileGrid[randY][randX].getBomb());

            tileGrid[randY][randX].setBomb(true);
        }
    }

    public void endGame(){
        for(Tile[] row : tileGrid)
            for(Tile t : row)
                t.setRevealed(true);
    }

    public int getFlagged(){
        int total = 0;
        for(Tile[] row : tileGrid)
            for(Tile t : row)
                if(t.getFlagged())
                    total++;
        return total;
    }

    public void setFirstClick(boolean firstClick){
        this.firstClick = firstClick;
    }
    public void setDifficulty(String difficulty){
        if(difficulty.equals("Easy")){
            GRID_HEIGHT = 8;
            GRID_WIDTH = 8;
            NUM_OF_BOMBS = 10;
        }
        else if(difficulty.equals("Medium")){
            GRID_HEIGHT = 16;
            GRID_WIDTH = 16;
            NUM_OF_BOMBS = 40;
        }
        else if(difficulty.equals("Hard")){
            GRID_HEIGHT = 16;
            GRID_WIDTH = 30;
            NUM_OF_BOMBS = 99;
        }
    }

    public boolean getFirstClick(){
        return firstClick;
    }
    public int getTileWidth(){
        return TILE_WIDTH;
    }
    public int getTileHeight(){
        return TILE_HEIGHT;
    }
    public Tile[][] getTileArray(){
        return tileGrid;
    }

    


}