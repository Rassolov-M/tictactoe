import java.util.Random;
import java.util.Scanner;

public class X0Game {

    static char[][] map;
    static int SIZE = 3;
    static char DOT_X = 'X';
    static char DOT_0 = '0';
    static char DOT_EMPTY = '.';
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        initMap();
        printMap();
        while (true){
            humanTurn();
            printMap();
            if (checkWinLines(DOT_X)){
                System.out.println("Победил человек");
                break;
            }

            if (isFull()){
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();

            if (checkWinLines(DOT_0)){
                System.out.println("Победил искуственный интелект");
                break;
            }

            if (isFull()){
                System.out.println("Ничья");
                break;
            }
        }

    }

    public static void initMap(){
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap(){
        System.out.print("  ");
        for (int i = 1; i <= SIZE ; i++) {
            System.out.print(i +  " ");

        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isCelValidate (int y, int x){
        if (y < 0 || y >= SIZE || x < 0 || x >= SIZE){
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static void humanTurn(){
        int y,x;

        do {
            System.out.println("Введите кординаты кординаты по сторке и по столбцу");
            y = scanner.nextInt() -1;
            x = scanner.nextInt() -1;
        }while (!isCelValidate(y, x));
        map[y][x] = DOT_X;
    }

    public static void aiTurn(){
        int y,x;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCelValidate(i, j)){
                    map[i][j] = DOT_X;
                    if (checkWinLines(DOT_X)){
                        map[i][j] = DOT_0;
                        return;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        do {
            y = random.nextInt(SIZE);
            x = random.nextInt(SIZE);
        }while (!isCelValidate(y, x));
        System.out.println("Ход искуственного интелекта");
        map[y][x] = DOT_0;
    }

    public static boolean isFull(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWinLines(char symbol){

        if(map[0][0] == symbol && map[0][1] == symbol && map[0][2] == symbol){return true;}
        if(map[1][0] == symbol && map[1][1] == symbol && map[1][2] == symbol){return true;}
        if(map[2][0] == symbol && map[2][1] == symbol && map[2][2] == symbol){return true;}

        if(map[0][0] == symbol && map[1][0] == symbol && map[2][0] == symbol){return true;}
        if(map[0][1] == symbol && map[1][1] == symbol && map[1][2] == symbol){return true;}
        if(map[0][2] == symbol && map[1][2] == symbol && map[2][2] == symbol){return true;}

        if(map[0][0] == symbol && map[1][1] == symbol && map[2][2] == symbol){return true;}
        if(map[0][2] == symbol && map[1][1] == symbol && map[2][0] == symbol){return true;}

        return false;
    }

}
