import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    String time;

    public Transaction(int id, int amount, String merchant, String account, String time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class WEEK1P9 {

    // -------- Classic Two Sum --------
    public static List<String> findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction match = map.get(complement);

                result.add("(" + match.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }

        return result;
    }

    // -------- Duplicate Detection --------
    public static void detectDuplicates(List<Transaction> transactions) {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "_" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        System.out.println("Duplicate Transactions:");

        for (List<Transaction> list : map.values()) {

            if (list.size() > 1) {

                System.out.println("Amount: " + list.get(0).amount +
                        " Merchant: " + list.get(0).merchant +
                        " Accounts involved: " + list.size());
            }
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "Store A", "acc1", "10:00"));
        transactions.add(new Transaction(2, 300, "Store B", "acc2", "10:15"));
        transactions.add(new Transaction(3, 200, "Store C", "acc3", "10:30"));
        transactions.add(new Transaction(4, 500, "Store A", "acc4", "11:00"));

        // Two Sum
        System.out.println("Two Sum Result:");
        System.out.println(findTwoSum(transactions, 500));

        // Duplicate Detection
        detectDuplicates(transactions);
    }
}