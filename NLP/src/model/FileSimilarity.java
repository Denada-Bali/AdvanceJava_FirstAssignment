package model;

/**
 * @author Denalda | Danja
 * Created on January 08, 2023, at 10:43 PM
 */

public class FileSimilarity {

 public  Double Similarity;
 public  String Language;

 public FileSimilarity() {
 }

 public Double getSimilarity() {
  return Similarity;
 }

 public void setSimilarity(Double similarity) {
  Similarity = similarity;
 }

 public String getLanguage() {
  return Language;
 }

 public void setLanguage(String language) {
  Language = language;
 }
}
