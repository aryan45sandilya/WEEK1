import java.util.*;

class WEEK1P5 {

    // page URL -> visit count
    private HashMap<String, Integer> pageViews = new HashMap<>();

    // page URL -> unique visitors
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    private HashMap<String, Integer> trafficSources = new HashMap<>();


    // Process incoming page event
    public void processEvent(String url, String userId, String source) {

        // count page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // track unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // count traffic sources
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }


    // Get Top 10 pages
    public void getTopPages() {

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top Pages:");

        int count = 0;

        for (Map.Entry<String, Integer> entry : list) {

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(
                    url + " - " + views + " views (" + unique + " unique)"
            );

            count++;
            if (count == 10) break;
        }
    }


    // Show traffic source distribution
    public void showTrafficSources() {

        int total = 0;

        for (int count : trafficSources.values()) {
            total += count;
        }

        System.out.println("\nTraffic Sources:");

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {

            double percent = (entry.getValue() * 100.0) / total;

            System.out.println(entry.getKey() + ": "
                    + String.format("%.2f", percent) + "%");
        }
    }


    // Display dashboard
    public void getDashboard() {

        getTopPages();
        showTrafficSources();
    }


    public static void main(String[] args) {

        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        dashboard.processEvent("/article/breaking-news", "user1", "google");
        dashboard.processEvent("/article/breaking-news", "user2", "facebook");
        dashboard.processEvent("/sports/championship", "user3", "google");
        dashboard.processEvent("/sports/championship", "user4", "direct");
        dashboard.processEvent("/sports/championship", "user5", "google");
        dashboard.processEvent("/article/breaking-news", "user3", "google");

        dashboard.getDashboard();
    }
}