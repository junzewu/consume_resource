import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveSpecialStrings {

    public static void main(String[] args) {

    }

    /**
     * 删除字符串中的所有 "\\r" 和 "\\n" 字符串。
     *
     * @param input 输入的字符串
     * @return 清理后的字符串
     */
    public static String removeSpecialStrings(String input) {
        // 使用正则表达式替换掉所有的 "\\r" 和 "\\n"
        return input.replaceAll("\\\\r|\\\\n", "");
    }
}