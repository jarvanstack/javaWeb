public class test03 {
    /**
     * 传入方法的参数的值会被拷贝，而不是改变.
     *
     * @param args v
     */
    public static void main(String[] args) {
        //参数是否能在方法改变
        int x = 0;
        changeX(x);
        System.out.println(x);

    }
    public static void changeX(int x ){
        x = 10;
    }
}
