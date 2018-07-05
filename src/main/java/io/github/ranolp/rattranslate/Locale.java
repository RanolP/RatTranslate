package io.github.ranolp.rattranslate;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Locale {
  AFRIKAANS("Afrikaans", "af_ZA"),
  ARABIC("العربية", "ar_SA"),
  ASTURIAN("Asturianu", "ast_ES"),
  AZERBAIJANI("Azərbaycanca", "az_AZ"),
  BELARUSIAN("Беларуская", "be_BY"),
  BULGARIAN("Български", "bg_BG"),
  BRETON("Brezhoneg", "br_FR"),
  CATALAN("Català", "ca_ES"),
  CZECH("Čeština", "cs_CZ"),
  WELSH("Cymraeg", "cy_GB"),
  DANISH("Dansk", "da_DK"),
  AUSTRIAN_GERMAN("Österreichisches Deitsch", "de_AT"),
  GERMAN("Deutsch", "de_DE"),
  GREEK("Ελληνικά", "el_GR"),
  AUSTRALIAN_ENGLISH("Australian English", "en_AU"),
  CANADIAN_ENGLISH("Canadian English", "en_CA"),
  BRITISH_ENGLISH("English (UK)", "en_GB"),
  NEW_ZEALAND_ENGLISH("New Zealand English", "en_NZ"),
  BRITISH_ENGLISH_UPSIDE_DOWN("ɥsᴉꞁᵷuƎ (ɯopᵷuᴉ\uA7B0 pǝʇᴉu∩)", "en_UD"),
  PIRATE_ENGLISH("Pirate Speak", "en_7S"),
  AMERICAN_ENGLISH("English (US)", "en_US"),
  ESPERANTO("Esperanto", "eo_UY"),
  ARGENTINIAN_SPANISH("Español (Argentina)", "es_AR"),
  SPANISH("Español (España)", "es_ES"),
  MEXICAN_SPANISH("Español (México)", "es_MX"),
  URUGUAYAN_SPANISH("Español (Uruguay)", "es_UY"),
  VENEZUELAN_SPANISH("Español (Venezuela)", "es_VE"),
  ESTONIAN("Eesti", "et_EE"),
  BASQUE("Euskara", "eu_ES"),
  PERSIAN("فارسی", "fa_IR"),
  FINNISH("Suomi", "fi_FI"),
  FILIPINO("Filipino", "fil_PH"),
  FAROESE("Føroyskt", "fo_FO"),
  FRENCH("Français", "fr_FR"),
  CANADIAN_FRENCH("Français québécois", "fr_CA"),
  FRISIAN("Frysk", "fy_NL"),
  IRISH("Gaeilge", "ga_IE"),
  SCOTTISH_GAELIC("Gàidhlig", "gd_GB"),
  GALICIAN("Galego", "gl_ES"),
  MANX("Gaelg", "gv_IM"),
  HAWAIIAN("ʻŌlelo Hawaiʻi", "haw"),
  HEBREW("עברית", "he_IL"),
  HINDI("हिन्दी", "hi_IN"),
  CROATIAN("Hrvatski", "hr_HR"),
  HUNGARIAN("Magyar", "hu_HU"),
  ARMENIAN("Հայերեն", "hy_AM"),
  INDONESIAN("Bahasa Indonesia", "id_ID"),
  ICELANDIC("Íslenska", "is_IS"),
  IDO("Ido", "io"),
  ITALIAN("Italiano", "it_IT"),
  JAPANESE("日本語", "ja_JP"),
  LOJBAN("la .lojban.", "jbo_EN"),
  GEORGIAN("ქართული", "ka_GE"),
  KOREAN("한국어", "ko_KR") {
    private final char[] SPECIAL_CHARS = {
        // Braces
        '{', '}', '[', ']', '(', ')',
        // Punctuation Marks
        '!', '.', '?', ',', '\'', '"', ':', '&',
        // Mathematics
        '%', '^', '*', '-', '=', '+', '<', '>', '/',
        // Logical (for Programmers)
        '&', '|',
        // Other
        '@', '#', '$', '_', '\\'
    };

    private final Predicate<Character> PREDICATE =
        // Minecraft Nickname Part
        ((Predicate<Character>) Locale::isMinecraftNicknamePart).
            // Hangul Standard Syllable
                or(c -> '가' <= c && c <= '힣').
            // Hangul Compatible Jamo
                or(c -> ('ㄱ' <= c && c <= 'ㅎ') || ('ㅏ' <= c && c <= 'ㅣ')).
            // Special Characters
                or(c -> Arrays.binarySearch(SPECIAL_CHARS, c) > 0);

    @Override
    public boolean isAmbiguousSentence(String sentence) {
      int okCount = sentence.length() * OK_PERCENTAGE / 100;
      for (char c: sentence.toCharArray()) {
        if (!PREDICATE.test(c)) {
          okCount--;
        }
      }
      return okCount <= 0;
    }
  },
  KOLSCH_OR_RIPUARIAN("Kölsch/Ripoarisch", "ksh_DE"),
  CORNISH("Kernewek", "kw_GB"),
  LATIN("Latina", "la_VA"),
  LUXEMBOURGISH("Lëtzebuergesch", "lb_LU"),
  LIMBURGISH("Limburgs", "li_LI"),
  LOLCAT("LOLCAT", "lol_US"),
  LITHUANIAN("Lietuvių", "lt_LT"),
  LATVIAN("Latviešu", "lv_LV"),
  MAORI("Te Reo Māori", "mi_NZ"),
  MACEDONIAN("Македонски", "mk_MK"),
  MONGOLIAN("Монгол", "mn_MN"),
  MALAY("Bahasa Melayu", "ms_MY"),
  MALTESE("Malti", "mt_MT"),
  LOW_GERMAN("Platdüütsk", "nds_DE"),
  DUTCH("Nederlands", "nl_NL"),
  NORWEGIAN_NYNORSK("Norsk Nynorsk", "nn_NO"),
  NORWEGIAN1("Norsk", "no_NO"),
  NORWEGIAN2("Norsk Bokmål", "nb_NO"),
  OCCITAN("Occitan", "oc_FR"),
  POLISH("Polski", "pl_PL"),
  BRAZILIAN_PORTUGUESE("Português (Brasil)", "pt_BR"),
  PORTUGUESE("Português (Portugal)", "pt_PT"),
  QUENYA("Quenya", "qya_AA"),
  ROMANIAN("Română", "ro_RO"),
  RUSSIAN("Русский", "ru_RU"),
  NORTHERN_SAMI("Davvisámegiella", "sme"),
  SLOVAK("Slovenčina", "sk_SK"),
  SLOVENIAN("Slovenščina", "sl_SI"),
  SOMALI("Af-Soomaali", "so_SO"),
  ALBANIAN("Shqip", "sq_AL"),
  SERBIAN("Српски", "sr_SP"),
  SWEDISH("Svenska", "sv_SE"),
  SWABIAN_GERMAN("Oschdallgaierisch", "swg_DE"),
  THAI("ภาษาไทย", "th_TH"),
  TAGALOG("Filipino", "tl_PH"),
  KLINGON("tlhIngan Hol", "tlh_AA"),
  TURKISH("Türkçe", "tr_TR"),
  TALOSSAN("Talossan", "tzl_TZL"),
  UKRAINIAN("Українська", "uk_UA"),
  VALENCIAN("Valencià", "ca-val_ES"),
  VIETNAMESE("Tiếng Việt", "vi_VI"),
  SIMPLIFIED_CHINESE("简体中文", "zh_CN"),
  TRADITIONAL_CHINESE("繁體中文", "zh_TW");

  // 30 percent of invalid characters are allowed in locale.
  private static final int OK_PERCENTAGE = 30;

  private final String name;
  private final String code;

  Locale(String name, String code) {
    this.name = name;
    this.code = code;
  }

  private static boolean isMinecraftNicknamePart(char c) {
    return Character.isLetterOrDigit(c) || c == '_';
  }

  public static Locale getByCode(String code) {
    for (Locale l: values()) {
      if (l.getCode().equalsIgnoreCase(code)) {
        return l;
      }
    }
    return null;
  }

  public boolean isAmbiguousSentence(String sentence) {
    return false;
  }

  public String getName() {
    return this.name;
  }

  public String getCode() {
    return this.code;
  }

  public String getLanguageCode() {
    return getCode().split("_")[0];
  }

  public String toPropertiesKey() {
    return "lang." + name().toLowerCase().replace('_', '-');
  }
}
