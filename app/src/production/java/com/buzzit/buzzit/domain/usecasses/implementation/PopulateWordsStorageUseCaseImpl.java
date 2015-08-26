package com.buzzit.buzzit.domain.usecasses.implementation;

import android.content.Context;
import com.buzzit.buzzit.R;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.CreateWordsService;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * This implementation reads the file words.json in the raw resources of the app and uses this file
 * to populate the local storage.
 * <p/>
 * Created by fred on 26.08.15.
 */
public class PopulateWordsStorageUseCaseImpl implements PopulateWordsStorageUseCase {
  private final CreateWordsService service;
  private final Context context;

  @Inject public PopulateWordsStorageUseCaseImpl(CreateWordsService service, Context context) {
    this.service = service;
    this.context = context;
  }

  @Override public Observable<List<Word>> populate() {
    // First read and parse the file and then use flatMap to chain the observers and create the
    // words
    return Observable.create(new Observable.OnSubscribe<List<Word>>() {
      @Override public void call(Subscriber<? super List<Word>> subscriber) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.words);
        List<Word> words = new Gson().fromJson(new InputStreamReader(inputStream),
            new TypeToken<ArrayList<Word>>() {
            }.getType());
        subscriber.onNext(words);
        subscriber.onCompleted();
      }
    }).flatMap(new Func1<List<Word>, Observable<List<Word>>>() {
      @Override public Observable<List<Word>> call(List<Word> words) {
        return service.create(words);
      }
    });
  }
}
