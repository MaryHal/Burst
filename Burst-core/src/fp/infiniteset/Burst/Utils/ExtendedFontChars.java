package fp.infiniteset.Burst.Utils;

public class ExtendedFontChars
{
    public static void test()
    {
        System.out.println(Character.toString((char)0x4E11));
    }

    public static String cjk()
    {
        String s = "";

        /* East Asian Scripts Unicode Specification
         * http://www.unicode.org/versions/Unicode5.0.0/ch12.pdf#G12159
         * Table 12-2. Blocks Containing Han Ideographs
         * Block                                   Range       Comment
         * CJK Unified Ideographs                  4E00-9FFF   Common
         * CJK Unified Ideographs Extension A      3400-4DFF   Rare
         * CJK Unified Ideographs Extension B      20000-2A6DF Rare, historic
         * CJK Compatibility Ideographs            F900-FAFF   Duplicates, unifiable variants, corporate characters
         * CJK Compatibility Ideographs Supplement 2F800-2FA1F Unifiable variants
         */
        for (int i = 0x4e00; i < 0x62ff; ++i)
        {
            String n = new String(Character.toChars(i));
            /* System.out.println(n); */
            s += n;
            /* s += Character.toString((char)i); */
        }

        System.out.println(0x62ff - 0x4e00);
        System.out.println(s.length());

        return s;
    }
}

