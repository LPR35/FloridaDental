import java.util.Scanner;

public class FloridaDental {

    private static final Scanner keyboard = new Scanner(System.in);

    //Main method asking for inputs for family members, family members names, and the teeth they have
    //Also checks that teeth are no more than 8 letters and teeth must be in I B M letters

    public static void main(String[] args) {
        int numberOfPeopleInFamily;
        String[] familyMemberNames;
        char[][][] teeth;
        char option;
        int familyMember;
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");
        numberOfPeopleInFamily = getNumberOfFamilyMembers();
        familyMemberNames = new String[numberOfPeopleInFamily];
        teeth = new char[numberOfPeopleInFamily][2][8];
        for (familyMember = 0; familyMember < familyMemberNames.length; familyMember++) {
            System.out.print("Please enter the name for family member " + (familyMember + 1) + "   : ");
            familyMemberNames[familyMember] = keyboard.next();

            String acceptedTeeth = rowOfTeeth("uppers", familyMemberNames[familyMember]);
            for (int teeeth = 0; teeeth < acceptedTeeth.length(); teeeth++) {
                teeth[familyMember][0][teeeth] = acceptedTeeth.charAt(teeeth);
            }
            acceptedTeeth = rowOfTeeth("lowers", familyMemberNames[familyMember]);
            for (int teeeeth = 0; teeeeth < acceptedTeeth.length(); teeeeth++) {
                teeth[familyMember][1][teeeeth] = acceptedTeeth.charAt(teeeeth);
            }
        }
        //Create a switch case so user can select which menu option they want
        System.out.println();
        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
        do {
            option = keyboard.next().toUpperCase().charAt(0);
            switch (option) {
                case 'P':
                    System.out.println();
                    printTeeth(teeth, familyMemberNames);
                    System.out.println();
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                    break;
                case 'E':
                    extraction(teeth, familyMemberNames);
                    System.out.println();
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                    break;
                case 'R':
                    System.out.println();
                    rootCanal(teeth);
                    System.out.println();
                    System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
                    break;
                case 'X':
                    System.out.println();
                    System.out.print("Exiting the Floridian Tooth Records :-)");
                    break;
                default:
                    System.out.print("Invalid menu option, try again              : ");
                    break;
            }
        } while (option != 'X');
    }

    //Store uppers and lowers teeth

    private static void printTeeth(char[][][] teeth, String[] familyMemberNames) {
        for (int name = 0; name < familyMemberNames.length; name++) {
            System.out.println(familyMemberNames[name]);
            for (int layer = 0; layer < teeth[name].length; layer++) {
                if (layer == 0) {
                    System.out.print("   Uppers: ");
                } else {
                    System.out.print("   Lowers: ");
                }
                for (int toothPosition = 0; toothPosition < teeth[name][layer].length; toothPosition++) {
                    System.out.print((toothPosition + 1) + ":" + teeth[name][layer][toothPosition] + "  ");
                }
                System.out.println();
            }
        }
    }

    //Create a 3 char array to store family member name, tooth layer, and position of tooth
    //Also had all input letters automatically be read as upper case and convert them to uppercase
    private static void extraction(char[][][] teeth, String[] familyMemberNames) {
        String enterName;
        char toothLayer;
        int indexOfLayer, positionOfTooth;
        int indexOfName = 0;
        System.out.print("Which family member                         : ");
        enterName = keyboard.next();
        do {
            for (int name = 0; name < familyMemberNames.length; name++) {
                if (enterName.equalsIgnoreCase(familyMemberNames[name])) {
                    indexOfName = name;
                    break;
                } else {
                    indexOfName = -1;
                }
            }
            if (indexOfName < 0) {
                System.out.print("Invalid family member, try again            : ");
                enterName = keyboard.next();
            }
        } while (indexOfName < 0);
        System.out.print("Which tooth layer (U)pper or (L)ower        : ");
        do {
            toothLayer = keyboard.next().toUpperCase().charAt(0);
            if (toothLayer == 'U') {
                indexOfLayer = 0;
            } else if (toothLayer == 'L') {
                indexOfLayer = 1;
            } else {
                System.out.print("Invalid layer, try again                    : ");
                indexOfLayer = -1;
            }
        } while (indexOfLayer < 0);
        System.out.print("Which tooth number                          : ");
        do {
            positionOfTooth = keyboard.nextInt();
            positionOfTooth--;
            if (positionOfTooth >= 0 && positionOfTooth < teeth[indexOfName][indexOfLayer].length) {
                if (teeth[indexOfName][indexOfLayer][positionOfTooth] == 'M') {
                    System.out.print("Missing tooth, try again                    : ");
                    positionOfTooth = -1;
                }
            } else {
                System.out.print("Invalid tooth number, try again             : ");
            }
            // Check if the input is greater than length of that specific layer for that specific person
            // Check if that tooth is already missing, if it is, do a system.out.print line saying that and get a new input
        } while (positionOfTooth < 0 || positionOfTooth >= teeth[indexOfName][indexOfLayer].length);
        teeth[indexOfName][indexOfLayer][positionOfTooth] = 'M';
    }

    private static void rootCanal(char[][][] teeth) {
        int incisor = 0, bicuspids = 0, missing = 0;
        double posRoot, negRoot, theDiscriminant;
        for (int person = 0; person < teeth.length; person++) {
            for (int row = 0; row < teeth[person].length; row++) {
                for (int column = 0; column < teeth[person][row].length; column++) {
                    if (teeth[person][row][column] == 'I') incisor++;
                    if (teeth[person][row][column] == 'B') bicuspids++;
                    if (teeth[person][row][column] == 'M') missing++;
                }
            }
        }
        //System.out.println(bicuspids + " " + incisor + " " + missing);
        theDiscriminant = (bicuspids * bicuspids) - (4 * (incisor * -missing));
        //System.out.println("This is the discriminant, " + theDiscriminant);
        posRoot = (-bicuspids + Math.sqrt(theDiscriminant)) / (2 * incisor);
        negRoot = (-bicuspids - Math.sqrt(theDiscriminant)) / (2 * incisor);
        System.out.println("One root canal at    " + String.format("%.2f", posRoot));
        System.out.println("Another root canal at " + String.format("%.2f", negRoot));
    }
    // I made a variable for the discriminant (b^2 -4ac)
    //Then I finished the rest of the formula (b + discriminat / 2a) AND (b - discrimnant / 2a)
    // Also these will be two seperate variables for you to print out, a negative and a positive variable

    private static int getNumberOfFamilyMembers() {
        int numOfFam;
        System.out.print("Please enter number of people in the family : ");
        numOfFam = keyboard.nextInt();
        while (numOfFam >= 7 || numOfFam <= 0) {
            System.out.print("Invalid number of people, try again         : ");
            numOfFam = keyboard.nextInt();
        }
        return numOfFam;
    }

    //Get input for upper and lower rows of teeth
    //Make sure they are only I B M.
    private static String rowOfTeeth(String row, String name) {
        String teethLetters;
        boolean isValid = true;
        int notValid = 0;
        System.out.print("Please enter the " + row + " for " + name + "       : ");
        do {
            isValid = true;
            teethLetters = keyboard.next().toUpperCase();
            if (teethLetters.length() > 8) {
                System.out.print("To many teeth, try again                    : ");
                notValid = 0;
            } else if (teethLetters.length() <= 8) {
                for (int tooth = 0; tooth < teethLetters.length(); tooth++) {
                    if ((teethLetters.charAt(tooth) != 'I') && (teethLetters.charAt(tooth) != 'B') && (teethLetters.charAt(tooth) != 'M') && (teethLetters.charAt(tooth) != 'i') && (teethLetters.charAt(tooth) != 'b') && (teethLetters.charAt(tooth) != 'm')) {
                        isValid = false;
                    }
                }
                if (isValid == false) {
                    System.out.print("Invalid teeth types, try again              : ");
                    notValid = 0;
                }
                else {
                    notValid = 1;
                }
            }
            else {
                notValid = 1;
                break;
            }
        }
        while (notValid == 0);
        return teethLetters;
    }
}