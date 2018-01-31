package com.justas.planuotojaspro.global;

public class Language {
    private String language;
    private String lang;


    public Language(String language, String lang) {
        this.language = language;
        this.lang = lang;
    }

    public String getLanguage() {
        return language;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return getLanguage();
    }
}
