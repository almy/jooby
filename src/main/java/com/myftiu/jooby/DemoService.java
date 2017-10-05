package com.myftiu.jooby;

import com.squareup.moshi.Moshi;
import com.squareup.tape2.ObjectQueue;
import com.squareup.tape2.QueueFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Converter;
import retrofit2.converter.moshi.MoshiConverterFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Singleton
public class DemoService {

   private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

   @PostConstruct
   public void start() throws IOException {
      demoQueue().add("new String");
      LOGGER.info("starting ModelDemo post construct");
      Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               demoQueue().remove();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      });

      Thread thread2 = new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               demoQueue().remove();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      });
      thread.start();
      thread2.start();

   }

   @PreDestroy
   public void stop() {
      LOGGER.info("stopping ModelDemo pre-destroy ");
   }


   public ObjectQueue<String> demoQueue() throws IOException {

      final MoshiConverter<String> converter =  new MoshiConverter<>(new Moshi.Builder().build(), String.class);
      final File file = new File("TestingDemoX");
      final QueueFile queueFile = new QueueFile.Builder(file).build();
      final ObjectQueue<String> queue = ObjectQueue.create(queueFile, converter);
      return queue;

   }

}
