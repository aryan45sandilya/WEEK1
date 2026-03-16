import java.util.*;

public class WEEK1P4
{

    // n-gram -> set of documents containing it
    private HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    // Extract n-grams from text
    public List<String> generateNgrams(String text, int n) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> grams = new ArrayList<>();

        for (int i = 0; i <= words.length - n; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = i; j < i + n; j++) {
                gram.append(words[j]).append(" ");
            }

            grams.add(gram.toString().trim());
        }

        return grams;
    }

    // Index a document into hash table
    public void indexDocument(String docId, String text) {

        List<String> grams = generateNgrams(text, 5);

        for (String gram : grams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }

        System.out.println(docId + " indexed with " + grams.size() + " n-grams");
    }

    // Analyze new document for plagiarism
    public void analyzeDocument(String docId, String text) {

        List<String> grams = generateNgrams(text, 5);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {

            if (ngramIndex.containsKey(gram)) {

                for (String existingDoc : ngramIndex.get(gram)) {

                    matchCount.put(existingDoc,
                            matchCount.getOrDefault(existingDoc, 0) + 1);
                }
            }
        }

        System.out.println("Analysis for " + docId);

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            double similarity = (entry.getValue() * 100.0) / grams.size();

            System.out.println(
                    "Matched with " + entry.getKey() +
                            " → " + entry.getValue() + " n-grams → Similarity: "
                            + String.format("%.2f", similarity) + "%"
            );

            if (similarity > 60) {
                System.out.println("⚠ PLAGIARISM DETECTED");
            }
        }
    }

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        String essay1 = "machine learning is transforming the world of technology and innovation";
        String essay2 = "machine learning is transforming the world of artificial intelligence";
        String essay3 = "sports and fitness improve health and overall wellbeing";

        detector.indexDocument("essay_001", essay1);
        detector.indexDocument("essay_002", essay2);

        detector.analyzeDocument("essay_003", essay3);
    }
}