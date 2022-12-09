package T02.link;

public class test {
    public static void main(String[] args) {
        test6();
    }

    static void test6() {
        int k = 4;
        while (k-- > 1) {
            System.out.println("1");
        }
        k = 4;
        while (--k > 1) {
            System.out.println("2");
        }

    }

    static void test5() {
        int k = 0;
        while (k++ < 4) {
            System.out.println(k);
        }

        System.out.println();

        int q = 0;
        while (++q < 4) {
            System.out.println(q);
        }
    }


    static void test4() {
        int k = 4;
        while (k-- > 0) {
            System.out.println(k);
        }
        System.out.println();

        int q = 4;
        while (--q > 0) {
            System.out.println(q);
        }
    }


    static void test3() {
        int a = 4;
        for (int i = 4; i > 0; i--) {
            System.out.println(a--);
        }

        System.out.println();
        int b = 4;
        for (int i = 4; i > 0; i--) {
            System.out.println(--b);
        }
    }


    static void test2() {
        int a = 0;
        for (int i = 0; i < 4; i++) {
            System.out.println(a++);
        }
        System.out.println();

        int b = 0;
        for (int i = 0; i < 4; ++i) {
            System.out.println(++b);
        }
    }


    static void test1() {
        for (int i = 0; i < 4; i++) {
            System.out.println("---------------1-------------");
        }
        System.out.println();

        for (int i = 0; i < 4; ++i) {
            System.out.println("---------------2-------------");
        }
        System.out.println();
        for (int i = 4; i > 0; i--) {
            System.out.println("---------------3-------------");
        }
        System.out.println();
        for (int i = 4; i > 0; --i) {
            System.out.println("---------------4-------------");
        }
        System.out.println();
    }
}
