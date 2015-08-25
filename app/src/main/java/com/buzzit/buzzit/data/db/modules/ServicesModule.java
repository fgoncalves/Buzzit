package com.buzzit.buzzit.data.db.modules;

import com.buzzit.buzzit.data.services.CreateWordsService;
import com.buzzit.buzzit.data.services.implementation.CreateWordsServiceImpl;
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
}
