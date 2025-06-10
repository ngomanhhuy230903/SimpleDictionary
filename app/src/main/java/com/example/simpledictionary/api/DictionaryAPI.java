package com.example.simpledictionary.api;

import com.example.simpledictionary.model.Word;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryAPI {
    @GET("api/v2/entries/en/{word}")
    Call<List<Word>> searchWord(@Path("word") String word); // {word} sẽ được thay thế bằng giá trị của biến String word
}