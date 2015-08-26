package com.buzzit.buzzit.domain.modules;

import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.domain.usecasses.implementation.GetAllWordsUseCaseImpl;
import com.buzzit.buzzit.domain.usecasses.implementation.PopulateWordsStorageUseCaseImpl;
import com.buzzit.buzzit.domain.usecasses.implementation.RemoveWordUseCaseImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module for the use cases
 * <p/>
 * Created by fred on 26.08.15.
 */
@Module(complete = false, library = true) public class UseCasesModule {

  @Provides @Singleton public PopulateWordsStorageUseCase providesPopulateWordsStorage(
      PopulateWordsStorageUseCaseImpl populateWordsStorage) {
    return populateWordsStorage;
  }

  @Provides @Singleton
  public GetAllWordsUseCase providesGetAllWordsUseCase(GetAllWordsUseCaseImpl getAllWordsUseCase) {
    return getAllWordsUseCase;
  }

  @Provides @Singleton
  public RemoveWordUseCase providesRemoveWordUseCase(RemoveWordUseCaseImpl removeWordUseCase) {
    return removeWordUseCase;
  }
}
