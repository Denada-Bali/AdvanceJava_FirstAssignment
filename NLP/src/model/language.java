package model;

/**
 * @author Denalda | Danja
 * Created on January 08, 2023, at 06:03 PM
 */

public class language {

 public  String path ;

 public  String language;

 public language(String path, String language) {
  this.path = path;
  this.language = language;
 }

 public language() {

 }

 public String getPath() {
  return path;
 }

 public String getLanguage() {
  return language;
 }

 public void setPath(String path) {
  this.path = path;
 }

 public void setLanguage(String language) {
  this.language = language;
 }

 @Override
 public String toString() {
  return "language{" +
          "path='" + path + '\'' +
          ", language='" + language + '\'' +
          '}';
 }
}
