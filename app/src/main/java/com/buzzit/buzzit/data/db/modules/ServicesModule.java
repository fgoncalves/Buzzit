package com.buzzit.buzzit.data.db.modules;

import com.buzzit.buzzit.data.services.CreateWordsService;
import com.buzzit.buzzit.data.services.GetAllWordsService;
import com.buzzit.buzzit.data.services.RemoveWordService;
import com.buzzit.buzzit.data.services.implementation.CreateWordsServiceImpl;
import com.buzzit.buzzit.data.services.implementation.GetAllWordsServiceImpl;
import com.buzzit.buzzit.data.services.implementation.RemoveWordServiceImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module satisfying the dependencies for the services
 * <p/>
 * Created by fred on 25.08.15.
 */
@Module(complete = false, library = true) public class ServicesModule {
  @Provides @Singleton
  public CreateWordsService providesCreateWordsService(CreateWordsServiceImpl createWordsService) {
    return createWordsService;
  }

  @Provides @Singleton
  public GetAllWordsService providesGetAllWordsServices(GetAllWordsServiceImpl getAllWordsService) {
    return getAllWordsService;
  }

  @Provides @Singleton
  public RemoveWordService providesRemoveWordService(RemoveWordServiceImpl removeWordService) {
    return removeWordService;
  }
}
