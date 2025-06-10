package com.example.simpledictionary.model;

// Lớp này đại diện cho cấu trúc của MỘT đối tượng định nghĩa từ API
public class Definition {
    private String definition; // Nội dung định nghĩa
    private String example;    // Câu ví dụ
    // API còn có thể có synonyms, antonyms nhưng ta chỉ lấy 2 trường này cho đơn giản

    // Constructor và Getter
    public Definition(String definition, String example) {
        this.definition = definition;
        this.example = example;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }
}