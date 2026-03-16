 import java.util.*;
/* ---------------- PROBLEM 2 ---------------- */
/* Flash Sale Inventory Manager */

class InventoryManager {

    private HashMap<String, Integer> stock = new HashMap<>();
    private HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedList<>());
    }

    public synchronized String purchaseItem(String productId, int userId) {

        int currentStock = stock.getOrDefault(productId, 0);

        if (currentStock > 0) {
            stock.put(productId, currentStock - 1);
            return "Success, remaining stock: " + (currentStock - 1);
        } else {
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            return "Added to waiting list, position #" + queue.size();
        }
    }

    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }
}


/* ---------------- PROBLEM 3 ---------------- */
/* DNS Cache with TTL */

class DNSCache {

    class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, int ttl) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttl * 1000;
        }
    }

    HashMap<String, DNSEntry> cache = new HashMap<>();

    public String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null && System.currentTimeMillis() < entry.expiryTime) {
            return "Cache HIT → " + entry.ip;
        }

        String ip = "172.217.14." + new Random().nextInt(255);
        cache.put(domain, new DNSEntry(ip, 300));

        return "Cache MISS → " + ip;
    }
}


/* ---------------- PROBLEM 4 ---------------- */
/* Plagiarism Detection */

class PlagiarismDetector {

    HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    public List<String> getNgrams(String text, int n) {

        String[] words = text.split(" ");
        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {
            String gram = "";

            for (int j = i; j < i + n; j++) {
                gram += words[j] + " ";
            }

            grams.add(gram.trim());
        }

        return grams;
    }

    public void indexDocument(String docId, String text) {

        for (String gram : getNgrams(text, 5)) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }
}


/* ---------------- PROBLEM 5 ---------------- */
/* Real-Time Analytics Dashboard */

class AnalyticsDashboard {

    HashMap<String, Integer> pageViews = new HashMap<>();
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    HashMap<String, Integer> trafficSource = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        trafficSource.put(source, trafficSource.getOrDefault(source, 0) + 1);
    }

    public void showDashboard() {

        System.out.println("Page Views: " + pageViews);
        System.out.println("Traffic Sources: " + trafficSource);
    }
}


/* ---------------- PROBLEM 6 ---------------- */
/* Rate Limiter */

class RateLimiter {

    class TokenBucket {

        int tokens;
        long lastRefill;
        int maxTokens = 1000;

        TokenBucket() {
            tokens = maxTokens;
            lastRefill = System.currentTimeMillis();
        }
    }

    HashMap<String, TokenBucket> clients = new HashMap<>();

    public boolean allowRequest(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket());

        TokenBucket bucket = clients.get(clientId);

        if (bucket.tokens > 0) {
            bucket.tokens--;
            return true;
        }

        return false;
    }
}


/* ---------------- PROBLEM 7 ---------------- */
/* Autocomplete System */

class AutocompleteSystem {

    HashMap<String, Integer> queryFrequency = new HashMap<>();

    public void addQuery(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {

        List<String> result = new ArrayList<>();

        for (String query : queryFrequency.keySet()) {

            if (query.startsWith(prefix)) {
                result.add(query);
            }
        }

        return result;
    }
}


/* ---------------- PROBLEM 8 ---------------- */
/* Parking Lot using Open Addressing */

class ParkingLot {

    String[] spots = new String[500];

    public int parkVehicle(String plate) {

        int hash = Math.abs(plate.hashCode()) % spots.length;

        int index = hash;

        while (spots[index] != null) {
            index = (index + 1) % spots.length;
        }

        spots[index] = plate;
        return index;
    }

    public void exitVehicle(String plate) {

        for (int i = 0; i < spots.length; i++) {

            if (plate.equals(spots[i])) {
                spots[i] = null;
                System.out.println("Vehicle exited from spot " + i);
                return;
            }
        }
    }
}


/* ---------------- PROBLEM 9 ---------------- */
/* Two Sum Transaction Detection */

class TransactionAnalyzer {

    public List<int[]> twoSum(int[] arr, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();
        List<int[]> result = new ArrayList<>();

        for (int num : arr) {

            int complement = target - num;

            if (map.containsKey(complement)) {
                result.add(new int[]{num, complement});
            }

            map.put(num, 1);
        }

        return result;
    }
}


/* ---------------- PROBLEM 10 ---------------- */
/* Multi-Level Cache System */

class MultiLevelCache {

    LinkedHashMap<String, String> L1 = new LinkedHashMap<>(10000, 0.75f, true);
    HashMap<String, String> L2 = new HashMap<>();

    public String getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            return "L1 Cache HIT";
        }

        if (L2.containsKey(videoId)) {

            L1.put(videoId, L2.get(videoId));
            return "L2 Cache HIT → Promoted to L1";
        }

        String data = "VideoData";
        L2.put(videoId, data);

        return "Database HIT → Added to L2";
    }
}


/* ---------------- MAIN CLASS ---------------- */

public class Main {

    public static void main(String[] args) {

        InventoryManager inventory = new InventoryManager();
        inventory.addProduct("IPHONE15", 100);

        System.out.println("Stock: " + inventory.checkStock("IPHONE15"));
        System.out.println(inventory.purchaseItem("IPHONE15", 12345));

        DNSCache dns = new DNSCache();
        System.out.println(dns.resolve("google.com"));

        AnalyticsDashboard dashboard = new AnalyticsDashboard();
        dashboard.processEvent("/news", "user1", "google");
        dashboard.processEvent("/news", "user2", "facebook");
        dashboard.showDashboard();

        RateLimiter limiter = new RateLimiter();
        System.out.println("Rate Limit Allowed: " + limiter.allowRequest("client1"));

        AutocompleteSystem auto = new AutocompleteSystem();
        auto.addQuery("java tutorial");
        auto.addQuery("javascript guide");

        System.out.println(auto.search("jav"));

        ParkingLot parking = new ParkingLot();
        int spot = parking.parkVehicle("ABC1234");
        System.out.println("Parked at spot: " + spot);

        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        int[] arr = {500, 300, 200};
        System.out.println(analyzer.twoSum(arr, 500));

        MultiLevelCache cache = new MultiLevelCache();
        System.out.println(cache.getVideo("video123"));
    }
}