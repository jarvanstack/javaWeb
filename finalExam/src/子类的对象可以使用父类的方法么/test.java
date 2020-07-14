package 子类的对象可以使用父类的方法么;

public class test {
    public static void main(String[] args) {
        //**不能，
        new sum().father();
    }
}
