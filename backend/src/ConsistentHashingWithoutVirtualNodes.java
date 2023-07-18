import java.util.SortedMap;
import java.util.TreeMap;
import java.util.List;

/**
 * @author xiaorui
 *
 * ConsistentHasing(fnv1_32) without virtual nodes
 */
public class ConsistentHashingWithoutVirtualNodes {
    private final SortedMap<Integer, String> ring;

    public ConsistentHashingWithoutVirtualNodes(List<String> nodeList) {
        this.ring = new TreeMap<>();
        for (String node : nodeList) {
            addNode(node);
        }
        System.out.println();
    }

    public void addNode(String node) {
        int hash = getHash(node);
        ring.put(hash, node);
        System.out.println("[" + node + "] added, its hash: " + hash);
    }

    public String getNode(String key) {
        if (ring.isEmpty()) {
            return null;
        }

        int hash = getHash(key);
        SortedMap<Integer, String> tailMap = ring.tailMap(hash);

        if (tailMap.isEmpty()) {
            return ring.get(ring.firstKey());
        }

        return tailMap.get(tailMap.firstKey());
    }

    private int getHash(String key) {
        return FnvHash.fnv1_32(key);
    }
}
