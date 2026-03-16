import java.util.*;

class WEEK1P10 {

    // L1 Cache (LRU using LinkedHashMap)
    LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(10000, 0.75f, true);

    // L2 Cache (SSD cache)
    HashMap<String, String> L2 = new HashMap<>();

    // Statistics
    int L1Hits = 0;
    int L2Hits = 0;
    int DBHits = 0;

    // Get video data
    public String getVideo(String videoId) {

        // Check L1
        if (L1.containsKey(videoId)) {
            L1Hits++;
            return "L1 Cache HIT (0.5ms)";
        }

        // Check L2
        if (L2.containsKey(videoId)) {

            L2Hits++;

            // Promote to L1
            L1.put(videoId, L2.get(videoId));

            return "L2 Cache HIT → Promoted to L1 (5ms)";
        }

        // Database fetch
        DBHits++;

        String data = "VideoData_" + videoId;

        // Add to L2 cache
        L2.put(videoId, data);

        return "Database HIT → Added to L2 (150ms)";
    }

    // Display cache statistics
    public void getStatistics() {

        int total = L1Hits + L2Hits + DBHits;

        System.out.println("L1 Hits: " + L1Hits);
        System.out.println("L2 Hits: " + L2Hits);
        System.out.println("DB Hits: " + DBHits);

        if (total > 0) {

            System.out.println(
                    "Overall Hit Rate: " +
                            ((L1Hits + L2Hits) * 100.0 / total) + "%"
            );
        }
    }
}

public class CacheSystem {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        System.out.println(cache.getVideo("video_123"));
        System.out.println(cache.getVideo("video_456"));
        System.out.println(cache.getVideo("video_123"));

        cache.getStatistics();
    }
}