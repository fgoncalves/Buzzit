package com.buzzit.buzzit;

import android.content.Context;
import com.buzzit.buzzit.data.db.modules.DbModule;
import com.buzzit.buzzit.data.db.modules.ServicesModule;
import com.buzzit.buzzit.domain.modules.UseCasesModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Main dagger module for the entire application. Include here other modules that would make part
 * of the app as well.
 * <p/>
 * Created by fred on 25.08.15.
 */
@Module(includes = { ServicesModule.class, DbModule.class, UseCasesModule.class }, library = true)
public class AppModule {
  private final Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton public Context providesContext() {
    return context;
  }
}
