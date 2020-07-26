public class KnapsackProblem {
    private static int maxW = 0;//结果
    private static int[] weight = {2,2,4,6,3};//物品重量
    private static int n = 5;//物品个数
    private static int w = 9;//背包最大承受重量

    /**
     * 递归解法
     * 递推公式：f(n)=f1(n)+f2(n) f1:不放入背包 f2:放入背包
     * 退出条件：背包超重，或者所有物品都考察完
     * @param i
     * @param cw
     */
    public static void getMaxW(int i,int cw) {
        if(cw > maxW) maxW = cw;
        if (cw == w || i == n) return ;
        getMaxW(i+1, cw);
        if(cw + weight[i] <= w)
            getMaxW(i+1,cw+weight[i]);
    }

    /**
     * 动态规划解法
     * 状态转移公式：weight(n)=weight(n-1)+w
     */
    public static int dynamicProgramming() {
        boolean[] states = new boolean[w+1];
        states[0] = true;
        for(int i = 0; i < n; i++)
            for(int j = w-weight[i]; j >= 0; j--){
                if(states[j]==true) states[j + weight[i]] = true;
            }

        for(int i=w; i>=0; i--)
            if(states[i] == true) return i;
       return 0;
    }

    public static void main(String[] args) {
        getMaxW(0,0);
        System.out.println(maxW);
        System.out.println(dynamicProgramming());
    }
}
