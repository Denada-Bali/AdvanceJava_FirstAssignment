package model;

/**
 * @author Denalda | Danja
 * Created on January 08, 2023, at 09:39 PM
 */

public class FileContent {
 public String Language;
 public String Content;

 public FileContent() {
 }

 public FileContent(String language, String content) {
  Language = language;
  Content = content;
 }

 public String getLanguage() {
  return Language;
 }

 public void setLanguage(String language) {
  Language = language;
 }

 public String getContent() {
  return Content;
 }

 public void setContent(String content) {
  Content = content;
 }
}
