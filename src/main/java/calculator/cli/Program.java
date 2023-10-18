package calculator.cli;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        new Program().run();
    }

    private void run() {
        //Does removing an element shift the indexes
        List<String> list = new java.util.ArrayList<>(List.of("A", "B", "C"));
        System.out.println(list.indexOf("B"));
        list.remove("A");
        System.out.println(list.indexOf("B"));
    }
}
