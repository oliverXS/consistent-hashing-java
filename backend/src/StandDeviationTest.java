import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaorui
 */
public class StandDeviationTest {
    private Map<String, Integer> nodeDistribution = new HashMap<>();

    public StandDeviationTest(Map<String, Integer> nodeDistribution) {
        this.nodeDistribution = nodeDistribution;
    }

    public void getStandDeviation(int numKeys) {
        // Analyze distribution
        int totalKeys = 0;
        double sumOfSquaredDeviations = 0;
        for (int keys : nodeDistribution.values()) {
            totalKeys += keys;
            double deviation = keys - (double) numKeys / nodeDistribution.size();
            sumOfSquaredDeviations += deviation * deviation;
        }
        double averageKeysPerNode = (double) totalKeys / nodeDistribution.size();
        double standardDeviation = Math.sqrt(sumOfSquaredDeviations / nodeDistribution.size());

        // Print distribution statistics
        System.out.println("Distribution Statistics");
        System.out.println("Total Keys: " + numKeys);
        System.out.println("Total Nodes: " + nodeDistribution.size());
        System.out.println("Average Keys per Node: " + averageKeysPerNode);
        System.out.println("Standard Deviation: " + standardDeviation);
    }

    private static String[] generateTestKeys(int numKeys) {
        String[] keys = new String[numKeys];
        for (int i = 0; i < numKeys; i++) {
            keys[i] = "Key_" + i;
        }
        return keys;
    }

    public static void main(String[] args) {
        // Set test nodes
        List<String> nodeList = new LinkedList<>();
        nodeList.add("192.168.0.0:111");
        nodeList.add("192.168.0.1:111");
        nodeList.add("192.168.0.2:111");
        nodeList.add("192.168.0.3:111");
        nodeList.add("192.168.0.4:111");

        // Set test keys
        int numKeys = 1000000;
        String[] testKeys = generateTestKeys(numKeys);

        // Test for version without virtual nodes
        ConsistentHashingWithoutVirtualNodes hashing_1 = new ConsistentHashingWithoutVirtualNodes(nodeList);
        Map<String, Integer> nodeDistribution1 = new HashMap<>();
        for (String key : testKeys) {
            String node = hashing_1.getNode(key);
            nodeDistribution1.put(node, nodeDistribution1.getOrDefault(node, 0) + 1);
        }
        StandDeviationTest standDeviation_1 = new StandDeviationTest(nodeDistribution1);
        standDeviation_1.getStandDeviation(numKeys);
        System.out.println();

        // Test for version with virtual nodes
        ConsistentHashingWithVirtualNodes hashing_2 = new ConsistentHashingWithVirtualNodes(nodeList);
        Map<String, Integer> nodeDistribution2 = new HashMap<>();
        for (String key : testKeys) {
            String node = hashing_2.getNode(key);
            nodeDistribution2.put(node, nodeDistribution2.getOrDefault(node, 0) + 1);
        }
        StandDeviationTest standDeviation_2 = new StandDeviationTest(nodeDistribution2);
        standDeviation_2.getStandDeviation(numKeys);
    }
}
