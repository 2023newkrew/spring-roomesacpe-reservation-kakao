package nextstep.theme;

import nextstep.theme.entity.Theme;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThemeMock {
    public static List<Theme> makeRandomTheme(int count, String[] names){
        return IntStream.range(0, count)
                .mapToObj((index) -> makeRandomTheme(names[index]))
                .collect(Collectors.toList());
    }

    public static Theme makeRandomTheme(){
        return makeRandomTheme(1, getRandomNames(1)).get(0);
    }

    public static Theme makeRandomTheme(String name){
        String[] descs = {"desc1", "desc2", "desc3", "desc4"};
        Integer[] prices = {1000, 2000, 3000, 4000};
        int index = ThreadLocalRandom.current().nextInt(descs.length);

        return new Theme(name, descs[index], prices[index]);
    }

    public static String[] getRandomNames(int count){
        return IntStream.range(0, count)
                .map((index) -> ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE))
                .mapToObj((value) -> "theme" + value)
                .toArray(String[]::new);
    }
}
