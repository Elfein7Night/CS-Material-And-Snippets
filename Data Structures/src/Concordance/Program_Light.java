package Concordance;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

/*
 *
 * Same as Program.java, but without the slow javax.swing GUI
 *
 */


public class Program_Light {
    //-------------------- Change This If Needed --------------------//
    public static final String PROJECT_DIR = Paths.get("").toAbsolutePath() + "/Data Structures/src/Concordance/";
    //---------------------------------------------------------------//
    public static final String SHREK_MOVIE_SCRIPT = PROJECT_DIR + "DefaultInputFiles/shrekMovieScript.txt";
    public static final String HARRY_POTTER_BOOK_1 = PROJECT_DIR + "DefaultInputFiles/Harry Potter and the Philosopher's Stone.txt";
    public static final String LORD_OF_THE_RINGS = PROJECT_DIR + "DefaultInputFiles/lotr.txt";
    public static final String BIBLE_KING_JAMES_EDITION = PROJECT_DIR + "DefaultInputFiles/BibleKingJamesEdition.txt";
    public static final String DEFAULT_OUTPUT_FILE_PATH = PROJECT_DIR + "output.txt";

    private static ConcordanceProcessor concordanceProcessor;

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome To My Concordance Program!");
        init(scanner);
        int choice = -1;
        while (choice != 0) try {
            System.out.println("\n> Choose:");
            System.out.println("1) Search For A Specific Word");
            System.out.println("2) Create An Output File Again");
            System.out.println("3) Print The Results In Console (may not print all for very large outputs)");
            System.out.println("4) Process Another File");
            System.out.println("5) Change How The Output Looks");
            System.out.println("6) Toggle Automatic Output File Opening");
            System.out.println("7) Get Most Common Word");
            System.out.println("\n0) Exit Program");
            choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.println("Enter The Word To Search For:");
                    concordanceProcessor.search(scanner.next().toLowerCase());
                    break;
                case 2:
                    System.out.println("1) Save To Originally Chosen File");
                    System.out.println("2) Create A Different File");
                    switch (scanner.nextInt()) {
                        case 1:
                            concordanceProcessor.printToFile();
                            break;
                        case 2:
                            concordanceProcessor.printToFile(chooseOutputPath(scanner));
                            break;
                        default:
                            throw new Exception("Invalid Input!");
                    }
                    break;
                case 3:
                    System.out.print(concordanceProcessor);
                    break;
                case 4:
                    init(scanner);
                    break;
                case 5:
                    System.out.println("1) Print All Line Numbers In One Line After Each Word");
                    System.out.println("2) Print The Line Numbers With Line Breaks And Indentation");
                    switch (scanner.nextInt()) {
                        case 1:
                            MyLinkedList.breakOutputLine = false;
                            break;
                        case 2:
                            MyLinkedList.breakOutputLine = true;
                            break;
                        default:
                            System.out.println("Invalid Input!");
                    }
                    break;
                case 6:
                    System.out.println("1) Automatically Open The Outfile");
                    System.out.println("2) Don't Automatically Open The Outfile");
                    switch (scanner.nextInt()) {
                        case 1:
                            ConcordanceProcessor.autoOpenOutput = true;
                            break;
                        case 2:
                            ConcordanceProcessor.autoOpenOutput = false;
                            break;
                        default:
                            System.out.println("Invalid Input!");
                    }
                    break;
                case 7:
                    concordanceProcessor.printMostCommonWord();
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
        } catch (Exception exception) {
            System.out.println(exception.toString());
            scanner.nextLine(); //clear buffer
        }
    }

    private static void init(Scanner scanner) {
        boolean fileProcessed = false;
        while (!fileProcessed) try {
            System.out.println("\nPlease Choose Which File To Process:");
            System.out.println("1) Enter Your Own Text File Path/Name");
            System.out.println("2) Shrek Movie Script (1,653 lines)");
            System.out.println("3) Harry Potter and the Philosopher's Stone Book (6,065 lines)");
            System.out.println("4) The Lord of the Rings Books (48,722 lines)");
            System.out.println("5) Bible - King James Edition (100,182 lines)");
            String workingFilePath = null;
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("Enter the file Path/Name");
                    scanner.nextLine(); //clear buffer
                    workingFilePath = scanner.nextLine();
                    break;
                case 2:
                    workingFilePath = SHREK_MOVIE_SCRIPT;
                    break;
                case 3:
                    workingFilePath = HARRY_POTTER_BOOK_1;
                    break;
                case 4:
                    workingFilePath = LORD_OF_THE_RINGS;
                    break;
                case 5:
                    workingFilePath = BIBLE_KING_JAMES_EDITION;
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
            if (workingFilePath != null) {
                concordanceProcessor = new ConcordanceProcessor(new File(workingFilePath), chooseOutputPath(scanner));
                fileProcessed = true;
                concordanceProcessor.printToFile();
            }
        } catch (Exception exception) {
            System.out.println(exception.toString());
            scanner.nextLine(); //clear buffer
        }
    }

    private static String chooseOutputPath(Scanner scanner) throws Exception {
        System.out.println("Please Choose Output File:");
        System.out.println("1) Enter Your Own Output File Path/Name");
        System.out.println("2) Save To Default Output File");
        String outputPath;
        switch (scanner.nextInt()) {
            case 1:
                System.out.println("Enter the file Path/Name");
                scanner.nextLine(); //clear buffer
                outputPath = scanner.nextLine();
                break;
            case 2:
                outputPath = DEFAULT_OUTPUT_FILE_PATH;
                break;
            default:
                throw new Exception("Invalid Input!");
        }
        return outputPath;
    }
}
