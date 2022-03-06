package model;

import java.util.List;

public class ExactExpense extends Expense{

    public ExactExpense(double amount, User paidBy, List<Split> splits, ExpenseMetadata metadata) {
        super(amount, paidBy, splits, metadata);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof SplitExact)) {
                return false;
            }
        }

        double totalAmount = getAmount();
        double sumSplitAmount = 0;
        for (Split split : getSplits()) {
            SplitExact exactSplit = (SplitExact) split;
            sumSplitAmount += exactSplit.getAmount();
        }

        if (totalAmount != sumSplitAmount) {
            return false;
        }

        return true;

    }
}
