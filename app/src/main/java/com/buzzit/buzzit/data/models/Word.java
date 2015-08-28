package com.buzzit.buzzit.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Word {

  @DatabaseField(generatedId = true) private Integer id;
  @SerializedName("text_eng") @Expose @DatabaseField private String textEng;
  @SerializedName("text_spa") @Expose @DatabaseField private String textSpa;

  public String getTextEng() {
    return textEng;
  }

  public void setTextEng(String textEng) {
    this.textEng = textEng;
  }

  public String getTextSpa() {
    return textSpa;
  }

  public void setTextSpa(String textSpa) {
    this.textSpa = textSpa;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Word word = (Word) o;

    return !(id != null ? !id.equals(word.id) : word.id != null);
  }

  @Override public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
