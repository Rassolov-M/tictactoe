import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    /**
    *Блок настроек игры
    */
    private static char[][] map; // двумерный массив символов, матрица игры.
    private static int SIZE = 3; // размерность поля.

    private static final char DOT_Empty = '●'; // Пустой символ свободное поле ●
    private static final char DOT_x = 'X'; // крестик
    private static final char DOT_0 = '0'; // нолик

    private static final boolean SILLY_MODE     = false; //константа SILLY_MODE, режим глупого компуктера.
    private static final boolean SCORING_MODE   = true;

    private static Scanner scanner  = new Scanner(System.in);//класс Scanner, для работы с пользовательским вводом.
    private static Random random    = new Random();

    public static void main(String[] args){
        initMap();
        printMap();

        while (true){

            humanTrun(); // ход человеком
                if (isEndGame(DOT_x)){
                    break;
                }
            computerTrun(); // ход компьютером
                if (isEndGame(DOT_0)){
                     break;
                }
        }
        System.out.println("Игра закончена!");
    }

    /**
     * Метод проверки выйгрышы
     * @param  playerSymbol - Символ введёный пользователём
     * @return - boolean результат проверки
     */

    private static boolean checkWin(char playerSymbol){
        boolean result = false;

        if(checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)){
            result = true;
        }
        return result;
    }

    /**
     * проверка выйгрышных комбинацый по диаганали
     * @param playerSymbol - символ игрока
     * @return  флаг выйгрыша
     */
    private static boolean checkWinDiagonals(char playerSymbol){
        boolean leftRight, rightLeft, result;
        leftRight   = true;
        rightLeft   = true;
        result      = false;

        for (int i = 0; i < SIZE; i++){
            leftRight &= (map[i][i] == playerSymbol);
            // if(leftRight &= (map[i][i] == playerSymbol)){
            //   leftRight = true;
            //}
            //else {
            //   leftRight = false;
            //}

            rightLeft &= (map [SIZE - i - 1][i] == playerSymbol);

        }
        if (leftRight || rightLeft){
            result = true;
        }
        return result;
    }

    /**
     *  проверка выйгрыша по столбцам и линиям
     * @param  playerSymbol - символ игрока
     * @return флаг победы
     */
    private static boolean checkWinLines(char playerSymbol){
        boolean cols, rows, result;
        result = false;

        for (int col = 0; col < SIZE; col++){
            cols = true;
            rows = true;

            for (int row = 0; row < SIZE; row++){
                cols &= (map[col][row] == playerSymbol);
                rows &= (map[row][col] == playerSymbol);

                //Это условие после каждой проверки колонки и столбца
                //позволяет останавливать и выполнять дальнейшее, без проверки
                // всех остальных столбцов и строк

                if (cols || rows) {
                    result = true;
                    break;
                }
                if (result){
                    break;
                }
            }

        }
        return result;
    }

    /**
     * ход компуктером
     */
    private static void computerTrun(){
        int x = -1;
        int y = -1;

        if(SILLY_MODE) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            }
            while (!isCallValide(x, y));
        }
        else {
            //компуктер ищет свой символ
            //компуктер решает, можно ли продолжить последовательность для выйгрыша

            if(!SCORING_MODE){
                //маркер того что ход найден
                boolean moveFound = false;
                for(int i = 0; i < SIZE; i++){
                    for (int j = 0; j < SIZE; j++){
                        if (map[i][j] == DOT_Empty){
                            //проверяем направления
                            //лево верх
                            if (i - 1 >= 0 && j -1 >= 0 && map[i-1][j-1] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("LU");

                            }
                            //Вверх
                            else if (i -1 >= 0 && map[i-1][j] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("U");

                            }
                            //Право вверх
                            else if(i - 1 >= 0 && j + 1 < SIZE && map[i-1][j+1] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;System.out.println("RU");

                            }
                            //право
                            else if(j + 1 < SIZE && map[i][j+1] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("R");
                            }
                            //право низ
                            else if(i + 1 < SIZE && j + 1 < SIZE && map[i+1][j+1] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("RD");
                            }
                            //низ
                            else if (i +1 <SIZE && map[i+1][j] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("B");

                            }
                            //лево низ
                            else if (i + 1 <SIZE && j - 1 >= 0 && map[i+1][j-1] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("LD");

                            }
                            //лево
                            else if(j - 1 >= 0 && map[i][j-1] == DOT_0){

                                y = i;
                                x = j;
                                moveFound = true;
                                System.out.println("L");

                            }
                        }
                        //если ход найден прирываем внутренний цикл
                        if (moveFound){
                            break;
                        }
                    }
                    //если ход найден прирываем внешний цикл
                    if (moveFound){
                        break;
                    }
                }
            }
            //алгоритм с подсчётом очков
            else{
                int maxScoreFiledX = -1;
                int maxScoreFiledY = -1;
                int maxScore = 0;

                for (int i = 0; i < SIZE; i++){
                    for(int j = 0; j < SIZE; j++){
                        int filedScore = 0;

                        if( map[i][j] == DOT_Empty) {
                            //проверяем направления
                            //лево вверх
                            if (i - 1 >= 0 && j -1 >= 0 && map[i-1][j-1] == DOT_0) {
                                filedScore++;
                                System.out.println("LU");
                            }
                            //вверх
                            if (i -1 >= 0 && map[i-1][j] == DOT_0) {
                                filedScore++;
                            }
                            //право верх
                            if (i - 1 >= 0 && j + 1 < SIZE && map[i-1][j+1] == DOT_0) {
                                filedScore++;
                            }
                            //право
                            if (j + 1 < SIZE && map[i][j+1] == DOT_0) {
                                filedScore++;
                            }
                            //право низ
                            if (i + 1 < SIZE && j + 1 < SIZE && map[i+1][j+1] == DOT_0) {
                                filedScore++;
                            }
                            //низ
                            if (i + 1 < SIZE && map[i + 1][j] == DOT_0) {
                                filedScore++;
                            }
                            //лево низ
                            if (i + 1 < SIZE && j - 1 >= 0 && map[i + 1][j - 1] == DOT_0) {
                                filedScore++;
                            }
                            //лево
                            if (j - 1 >= 0 && map[i][j - 1] == DOT_0) {
                                filedScore++;
                            }
                        }
                        if (filedScore > maxScore){ //полученное кол-во очков больше чем максимальное
                            maxScore = filedScore; //перезаписываем макс.значение
                            maxScoreFiledX = j;
                            maxScoreFiledY = i;
                        }
                    }
                }
                //если в цикле найдена наилучшая клетка
                if (maxScoreFiledX != -1){
                    x = maxScoreFiledX;//координаты для максимального количества очков
                    y = maxScoreFiledY;//координаты для максимального количества очков
                }
            }

            //если ничего не нашли делаем глупый ход
            if (x == -1){
                do{
                    x = random.nextInt(SIZE);
                    y = random.nextInt(SIZE);
                }
                while (!isCallValide(x, y));
                System.out.println("RANDOM");
            }
                            /*for (int i = 0; i < SIZE; i++){
                                for (int j = 0; j < SIZE; j++ ){
                                    //проверяем клетки по направлениям
                                }
                            }*/
        }

        System.out.println("Компуктер выбрал ячейку " + (y + 1) + " " + (x + 1));
        map[y][x] = DOT_0;
    }

    /**
     * Ход человеком
     */
    private static void humanTrun(){
        int x, y;

        do {
            System.out.println("Введите координаты ячейки X Y");
            y = scanner.nextInt() - 1;
            x = scanner.nextInt() - 1;
        } while (!isCallValide(x, y));

        map[y][x] = DOT_x;
    }

    /**
     * Метод вывода игрового поля на экран
     */
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
     * Метод подготовки игрового поля
     */
    private static void initMap(){
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                map[i][j] = DOT_Empty;
            }
        }
    }

    /**
     * Проверка на 100 -ю % заполненность поля
     * @return boolean признак оптимальности.
     */
    private static boolean isMapFull(){
        boolean result = true;
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++ ){
                if (map[i][j] == DOT_Empty){
                    //пустая клетка найдена
                    result = false;
                    //выходим из внутреннего цикла
                    break;
                }
            }
                //выходим из внешнего цикла, если пустая клетка найдена
            if (!result){
                break;
            }
        }
        return result;
    }

    /**
     * Метод валидации запрашиваемой яйчейки на корректность
     * @param x - признак по гаризонтали
     * @param y - признак по вертикали
     * @return boolean - признак валидности
     * */
    private static boolean isCallValide(int x, int y){
        boolean result = true;

        //проверка координаты
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE){
            result = false;
        }

        //заполненность ячейки
//        if (map[y][x] != DOT_Empty){
//            result = false;
//        }
        return result;
    }

    /**
     *Метод проверки игры на завершение
     * @param  playerSymbol - сивол, которым играет текущий игрок
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
}
