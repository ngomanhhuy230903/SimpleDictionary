package com.example.simpledictionary.model;

import java.io.Serializable;
public class Definition implements Serializable {
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