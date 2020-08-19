public class KahanSummation {
    public static void main(String[] args) {
        float sum = 0.0f;
        // 大量的浮点小数相加，最终加到1600万的时候，会丢失精度
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f;
            sum += x;
        }
        System.out.println("sum is " + sum);

        // Kahan算法解决了上面的问题
//        sum = 0.0f;
        float c = 0.0f;
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f;
            float y = x - c;//c：截止上一轮丢失的精度 y：本轮要加的数与之前累计损失的精度之和
            float t = sum + y;//t：本轮求和结果 sum：上一轮求和结果
            c = (t-sum)-y;//c：截止本轮丢失的精度 sum：上一轮求和结果
            sum = t;
        }
        System.out.println("sum is " + sum); }
}
