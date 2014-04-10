package com.ehc.angel.api;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 10/4/14
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenShots {
  List<Type> type;


  static class Type {
    String getThumb() {
      return thumb;
    }

    void setThumb(String thumb) {
      this.thumb = thumb;
    }

    String getOriginal() {
      return original;
    }

    void setOriginal(String original) {
      this.original = original;
    }

    String thumb;
    String original;

  }
}
