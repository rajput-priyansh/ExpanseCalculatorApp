
package com.example.expansecalculatorapp.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expansecalculatorapp.model.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;
    private LiveData<List<Word>> listLiveData;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        listLiveData = wordRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return listLiveData;
    }

    public void insert(Word word) { wordRepository.insert(word); }

}