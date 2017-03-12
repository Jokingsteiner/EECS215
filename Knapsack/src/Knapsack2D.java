import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * Created by cjk98 on 3/12/2017.
 */

public class Knapsack2D {
    int N;
    int W;
    int Z;
    int value[];
    int weight[];
    int volume[];
    boolean isTaken[][][];
    boolean finalDecision[];

    public Knapsack2D(int n, int W, int Z, int[] value, int[] weight, int[] volume) {
        this.N = n;
        this.W = W;
        this.Z = Z;

        this.value = new int[this.N + 1];
        this.value[0] = 0;
        for (int i = 0; i < value.length; i++)
            this.value[i + 1] = value[i];

        this.weight = new int[this.N + 1];
        this.weight[0] = 0;
        for (int i = 0; i < weight.length; i++)
            this.weight[i + 1] = weight[i];

        this.volume = new int[this.N + 1];
        this.volume[0] = 0;
        for (int i = 0; i < volume.length; i++)
            this.volume[i + 1] = volume[i];

        this.isTaken = new boolean[this.N + 1][this.W + 1][this.Z + 1];
        this.finalDecision = new boolean[this.N + 1];

        this.dpApproach();
        this.traceBack();
    }

    private void dpApproach() {
        int opt[][][] = new int[N + 1][W + 1][Z + 1];
        // to record if a item is taken in a sub problem, convenient to trace back

        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {
                for (int z = 1; z <= Z; z++) {
                    // don't take item i
                    int previousOPT = opt[i - 1][w][z];

                    // take item i
                    int newOPT = Integer.MIN_VALUE;
                    if (weight[i] <= w && volume[i] <= z)
                        newOPT = value[i] + opt[i - 1][w - weight[i]][z - volume[i]];

                    // pick the option that can give maximum value in this sub problem
                    opt[i][w][z] = Math.max(previousOPT, newOPT);
                    if (newOPT > previousOPT)
                        isTaken[i][w][z] = true;
                    else
                        isTaken[i][w][z] = false;
                }
            }
        }
    }

    private void traceBack() {
        // trace back to see which item should be put in to the bag in final solution with W and Z
        for (int n = N, w = W, z = Z; n > 0; n--) {
            if (isTaken[n][w][z]) {
                finalDecision[n] = true;
                w = w - weight[n];
                z = z - volume[n];
            }
            else {
                finalDecision[n] = false;
            }
        }
    }

    public void printResult() {
        int maxValue = 0;
        int maxWeight = 0;
        int maxVolume = 0;
        System.out.println("item" + "\t" + "value" + "\t" + "weight" + "\t" + "volume");
        for (int i = 1; i <= N; i++) {
            if (finalDecision[i]) {
                System.out.println(i + "\t\t" + value[i] + "\t\t" + weight[i] + "\t\t" + volume[i]);
                maxValue += value[i];
                maxWeight += weight[i];
                maxVolume += volume[i];
            }
        }
        System.out.println("------------------------------");
        System.out.println("Total" + "\t" + maxValue + "\t\t" + maxWeight + "\t\t" + maxVolume);
    }


    public static void main(String[] args) {
        int value[] = {1, 6, 18, 22, 28};
        int weight[] = {1, 2, 5, 6, 7};
        int volume[] = {1, 10, 3, 2, 2};
        assert(value.length == weight.length);
        assert(value.length == volume.length);
        assert(volume.length == weight.length);

        Knapsack2D ks1 = new Knapsack2D(value.length, 11, 15, value, weight, volume);
        ks1.printResult();

    }
}