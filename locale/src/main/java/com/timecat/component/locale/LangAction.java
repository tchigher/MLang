package com.timecat.component.locale;

import com.timecat.component.locale.model.LangPackDifference;
import com.timecat.component.locale.model.LangPackLanguage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2020/10/23
 * @description null
 * @usage null
 */
public interface LangAction {
    /**
     * 在 UI 线程运行
     * 比如 在应用语言包后要调用，在 UI 线程进行刷新
     * @param runnable 要运行的 runnable
     */
    void runOnUIThread(Runnable runnable);
    /**
     * SharedPreferences preferences = Utilities.getGlobalMainSettings();
     * SharedPreferences.Editor editor = preferences.edit();
     * editor.putString("language", language);
     * editor.commit();
     * @param language localeInfo.getKey()
     */
    void saveLanguageKeyInLocal(String language);

    /**
     * SharedPreferences preferences = Utilities.getGlobalMainSettings();
     * String lang = preferences.getString("language", null);
     * @return lang
     */
    @Nullable
    String loadLanguageKeyInLocal();

    /**
     * 在其他线程网络请求，在主线程或UI线程调用callback
     * 这里设计成这样，是因为这个方法里支持异步执行
     * 您需要在合适的时机手动调用 callback，且只能调用一次
     * @param lang_pack 语言包名字
     * @param lang_code 语言包版本名称
     * @param from_version 语言包版本号
     * @param callback 在主线程或UI线程调用
     */
    void langpack_getDifference(String lang_pack, String lang_code, int from_version, @NonNull GetDifferenceCallback callback);

    /**
     * 在其他线程网络请求，在主线程或UI线程调用callback
     * 这里设计成这样，是因为这个方法里支持异步执行
     * 您需要在合适的时机手动调用 callback，且只能调用一次
     * @param callback 在主线程或UI线程调用
     */
    void langpack_getLanguages(@NonNull GetLanguagesCallback callback);

    /**
     * 在其他线程网络请求，在主线程或UI线程调用callback
     * 这里设计成这样，是因为这个方法里支持异步执行
     * 您需要在合适的时机手动调用 callback，且只能调用一次
     * @param lang_code 语言包版本名称
     * @param callback 在主线程或UI线程调用
     */
    void langpack_getLangPack(String lang_code, @NonNull GetLangPackCallback callback);

    interface GetLanguagesCallback {
        /**
         * 必须在UI线程或者主线程调用
         * 所有可用的语言包
         * @param languageList 语言包列表
         */
        void onLoad(List<LangPackLanguage> languageList);
    }

    interface GetDifferenceCallback {
        /**
         * 必须在UI线程或者主线程调用
         * 如果服务端没有实现增量分发的功能，可以用完整的语言包代替
         * @param languageList 增量的语言包
         */
        void onLoad(LangPackDifference languageList);
    }

    interface GetLangPackCallback {
        /**
         * 必须在UI线程或者主线程调用
         * @param languageList 完整的语言包
         */
        void onLoad(LangPackDifference languageList);
    }

}
