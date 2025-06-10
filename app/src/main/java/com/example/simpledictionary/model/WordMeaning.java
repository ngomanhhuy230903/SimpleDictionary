package com.example.simpledictionary.model;

import java.util.List;

// Lớp này đại diện cho MỘT nghĩa của từ (vì một từ có thể có nhiều nghĩa)
public class WordMeaning {
    private String partOfSpeech; // Loại từ, ví dụ: "noun", "verb"
    private List<String> definitions; // Danh sách các định nghĩa
    private String example; // Một câu ví dụ

    // Constructor
    public WordMeaning(String partOfSpeech, List<String> definitions, String example) {
        this.partOfSpeech = partOfSpeech;
        this.definitions = definitions;
        this.example = example;
    }

    // Các hàm getter
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public String getExample() {
        return example;
    }
}