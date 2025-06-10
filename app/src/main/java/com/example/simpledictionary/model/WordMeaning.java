package com.example.simpledictionary.model;

import java.util.List;

public class WordMeaning {
    private String partOfSpeech; // Loại từ

    // THAY ĐỔI QUAN TRỌNG Ở ĐÂY
    private List<Definition> definitions; // Giờ đây là một danh sách các đối tượng Definition

    private List<String> synonyms; // API cũng trả về từ đồng nghĩa
    private List<String> antonyms; // và từ trái nghĩa

    // Constructor đã được cập nhật
    public WordMeaning(String partOfSpeech, List<Definition> definitions, List<String> synonyms, List<String> antonyms) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    // Getter đã được cập nhật
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }
}