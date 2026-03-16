import java.util.*;

class WEEK1P7{

    int tokens;
    int maxTokens;
    double refillRate;
    long lastRefillTime;

    public TokenBucket(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    private void refill() {

        long now = System.currentTimeMillis();
        double tokensToAdd = (now - lastRefillTime) / 1000.0 * refillRate;

        if (tokensToAdd > 0) {

            tokens = Math.min(maxTokens, tokens + (int) tokensToAdd);
            lastRefillTime = now;
        }
    }
}

public class RateLimiterSystem {

    private HashMap<String, TokenBucket> clientBuckets = new HashMap<>();

    private int maxRequests = 1000;
    private double refillRate = 1000.0 / 3600.0; // tokens per second

    public boolean checkRateLimit(String clientId) {

        clientBuckets.putIfAbsent(
                clientId,
                new TokenBucket(maxRequests, refillRate)
        );

        TokenBucket bucket = clientBuckets.get(clientId);

        return bucket.allowRequest();
    }

    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clientBuckets.get(clientId);

        if (bucket == null) {
            System.out.println("Client not found");
            return;
        }

        System.out.println(
                "Remaining requests: " + bucket.tokens +
                        " / " + bucket.maxTokens
        );
    }

    public static void main(String[] args) {

        RateLimiterSystem limiter = new RateLimiterSystem();

        String client = "abc123";

        for (int i = 1; i <= 5; i++) {

            boolean allowed = limiter.checkRateLimit(client);

            if (allowed) {
                System.out.println("Request " + i + " → Allowed");
            } else {
                System.out.println("Request " + i + " → Denied");
            }
        }

        limiter.getRateLimitStatus(client);
    }
}