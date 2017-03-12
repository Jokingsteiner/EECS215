import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjk98 on 3/12/2017.
 */

public class KnapsackRepetition {
    int N;
    int W;
    int value[];
    int weight[];
    // the node taken to build OPT for each w, for trace back convenience;
    int takenNode[];
    int opt[];

    public KnapsackRepetition(int n, int W, int[] value, int[] weight) {
        this.N = n;
        this.W = W;

        this.value = new int[this.N + 1];
        this.value[0] = 0;
        for (int i = 0; i < value.length; i++)
            this.value[i + 1] = value[i];

        this.weight = new int[this.N + 1];
        this.weight[0] = 0;
        for (int i = 0; i < weight.length; i++)
            this.weight[i + 1] = weight[i];

        this.takenNode = new int[this.W + 1];
        this.opt = new int[this.W + 1];

        dpApproach();
    }

    private void dpApproach() {
        for (int w = 1; w <= W; w++) {
            for (int i = 1; i <= N; i++) {
                if (weight[i] <= w) {
                    if (opt[w - weight[i]] + value[i] > opt[w]) {
                        takenNode[w] = i;
                        opt[w] = opt[w - weight[i]] + value[i];
                    }
                }
            }
        }
    }

    public void printResult() {
        int maxValue = 0;
        int maxWeight = 0;
        System.out.println("item" + "\t" + "value" + "\t" + "weight");
        int w = W;
        int node;
        do {
            node = takenNode[w];
            System.out.println(node + "\t\t" + value[node] + "\t\t" + weight[node]);
            maxValue += value[node];
            maxWeight += weight[node];
            w -= weight[node];
        } while (takenNode[w] != 0);

        System.out.println("----------------------");
        System.out.println("Total" + "\t" + maxValue + "\t\t" + maxWeight);
    }

    public void printOPT () {
        System.out.printf("weight\t");
        for (int i = 0; i < W + 1; i++)
            System.out.printf(i + "\t");
        System.out.printf("\nvalue\t");
        for (int i = 0; i < W + 1; i++)
            System.out.printf(opt[i] + "\t");
        System.out.println("");
    }

    public static List<String> getTokensFromString (String str, String splitRegEx) {
        List<String> tokenList = new ArrayList<>();
        String[] tokenOfLine = str.split(splitRegEx);
        for (String s: tokenOfLine) {
            if (s.length() != 0)
                tokenList.add(s.toLowerCase());
        }
        return tokenList;
    }

    public static void main(String[] args) {
        final int NUM_OF_INPUTS = 3;
        for (int i = 1; i <= NUM_OF_INPUTS; i++) {
            String filename = ".\\Knapsack\\inputs\\KP_input_" + i + ".txt";
            FileReaderWBuffer fr = new FileReaderWBuffer(filename);
            ArrayList<String> lines = fr.readAll();
            fr.close();

            int weightLimit = Integer.valueOf(getTokensFromString(lines.get(0), "[\\D]+").get(0));
            int value[] = new int[lines.size() - 1];
            int weight[] = new int[lines.size() - 1];
            List<String> tokens;
            for (int j = 1; j < lines.size(); j++) {
                tokens = getTokensFromString(lines.get(j), "[\\D]+");
                weight[j - 1] = Integer.valueOf(tokens.get(1));
                value[j - 1] = Integer.valueOf(tokens.get(2));
            }

            assert (value.length == weight.length);
            KnapsackRepetition ks = new KnapsackRepetition(value.length, weightLimit, value, weight);
            ks.printOPT();
            ks.printResult();
            System.out.println("");
        }
    }
}
