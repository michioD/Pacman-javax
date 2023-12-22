package ProjectPacman;


import java.util.ArrayList;

public class PacmanModel {

    private final String interiorWall = "#";
    private final String food = "-";
    private final String door = "d";
    private final String powerUp = "R";
    private final String pacman = "P";
    private String[][] board;
    private int boardWidth; // Array length
    private int boardHeight; // Number of arrays
    private PacPlayer pacmanEntity;
    private ArrayList<String> moves = new ArrayList<>();
    private String currentDirection = ""; // To store the current direction of Pac-Man
    private int beanAmount= 398;

    public PacmanModel(PacPlayer pacmanEntity) {
        this.pacmanEntity = pacmanEntity;
        this.pacmanEntity.setLives(3);
        this.pacmanEntity.setDirection(0);
        this.initBoard();
    }

    public void resetGame(){
        this.pacmanEntity.setLives(3);
        this.pacmanEntity.setDirection(0);
        this.initBoard();
    }
    
    public String lastMove() {
        if (!moves.isEmpty()) {
            return moves.get(moves.size() - 1); // Return the last move
        } else {
            return ""; // Return an empty string or a default value if no moves have been made
        }
    }

    public void move(String direction) {
        int x = pacmanEntity.getX();
        int y = pacmanEntity.getY();
        
        if (x == 14 && y == 0 && direction.equals("LEFT")) { // Check if Pac-Man is at the left tunnel entrance and if so run
            movePacmanTo(14, 27);
            return;
        }
        if (x == 14 && y == 28 - 1 && direction.equals("RIGHT")) { // Check if Pac-Man is at the right tunnel entrance and if so continue 
            movePacmanTo(14, 0);
            return;
        }

        if (!wallCollision(direction)) {
            currentDirection = direction; // Update current direction if no collision
            moves.add(direction); // Add the valid direction to the moves list
        }

        switch (currentDirection) {
            case "UP":
                if (!wallCollision("UP")) {
                    movePacmanTo(x - 1, y);
                }
                break;
            case "DOWN":
                if (!wallCollision("DOWN")) {
                    movePacmanTo(x + 1, y);
                }
                break;
            case "LEFT":
                if (!wallCollision("LEFT")) {
                    movePacmanTo(x, y - 1);
                }
                break;
            case "RIGHT":
                if (!wallCollision("RIGHT")) {
                    movePacmanTo(x, y + 1);
                }
                break;
        }
    }

    private void movePacmanTo(int newX, int newY) {
        int x = pacmanEntity.getX();
        int y = pacmanEntity.getY();
        board[x][y] = "."; // Clear the old position
        if(board[x][y]=="-"){
            pacmanEntity.setScore(1);
        }

        if(board[x][y]=="R"){
            pacmanEntity.setScore(5);
        }
        
        board[newX][newY] = pacman; // Move to the new position
        pacmanEntity.setX(newX);
        pacmanEntity.setY(newY);
    }


    // Gets the direction attribute of pacman to decide how to move
    public void movePacman(){
        int direction = pacmanEntity.getDirection(); 
        if (direction != 0){
            if (direction == 1) {
                move("RIGHT");
            } else if (direction == 2) {
                move("UP");
            } else if (direction==3){
                move("LEFT");
            } else if (direction==4){
                move("DOWN");
            }
        }
    }

    public boolean wallCollision(String direction) {
        int x = pacmanEntity.getX();
        int y = pacmanEntity.getY();

        // Check if the next move is valid based on the direction input
        if (direction.equals("UP") && !board[x - 1][y].equals(interiorWall) && !board[x - 1][y].equals(door)) {
            return false;
        } else if (direction.equals("DOWN") && !board[x + 1][y].equals(interiorWall) && !board[x + 1][y].equals(door)) {
            return false;
        } else if (direction.equals("LEFT") && !board[x][y - 1].equals(interiorWall) && !board[x][y - 1].equals(door)) {
            return false;
        } else if (direction.equals("RIGHT") && !board[x][y + 1].equals(interiorWall) && !board[x][y + 1].equals(door)) {
            return false;
        }
        return true;
    }

    
    public boolean checkWinCondition(){
        // if pacman score equal to amount of beans
        // Count how many beans in board i.e "-" symbol
        // returns true if pacMan has eaten all beans buddy. 
        if(pacmanEntity.getScore()==((beanAmount))){
            return true;
        }
        return false;
    }

    
    public boolean checkLossCondition(){
        return false;
    }

    public String getStatus(int i, int j) {
        return board[i][j];
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

  public  void initBoard(){    
        board = new String[][] {
        // "#" = wall, "-" = Pellet, "." = empty, "d" = door， "R" ＝ Power up
        {"#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#"},
        {"#","-","-","-","-","-","-","-","-","-","-","-","-","#","#","-","-","-","-","-","-","-","-","-","-","-","-","#"},
        {"#","-","#","#","#","#","-","#","#","#","#","#","-","#","#","-","#","#","#","#","#","-","#","#","#","#","-","#"},
        {"#","R","#","#","#","#","-","#","#","#","#","#","-","#","#","-","#","#","#","#","#","-","#","#","#","#","R","#"},
        {"#","-","#","#","#","#","-","#","#","#","#","#","-","#","#","-","#","#","#","#","#","-","#","#","#","#","-","#"},
        {"#","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","#"},
        {"#","-","#","#","#","#","-","#","#","-","#","#","#","#","#","#","#","#","-","#","#","-","#","#","#","#","-","#"},
        {"#","-","#","#","#","#","-","#","#","-","#","#","#","#","#","#","#","#","-","#","#","-","#","#","#","#","-","#"},
        {"#","-","-","-","-","-","-","#","#","-","-","-","-","#","#","-","-","-","-","#","#","-","-","-","-","-","-","#"},
        {"#","#","#","#","#","#","-","#","#","#","#","#",".","#","#",".","#","#","#","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#","#","#","#",".","#","#",".","#","#","#","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".",".",".",".",".",".",".",".",".",".","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".","#","#","#","d","d","#","#","#",".","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".","#",".",".",".",".",".",".","#",".","#","#","-","#","#","#","#","#","#"},
        {".",".",".",".",".",".","-",".",".",".","#",".",".",".",".",".",".","#",".",".",".","-",".",".",".",".",".","."},
        {"#","#","#","#","#","#","-","#","#",".","#",".",".",".",".",".",".","#",".","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".","#","#","#","#","#","#","#","#",".","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".",".",".",".",".",".",".",".",".",".","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".","#","#","#","#","#","#","#","#",".","#","#","-","#","#","#","#","#","#"},
        {"#","#","#","#","#","#","-","#","#",".","#","#","#","#","#","#","#","#",".","#","#","-","#","#","#","#","#","#"},
        {"#","-","-","-","-","-","-","-","-","-","-","-","-","#","#","-","-","-","-","-","-","-","-","-","-","-","-","#"},
        {"#","-","#","#","#","#","-","#","#","#","#","#","-","#","#","-","#","#","#","#","#","-","#","#","#","#","-","#"},
        {"#","-","#","#","#","#","-","#","#","#","#","#","-","#","#","-","#","#","#","#","#","-","#","#","#","#","-","#"},
        {"#","R","-","-","#","#","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","#","#","-","-","R","#"},
        {"#","#","#","-","#","#","-","#","#","-","#","#","#","#","#","#","#","#","-","#","#","-","#","#","-","#","#","#"},
        {"#","#","#","-","#","#","-","#","#","-","#","#","#","#","#","#","#","#","-","#","#","-","#","#","-","#","#","#"},
        {"#","-","-","-","-","-","-","#","#","-","-","-","-","#","#","-","-","-","-","#","#","-","-","-","-","-","-","#"},
        {"#","-","#","#","#","#","#","#","#","#","#","#","-","#","#","-","#","#","#","#","#","#","#","#","#","#","-","#"},
        {"#","-","#","#","#","#","#","#","#","#","#","#","-","#","#","-","#","#","#","#","#","#","#","#","#","#","-","#"},
        {"#","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","#"},
        {"#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#","#"}};
        
        boardWidth = board[0].length;
        boardHeight = board.length;
        // Pacman should be placed at the bottom or top corner at each start of the game. 
        board[boardWidth-2][1]=pacman;
        // movePacmanTo(boardWidth-2, 1);
        pacmanEntity.setX(boardWidth-2);
        pacmanEntity.setY(1);
    }}