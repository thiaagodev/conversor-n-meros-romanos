package dev.thiaago.numerosromanos;

import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RomanNumeralsController {

    final Map<Integer, String> romanNumeralTable = new HashMap<>();

    public RomanNumeralsController() {
        romanNumeralTable.put(1000, "M");
        romanNumeralTable.put(500, "D");
        romanNumeralTable.put(100, "C");
        romanNumeralTable.put(50, "L");
        romanNumeralTable.put(10, "X");
        romanNumeralTable.put(5, "V");
        romanNumeralTable.put(1, "I");
    }

    @GetMapping
    public String getRomanNumeral(@RequestParam("numeral") Long numeral) {
        StringBuilder romanNumeral = new StringBuilder();
        Long tempNum = numeral;

        int i = 0;
        while(tempNum > 0) {
            if(i == romanNumeralTable.size()) {
                i = 0;
            }

            int num = romanNumeralTable.keySet().stream().sorted(Comparator.reverseOrder()).toList().get(i);
            String stringNum = romanNumeralTable.get(num);


            Long finalTempNum = tempNum;
            Integer nextNum = romanNumeralTable.keySet().stream().filter(number -> number == finalTempNum +1).findFirst().orElse(null);

            if (tempNum >= num) {
                tempNum -= num;
                romanNumeral.append(stringNum);
            } else if(nextNum != null && nextNum - tempNum  == 1) {
                    romanNumeral.append(getRomanNumeral(nextNum - tempNum));
                    romanNumeral.append(romanNumeralTable.get(nextNum));
                    tempNum -= nextNum;
            } else {
                i++;
            }

        }

        return romanNumeral.toString();
    }
}
