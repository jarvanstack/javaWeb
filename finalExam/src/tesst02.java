public class tesst02 {
    public static void main(String[] args) {

        int x = 10;
        int a = 0 ;
        int b = 0 ;
        switch (x)
        {
            case 10:
                x += 15;
            case 12:
                x -= 5;
            case 500:
                System.out.println("进入500");
               a = 500;
            case 1000:
                System.out.println("1000");
                b = 1000;
            default:
                x *= 3;
        }
    }
}
