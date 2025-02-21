import java.util.*;

class Item {
    String name;
    List<String> features;

    public Item(String name, List<String> features) {
        this.name = name;
        this.features = features;
    }
}

class RecommenderSystem {
    private List<Item> items;

    public RecommenderSystem(List<Item> items) {
        this.items = items;
    }

    private double calculateSimilarity(List<String> features1, List<String> features2) {
        Set<String> featureSet = new HashSet<>(features1);
        featureSet.retainAll(features2);
        return (double) featureSet.size() / Math.sqrt(features1.size() * features2.size());
    }

    public List<String> recommend(List<String> userPreferences, int topN) {
        PriorityQueue<Item> pq = new PriorityQueue<>((a, b) ->
                Double.compare(calculateSimilarity(userPreferences, b.features),
                        calculateSimilarity(userPreferences, a.features)));
        
        pq.addAll(items);
        
        List<String> recommendations = new ArrayList<>();
        for (int i = 0; i < topN && !pq.isEmpty(); i++) {
            recommendations.add(pq.poll().name);
        }
        return recommendations;
    }
}

public class AIRecommendationSystem {
    public static void main(String[] args) {
        List<Item> items = Arrays.asList(
            new Item("Laptop", Arrays.asList("electronics", "portable", "battery-powered")),
            new Item("Smartphone", Arrays.asList("electronics", "portable", "touchscreen")),
            new Item("Headphones", Arrays.asList("electronics", "audio", "wireless")),
            new Item("Coffee Maker", Arrays.asList("kitchen", "appliance", "coffee"))
        );

        RecommenderSystem recommender = new RecommenderSystem(items);
        List<String> userPreferences = Arrays.asList("electronics", "audio");
        List<String> recommendations = recommender.recommend(userPreferences, 2);

        System.out.println("Recommended Items: " + recommendations);
    }
}
