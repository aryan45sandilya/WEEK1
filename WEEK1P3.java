import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    DNSEntry(String ipAddress, int ttlSeconds) {
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }
}

public class WEEK1P3 {

    private HashMap<String, DNSEntry> cache = new HashMap<>();
    private int hits = 0;
    private int misses = 0;

    // Resolve domain name
    public String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null) {

            // Check TTL expiration
            if (System.currentTimeMillis() < entry.expiryTime) {
                hits++;
                return "Cache HIT → " + entry.ipAddress;
            } else {
                cache.remove(domain);
            }
        }

        // Cache miss → simulate upstream DNS
        misses++;
        String ip = "172.217.14." + new Random().nextInt(255);

        cache.put(domain, new DNSEntry(ip, 5)); // TTL = 5 seconds

        return "Cache MISS → Queried upstream → " + ip;
    }

    // Show cache statistics
    public void getCacheStats() {

        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0) / total;

        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        DNSCacheSystem dns = new DNSCacheSystem();

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));

        Thread.sleep(6000); // wait for TTL expiry

        System.out.println(dns.resolve("google.com"));

        dns.getCacheStats();
    }
}