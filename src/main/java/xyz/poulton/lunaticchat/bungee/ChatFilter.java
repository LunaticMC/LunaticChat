//    Copyright © Lucy Poulton 2020.
//    This file is part of LunaticChat.
//
//    LunaticChat is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    LunaticChat is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with LunaticChat. If not, see <https://www.gnu.org/licenses/>.

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
