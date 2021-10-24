
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class BankWebApp {

    static Scanner input = new Scanner(System.in);
    static int selectedAction;
    static int pin;
    static int counter = 0;
    static Random random = new Random();
    static long[][] acctNumbersAndPin = new long[2][15];
    static long creditCardNumber;
    static int accountNumber;
    static int bankIDNumber;
    static int checkSum;
    static long CCWithoutChecksumNum;
    static String CCWithoutChecksum;
    static int[] CCBeforeCheckSum = new int[15];
    static String ccNum;


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
                generateCreditCard();
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
        //String lastPart = Integer.toString(checkSum);
        CCWithoutChecksum = firstPart + secondPart;
        generateChecksum();
        ccNum = CCWithoutChecksum + Integer.toString(checkSum);
        storeCreditCard(Long.valueOf(ccNum));

        System.out.println("Your card has been created");
        System.out.println("Your card number: \n" + ccNum);
        System.out.println("Your card PIN: \n" + pin);

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

        //4th step: Assign checksum to cc number
        if (sum % 10 == 0) {
            checkSum = 0;
        } else {
            checkSum = 10 - (sum % 10);
        }

    }


    public static void storeCreditCard(long creditCardNumber) {
        acctNumbersAndPin[0][counter] = creditCardNumber;
        acctNumbersAndPin[1][counter] = pin;
        ++counter;

        for (int i = 0; i < acctNumbersAndPin.length; i++) {
            System.out.println(Arrays.toString(acctNumbersAndPin[i]) + " ");
        }
    }



        public static void checkCredentials() {
            int counter2 = 0;
            System.out.println("Enter your card number:");
            long userCN = input.nextLong();
            System.out.println("Enter your PIN:");
            long userPin = input.nextLong();


            for (int i = 0; i < acctNumbersAndPin[0].length; i++) {
                if (acctNumbersAndPin[0][i] == userCN) {
                    if (acctNumbersAndPin[1][i] == userPin) {
                        System.out.println("You have successfully logged in!");
                        showAccountDetails();
                        break;
                    } else if (acctNumbersAndPin[1][i] != userPin) {
                        System.out.println("Wrong card number or PIN!");
                        break;

                    }
                } else if (acctNumbersAndPin[0][i] != userCN) {
                    ++counter2;
                    if (acctNumbersAndPin[0][i] != userCN && counter2 == acctNumbersAndPin[0].length) {
                        System.out.println("Wrong card number or PIN!");
                        break;
                    }
                }
            }
        }

        public static void showAccountDetails() {
            int selectedAction2;
            do {
                System.out.println("1. Balance");
                System.out.println("2. Log out");
                System.out.println("0. Exit");
                selectedAction2 = input.nextInt();

                switch (selectedAction2) {
                    case 0:
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