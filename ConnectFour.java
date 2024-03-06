import java.util.Scanner;

public class ConnectFour {
    private char[][] board;
    private boolean gameActive;
    private char winner;

    // Constructor
    private ConnectFour() {
        board = new char[6][7];
        initializeBoard();
        gameActive = true;
        winner = '-';
    }

    // Initilize board with '-' as placeholders
    private void initializeBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Current player picks a column to insert a new coin
    private int pickColumn(char player, int column) {
        // Check for out of bounds
        if (column < 0 || column >= 7 || !gameActive) {
            System.out.println("Invalid move! Try again.");
            return -1;
        }

        // Find an empty spot in the column from the bottom
        int row = findEmptyRow(column);

        if (row == -1) {
            System.out.println("Column is full! Choose another column.");
            return -1;
        }

        // Place coin
        board[row][column] = player;

        // Check if game is over
        if (checkForWinner(row, column)) {
            gameActive = false;
            winner = player;
        } 
        else if (isBoardFull()) {
            gameActive = false;
            winner = '=';
        }

        return 0;
    }

    private int findEmptyRow(int column) {
        for (int i = 5; i >= 0; i--) {
            if (board[i][column] == '-') {
                return i;
            }
        }
        return -1; // Column is full
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkForWinner(int row, int column) {
        return checkHorizontal(row) || checkVertical(column) || checkDiagonal(row, column);
    }

    private boolean checkHorizontal(int row) {
        for (int i = 0; i <= 3; i++) {
            if (board[row][i] != '-' && board[row][i] == board[row][i + 1] && board[row][i] == board[row][i + 2] && board[row][i] == board[row][i + 3]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical(int column) {
        for (int i = 0; i <= 2; i++) {
            if (board[i][column] != '-' && board[i][column] == board[i + 1][column] && board[i][column] == board[i + 2][column] && board[i][column] == board[i + 3][column]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int row, int column) {
        return checkDiagonalDown(row, column) || checkDiagonalUp(row, column);
    }

    private boolean checkDiagonalDown(int row, int column) {
        for (int i = 0; i <= 3; i++) {
            if (column - i >= 0 && column + 3 - i <= 6 && row - i >= 0 && row + 3 - i <= 5) {
                if (board[row - i][column - i] != '-' && board[row - i][column - i] == board[row - i + 1][column - i + 1]
                        && board[row - i][column - i] == board[row - i + 2][column - i + 2] && board[row - i][column - i] == board[row - i + 3][column - i + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalUp(int row, int column) {
        for (int i = 0; i <= 3; i++) {
            if (column - i >= 0 && column + 3 - i <= 6 && row + i <= 5 && row - 3 + i >= 0) {
                if (board[row + i][column - i] != '-' && board[row + i][column - i] == board[row + i - 1][column - i + 1]
                        && board[row + i][column - i] == board[row + i - 2][column - i + 2] && board[row + i][column - i] == board[row + i - 3][column - i + 3]) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isPlaying(){
        return gameActive;
    }

    private char getWinner(){
        return winner;
    }

    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Beginning the game!");
        char P1 = 'b';
        char P2 = 'r';
        char activePlayer = P1;

        while (game.isPlaying()) {
            if (activePlayer == P1) {
                System.out.println("Player 1's move:");
                activePlayer = P2;
            } else {
                System.out.println("Player 2's move:");
                activePlayer = P1;
            }

            int move = userInput.nextInt();
            int result = game.pickColumn(activePlayer, move);

            while(result == -1 ){
                move = userInput.nextInt();
                result = game.pickColumn(activePlayer, move);
            }
            printBoard(game);
        }

        System.out.println("Winner is: " + game.getWinner());
        userInput.close();
    }

    // Visual to help for debugging
    private static void printBoard(ConnectFour game) {
        System.out.println("-------------");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(game.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------");
    }
}
