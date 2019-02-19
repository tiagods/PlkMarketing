package br.com.tiagods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TesteSplit {
    public static void main(String[] args) {
        String valor = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nullam a mi hendrerit, consectetur arcu vel, viverra ante. " +
                "Suspendisse accumsan libero eu tortor porttitor gravida. " +
                "Fusce leo erat, porttitor quis volutpat a, euismod vel dui. " +
                "Nullam tincidunt lacus quis leo ornare, a dignissim magna imperdiet. " +
                "Aliquam odio libero, rhoncus vel mauris in, euismod lobortis risus. " +
                "Mauris sodales augue sed sem feugiat, eu ornare nisi tincidunt. " +
                "Vivamus egestas quam et metus interdum, varius tempus ipsum porttitor. " +
                "Maecenas cursus, sem volutpat porta ultricies, orci leo molestie lacus, pellentesque tempus massa sapien sit amet enim.";
        String[] novo = valor.split("[" + Pattern.quote(",.") + "]");

        String [] contas = new String[2];
        contas[0] = "conta1";
        contas[1] = "conta2";

        List<String> array = new ArrayList<>();
        array.addAll(Arrays.asList(contas));
        array.add("conta3");

        Object[] novasContas = array.toArray();
        for(Object s : novasContas)
            System.out.println(s);

    }
}
