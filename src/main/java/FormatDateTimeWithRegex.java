import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatDateTimeWithRegex {

    public static void main(String[] args) {
        String text = "初步诊断：阿斯顿，阿德飒飒的，敖德萨是否。撒酷酷酷：解决绝";

        // 提取第一个标点符号之前的子串
        String result = extractBeforePunctuation(text);

        // 输出结果
        System.out.println(result.trim()); // 使用 trim() 去除前后可能存在的空白字符
    }

    private static String extractBeforePunctuation(String text) {
        int index = text.indexOf(":") == -1? text.indexOf("："):-1;
        if(index == -1){
            return "";
        }
        text = text.substring(index+1);
        // 定义标点符号集合
        String punctuationMarks = "，.。?!、：";

        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if (punctuationMarks.indexOf(c) >= 0) {
                return text.substring(0, i).trim();
            }
        }

        // 如果没有找到任何标点符号，返回整个字符串
        return text.trim();
    }

    /**
     * 使用正则表达式将日期时间字符串格式化为 "yyyy-MM-dd HH:mm:ss"。
     *
     * @param dateTimeStr 日期时间字符串
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTimeWithRegex(String dateTimeStr) {
        // 正则表达式用于匹配日期时间
        String regex = "(\\d{4})年(\\d{2})月(\\d{2})日";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateTimeStr);

        if (matcher.find()) {
            // 使用匹配到的组重新构建日期时间字符串
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);

            System.out.println(year + "年" + month + "月" + day + "日");
            return dateTimeStr.replace(year + "年" + month + "月" + day + "日", year + "-" + month + "-" + day + " ");
        } else {
            throw new IllegalArgumentException("Invalid date time format: " + dateTimeStr);
        }
    }
}