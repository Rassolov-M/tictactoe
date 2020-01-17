import java.io.DataOutput;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    /*Блок настроек игры
    *
    * */
    private static char[][] map; // двумерный массив символов, матрица игры.
    private static int SIZE = 3; // размерность поля.

    private static final char DOT_Empty = '●'; // Пустой символ свободное поле ●
    private static final char DOT_x = 'X'; // крестик
    private static final char DOT_0 = '0'; // нолик

    private static Scanner scanner  = new Scanner(System.in);//класс Scanner, для работы с пользовательским вводом.
    private static Random random    = new Random();

    public static void main(String[] args){
        initMap();
        printMap();

        while (true){

            hummanTrun(); // ход человеком
                if (isEndGame(DOT_x)){
                    break;
                }
            compucterTrun(); // ход компьютером
                if (isEndGame(DOT_0)){
                     break;
                }
        }
        System.out.println("Игра закончена!");
    }

    /*
    * Метод подготовки игрового поля
    * */

    private static void initMap(){
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                map[i][j] = DOT_Empty;
            }
        }
    }

    /*
     * Метод вывода игрового поля на экран
     * */

    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");

            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
       /**
       * Ход человеком
       */

        private static void hummanTrun(){
            int x, y;

            do {
                System.out.println("Введите координаты ячейки...");
                y = scanner.nextInt() - 1;
                x = scanner.nextInt() - 1;
            }while (isCallValide(x, y));
            map [y][x] = DOT_x;
        }

    /**
     * ход компуктером
     */

    private static void compucterTrun(){
        int x = -1;
        int y = -1;

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        }while (isCallValide(x, y));
        System.out.println("Компуктер выбрал ячейку " + (y + 1) + " " + (x + 1));
        map [y][x] = DOT_0;
    }

    /**
     * Метод валидации запрашиваемой яйчейки на корректность
        * @param - X признак по гаризонтали
        * @param - y признак по вертикали
        * @return boolean - признак валидности
        * */

        private static boolean isCallValide(int x, int y){
            boolean result = true;

            //проверка координаты
            if (x < 0 || x <= SIZE || y < 0 || y <= SIZE){
                result = false;
            }

            //заполненность ячейки
            if (map[y][x] != DOT_Empty){
                result = false;
            }
            return result;
        }

    /**
     *Метод проверки игры на завершение
     * @param - playerSymbol сивол, которым играет текущий игрок
     * @return - boolean признак завершения игры
     */
    private static boolean isEndGame (char playerSymbol){
        boolean result = false;

        printMap();

        if (checkWin(playerSymbol)){
            System.out.println("Победили " + playerSymbol);
            result = true;
        }

        if (isMapFull()){
            System.out.println("Ничья");
            result = true;
        }
        return result;
    }

    /**
     * Проверка на 100 -ю % заполненность поля
     * @return boolean признак оптимальности.
     */

    private static boolean isMapFull(){
        boolean result = true;
            for (int i = 0; i < SIZE; i++){
                for (int j = 0; j < SIZE; j++ ){
                    if (map[i][j] == DOT_Empty);
                        result = false;

                }
            }
            return result;
    }

    /**
     * Метод проверки выйгрышы
     * @param - playerSymbol Символ введёный пользователём
     * @return - boolean результат проверки
     */

    private static boolean checkWin(char playerSymbol){
        boolean result = false;

        if(
            (map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol) ||
            (map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol) ||
            (map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol) ||
            (map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol) ||
            (map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol) ||
            (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol) ||
            (map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol) ||
            (map[2][0] == playerSymbol && map[1][1] == playerSymbol && map[0][2] == playerSymbol)){

            result = true;
        }
        return result;
    }
}
