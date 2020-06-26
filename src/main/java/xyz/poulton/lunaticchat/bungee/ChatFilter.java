package xyz.poulton.lunaticchat.bungee;

import java.util.regex.Pattern;

public class ChatFilter {
    private final Pattern[] patterns;
    public ChatFilter(Pattern[] patterns) {
        this.patterns = patterns;
    }

    public boolean check(String in) {
        String plain = replaceChars(in).toLowerCase();
        for (Pattern pattern : patterns) {
            if (pattern.matcher(plain).find()) return false;
        }
        return true;
    }

    public String replaceChars(String in) {
        return in.replaceAll("[ÀàÁáÂâÃãÄäÅåĀāĂăĄąǍǎǺǻẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặ]", "a")
                .replaceAll("[ÇçĆćĈĉĊċČč]", "c")
                .replaceAll("[ĎďĐđ]", "d")
                .replaceAll("[ÈèÉéÊêËëĒēĔĕĖėĘęĚěẸẹẺẻẼẽẾếỀềỂểỄễỆệ]", "e")
                .replaceAll("[ƒ]", "f")
                .replaceAll("[ĜĝĞğĠġĢģ]", "g")
                .replaceAll("[ĤĥĦħ]", "h")
                .replaceAll("[ÌìÍíÎîÏïĨĩĪīĬĭĮįİıǏǐỈỉỊị]", "i")
                .replaceAll("[Ĵĵ]", "j")
                .replaceAll("[Ķķ]", "k")
                .replaceAll("[ĹĺĻļĽľĿŀŁłℓ]", "l")
                .replaceAll("[ÑñŃńŅņŇňŉ]", "n")
                .replaceAll("[ÒòÓóÔôÕõÖöØøŌōŎŏŐőƠơǑǒǾǿỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợ]", "o")
                .replaceAll("[ŔŕŖŗŘř]", "r")
                .replaceAll("[ŚśŜŝŞşŠš]", "s")
                .replaceAll("[ŢţŤťŦŧ]", "t")
                .replaceAll("[ÙùÚúÛûÜüŨũŪūŬŭŮůŰűŲųƯưǓǔǕǖǗǗǘǙǚǛǜỤụỦủỨứỪừỬửỮữỰự]", "u")
                .replaceAll("[ŴŵẀẁẂẃẄẅ]", "w")
                .replaceAll("[ÝýŸÿŶŷỲỳỴỵỶỷỸỹ]", "y")
                .replaceAll("[ŹźŻżŽž]", "z");
    }
}
