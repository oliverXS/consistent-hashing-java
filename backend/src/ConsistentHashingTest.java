import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaorui
 */
public class ConsistentHashingTest {
    public static void main(String[] args) {
        List<String> nodeList = new LinkedList<>();
        nodeList.add("192.168.0.0:111");
        nodeList.add("192.168.0.1:111");
        nodeList.add("192.168.0.2:111");
        nodeList.add("192.168.0.3:111");
        nodeList.add("192.168.0.4:111");
        // ConsistentHashingWithVirtualNodes consistentHashing_2 = new ConsistentHashingWithVirtualNodes(nodeList);

        String[] keys = {"key-01", "key-02", "key-03", "key-04"};

        System.out.println("Test for Consistent Hashing without virtual nodes");
        ConsistentHashingWithoutVirtualNodes consistentHashing_1 = new ConsistentHashingWithoutVirtualNodes(nodeList);
        Map<String, Integer> nodeDistribution_1 = new HashMap<>();
        for (String key : keys) {
            String node = consistentHashing_1.getNode(key);
            System.out.println("[" + key + "]'s hash: " + FnvHash.fnv1_32(key) + ", it was routed to node [" + node + "]");
            nodeDistribution_1.put(node, nodeDistribution_1.getOrDefault(node, 0) + 1);
        }
        System.out.println();

        System.out.println("Test for Consistent Hashing with virtual nodes");
        ConsistentHashingWithVirtualNodes consistentHashing_2 = new ConsistentHashingWithVirtualNodes(nodeList);
        for (String key : keys) {
            System.out.println("[" + key + "]'s hash: " + FnvHash.fnv1_32(key) + ", it was routed to node [" + consistentHashing_2.getNode(key) + "]");
        }
    }
}