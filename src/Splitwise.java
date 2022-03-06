import model.*;
import org.omg.Messaging.SyncScopeHelper;
import service.ExpenseManager;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Splitwise {

    public static void main(String argv[]){
        //add user

        ExpenseManager expenseManager  = new ExpenseManager();
        expenseManager.addUser( new User("1", "suri", "surendra@gmail.com", "11233443"));
        expenseManager.addUser( new User("2", "suri2", "surendrak@gmail.com", "11233444"));
        expenseManager.addUser( new User("3", "suri3", "surendraks@gmail.com", "11233445"));

        System.out.println("Enter the command");
        Scanner s = new Scanner(System.in);
        String command = s.nextLine();
        String[] commands = command.split(" ");
        String commandType = commands[0];

        //Expense in the format: EXPENSE <user-id-of-person-who-paid>
        // <no-of-users>
        // <space-separated-list-of-users>
        // <EQUAL/EXACT/PERCENT>
        // <space-separated-values-in-case-of-non-equal>
        //
        //Show balances for all: SHOW
        //
        //Show balances for a single user: SHOW <user-id>

        if(commandType.equalsIgnoreCase("SHOW")){
            if (commands.length == 1) {
                expenseManager.showBalances();
            } else {
                expenseManager.showBalance(commands[1]);
            }

        }else if(commandType.equalsIgnoreCase("EXPENSE")){
            String paidBy = commands[1];
            Double amount = Double.parseDouble(commands[2]);
            List<Split> splits = new ArrayList<>();
            int usersCount = Integer.parseInt(commands[3]);
            ExpenseType expenseType = ExpenseType.valueOf(commands[4+usersCount]);

            switch (expenseType){
                case EQUAL:
                    for (int i = 0; i < usersCount; i++) {
                        splits.add(new SplitEqual(expenseManager.userMap.get(commands[4 + i])));
                    }
                    expenseManager.addExpense(ExpenseType.EQUAL, amount, paidBy, splits, null);
                    break;

                case EXACT:
                    for (int i = 0; i < usersCount; i++) {
                        splits.add(new SplitExact(expenseManager.userMap.get(commands[4 + i]), Double.parseDouble(commands[5 + usersCount + i])));
                    }
                    expenseManager.addExpense(ExpenseType.EXACT, amount, paidBy, splits, null);
                    break;

                case PERCENT:
                    for (int i = 0; i < usersCount; i++) {
                        splits.add(new SplitPercent(expenseManager.userMap.get(commands[4 + i]), Double.parseDouble(commands[5 + usersCount + i])));
                    }
                    expenseManager.addExpense(ExpenseType.PERCENT, amount, paidBy, splits, null);
                    break;
            }
            expenseManager.showBalance(commands[1]);
        }else{
            System.out.println("Enter Valid type of Command");
        }
    }


}
