
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Random;
import static java.lang.System.exit;


public class BankWebApp {

    static Scanner input = new Scanner(System.in);
    static int selectedAction;
    static int pin;
    static Random random = new Random();

    static int accountNumber;
    static int bankIDNumber;
    static int checkSum;
    static String CCWithoutChecksum;
    static int[] CCBeforeCheckSum = new int[15];
    static String ccNum;
    static int selectedAction2;


    public static void showLoginScreen() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        selectedAction = input.nextInt();

        switch (selectedAction) {
            case 0:
                System.out.println("Bye!");
                return;
            case 1:
                Database.connect();
                Database.createNewTable();
                generateCreditCard();
                try {
                    Database.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                checkCredentials();
                break;
        }
    }

    public static void generateCreditCard() {
        bankIDNumber = 400_000;
        accountNumber = random.nextInt(900_000_000) + 100_000_000;
        pin = random.nextInt(9000) + 1000;

        String firstPart = Integer.toString(bankIDNumber);
        String secondPart = Integer.toString(accountNumber);

        CCWithoutChecksum = firstPart + secondPart;
        generateChecksum();
        ccNum = CCWithoutChecksum + checkSum;

        storeCreditCard(Long.valueOf(ccNum),pin);

        System.out.println("Your card has been created");
        System.out.println("Your card number: \n" + ccNum);
        System.out.println("Your card PIN: \n" + pin);
        try {
            Database.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateChecksum() {
        //this will split the card number (not including checksum) into separate digits
        for (int i = 0; i < CCWithoutChecksum.length(); i++) {
            CCBeforeCheckSum[i] = Character.getNumericValue(CCWithoutChecksum.charAt(i));
        }

        // Luhns Algorithm

        //1st step: Multiply even indexes by 2

        for (int i = 0; i < CCWithoutChecksum.length(); i++) {
            if ( i % 2 == 0) {
                CCBeforeCheckSum[i] = CCBeforeCheckSum[i] * 2;
            }
        }

        //2nd step: Subtract 9 from elements greater than 9
        for (int i = 0; i < CCWithoutChecksum.length(); i++) {
            if (CCBeforeCheckSum[i] > 9) {
                CCBeforeCheckSum[i] = CCBeforeCheckSum[i] - 9;
            }
        }

        //3rd step: Find sum of all elements in array
        int sum = 0;
        for (int number:CCBeforeCheckSum) {
            sum += number;
        }

        //4th step: Assign value to checkSum
        if (sum % 10 == 0) {
            checkSum = 0;
        } else {
            checkSum = 10 - (sum % 10);
        }

    }


    public static void storeCreditCard(long creditCardNumber, int pin) {
        Database.insert(creditCardNumber,pin);
    }



    public static void checkCredentials() {
            System.out.println("Enter your card number:");
            long userCN = input.nextLong();
            System.out.println("Enter your PIN:");
            long userPin = input.nextLong();

            Database.connect();
            String sql = "SELECT pin FROM card WHERE number =" + userCN;
            try (Statement stmt1 = Database.conn.createStatement();
                 ResultSet rs    = stmt1.executeQuery(sql)) {
                rs.next();
                if (rs.getInt("pin") == userPin) {
                    System.out.println("You have successfully logged in!");
                    showAccountDetails();

                } else {
                    System.out.println("Wrong card number or PIN!");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Wrong card number or PIN!");

            } finally {
                try {
                    Database.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }

    public static void showAccountDetails() {
            do {
                System.out.println("1. Balance");
                System.out.println("2. Log out");
                System.out.println("0. Exit");
                selectedAction2 = input.nextInt();

                switch (selectedAction2) {
                    case 0:
                        exit(0);
                        return;
                    case 1:
                        System.out.println("Balance: 0");
                        break;
                    case 2:
                        System.out.println("You have successfully logged out!");
                        break;
                }
            } while (selectedAction2 == 1);
    }

    }