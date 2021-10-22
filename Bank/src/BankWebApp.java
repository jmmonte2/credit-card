
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class BankWebApp {

    static Scanner input = new Scanner(System.in);
    static int selectedAction;
    static int pin;
    static int counter = 0;
    static Random random = new Random();
    static long[][] acctNumbersAndPin = new long[2][100];
    static long creditCardNumber;
    static int accountNumber;
    static int bankIDNumber;
    static int checkSum;

    public static void generateCreditCard() {
        bankIDNumber = 400_000;
        accountNumber = random.nextInt(900_000_000) + 100_000_000;
        checkSum = random.nextInt(10);
        pin = random.nextInt(9000) + 1000;

        String firstPart = Integer.toString(bankIDNumber);
        String secondPart = Integer.toString(accountNumber);
        String lastPart = Integer.toString(checkSum);
        String creditCardNum = firstPart + secondPart + lastPart;

        creditCardNumber = Long.valueOf(creditCardNum);
        System.out.println("Your card has been created");
        System.out.println("Your card number: \n" + creditCardNumber);
        System.out.println("Your card PIN: \n" + pin);

    }

    public static void storeCreditCard(long creditCardNumber) {
        acctNumbersAndPin[0][counter] = creditCardNumber;
        acctNumbersAndPin[1][counter] = pin;
        ++counter;
        System.out.println(Arrays.deepToString(acctNumbersAndPin));
    }


    public static void showLoginScreen() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        selectedAction = input.nextInt();

        switch (selectedAction) {
            //case 0 terminates the program
            case 0:
                System.out.println("Bye!");
                return;
            //case 1 creates an account
            case 1:
                generateCreditCard();
                storeCreditCard(creditCardNumber);
                //CreditCard cc1 = new CreditCard();
                //CreditCard.storeCC(cc1.creditCardNumber);
                break;
            case 2:
                checkCredentials();
                break;
        }
    }

        public static void checkCredentials() {
            int counter2 = 0;
            System.out.println("Enter your card number:");
            long userCN = input.nextLong();
            System.out.println("Enter your PIN:");
            long userPin = input.nextLong();

            //create a loop to check if login is accurate

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
                    //checks all CN on file
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


