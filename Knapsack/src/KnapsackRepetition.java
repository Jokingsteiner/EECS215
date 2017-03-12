/**
 * Created by cjk98 on 3/12/2017.
 */

public class KnapsackRepetition {
    int N;
    int W;
    int value[];
    int weight[];
    boolean isTaken[][];
    boolean finalDecision[];

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

        this.isTaken = new boolean[this.N + 1][this.W + 1];
        this.finalDecision = new boolean[this.N + 1];

        this.dpApproach();
        this.traceBack();
    }

    private void dpApproach() {
        int opt[][] = new int[N + 1][W + 1];
        // to record if a item is taken in a sub problem, convenient to trace back

        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {
                // don't take item i
                int prevOPT = opt[i - 1][w];

                // take item i
                int newOPT = Integer.MIN_VALUE;
                if (weight[i] <= w)
                    newOPT = value[i] + opt[i - 1][w - weight[i]];

                // pick the option that can give maximum value in this sub problem
                opt[i][w] = Math.max(prevOPT, newOPT);
                if (newOPT > prevOPT)
                    isTaken[i][w] = true;
                else
                    isTaken[i][w] = false;
            }
        }
    }

    private void traceBack() {
        // trace back to see which item should be put in to the bag in final solution with W
        for (int n = N, w = W; n > 0; n--) {
            if (isTaken[n][w]) {
                finalDecision[n] = true;
                w = w - weight[n];
            }
            else {
                finalDecision[n] = false;
            }
        }
    }

    public void printResult() {
        int maxValue = 0;
        int maxWeight = 0;
        System.out.println("item" + "\t" + "value" + "\t" + "weight");
        for (int i = 1; i <= N; i++) {
            if (finalDecision[i]) {
                System.out.println(i + "\t\t" + value[i] + "\t\t" + weight[i]);
                maxValue += value[i];
                maxWeight += weight[i];
            }
        }
        System.out.println("Total" + "\t" + maxValue + "\t\t" + maxWeight);
    }


    public static void main(String[] args) {
        int value[] = {1, 6, 18, 22, 28};
        int weight[] = {1, 2, 5, 6, 7};

        KnapsackRepetition ks1 = new KnapsackRepetition(value.length, 11, value, weight);
        ks1.printResult();

    }
}
