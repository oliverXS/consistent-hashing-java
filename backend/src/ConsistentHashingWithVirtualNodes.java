import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author xiaorui
 */
public class ConsistentHashingWithVirtualNodes {
    private List<String> realNodes = new LinkedList<>();
    private final SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    private static final int VIRTUAL_NODES_NUM = 100;

    public ConsistentHashingWithVirtualNodes(List<String> realNodes) {
        this.realNodes = realNodes;
        generateVirtualNodes(this.realNodes);
    }

    private void generateVirtualNodes(List<String> realNodes) {
        for (String node : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES_NUM; i++) {
                String virtualNodeName = node + ":VNode" + String.valueOf(i);
                int hash = getHash(virtualNodeName);
                System.out.println("Virtual Node[" + virtualNodeName + "] was added, its hash: " + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
        System.out.println();
    }

    public String getNode(String key) {
        int hash = getHash(key);
        SortedMap<Integer, String> tailMap = virtualNodes.tailMap(hash);
        String virtualNode;
        if (tailMap.isEmpty()) {
            virtualNode = virtualNodes.get(virtualNodes.firstKey());
        } else {
            virtualNode = tailMap.get(tailMap.firstKey());
        }

        if (virtualNode != null) {
            return virtualNode.substring(0, virtualNode.indexOf(":"));
        }

        return null;
    }


    private int getHash(String key) {
        return FnvHash.fnv1_32(key);
    }
}
