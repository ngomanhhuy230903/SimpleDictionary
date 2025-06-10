package com.example.simpledictionary.model;

import java.io.Serializable;
import java.util.List;
public class Word implements Serializable {
    private String word; // Từ vựng, ví dụ: "hello"
    private String phonetic; // Phiên âm, ví dụ: /həˈloʊ/
    private List<WordMeaning> meanings; // Danh sách các nghĩa của từ

    // Constructor để tạo một đối tượng Word
    public Word(String word, String phonetic, List<WordMeaning> meanings) {
        this.word = word;
        this.phonetic = phonetic;
        this.meanings = meanings;
    }

    // Các hàm getter để lấy thông tin
    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public List<WordMeaning> getMeanings() {
        return meanings;
    }
}