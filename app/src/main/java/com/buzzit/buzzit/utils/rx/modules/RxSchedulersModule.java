package com.buzzit.buzzit.utils.rx.modules;

import com.buzzit.buzzit.utils.rx.IOToUISchedulerTransformer;
import com.buzzit.buzzit.utils.rx.SchedulerTransformer;
import com.buzzit.buzzit.utils.rx.SequenceRepeaterObservableCreator;
import com.buzzit.buzzit.utils.rx.SequenceRepeaterObservableCreatorImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Dagger module for the Rx scheduler transformers.
 * <p/>
 * Created by fred on 26.08.15.
 */
@Module(complete = false, library = true) public class RxSchedulersModule {
  public static final String IO_TO_UI_SCHEDULER_TRANSFORMER_INJECTION_NAME =
      "io.to.ui.scheduler.transformer";

  @Named(IO_TO_UI_SCHEDULER_TRANSFORMER_INJECTION_NAME) @Provides @Singleton
  public SchedulerTransformer providesIOToUISchedulerTransformer() {
    return new IOToUISchedulerTransformer();
  }

  @Provides @Singleton
  public SequenceRepeaterObservableCreator providesSequenceRepeaterObservableCreator(
      SequenceRepeaterObservableCreatorImpl sequenceRepeaterObservableCreator) {
    return sequenceRepeaterObservableCreator;
  }
}