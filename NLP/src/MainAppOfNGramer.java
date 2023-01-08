import model.FileContent;
import model.FileSimilarity;
import model.NGram;
import model.language;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * * @author Denalda | Danja
 * * Created on December 13, 2022, at 22:17 AM
 */
public class MainAppOfNGramer {

    static String localFolder = ".idea/data/mystery.txt";
    private static  List<language> languageListObj = new ArrayList<>();

    public MainAppOfNGramer() throws IOException {
        new NGramRepositoryFromFileImpl();
    } 

    public static void main(String[] args) {

        try {
             execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void execute() throws IOException, InterruptedException {

        NGramRepositoryFromFileImpl unigram = new NGramRepositoryFromFileImpl("unigram");
        NGramRepositoryFromFileImpl twogram = new NGramRepositoryFromFileImpl("twogram");
        NGramRepositoryFromFileImpl cosineSimilarity = new NGramRepositoryFromFileImpl("cosineSimilarity");

        System.out.println("\nStarting Executor");

        // create ExecutorService to manage threads
        ExecutorService executorService = Executors.newCachedThreadPool();

        String mysteryFileContent = new String(Files.readAllBytes(Paths.get(localFolder)));

        List<language> retfiles=readFiles();


        System.out.println("************** UNIGRAM MODEL ***********\n");
        executorService.execute(unigram);
        Thread.sleep(5000);
        uniGram(mysteryFileContent);

        executorService.execute(twogram);
        Thread.sleep(5000);
        System.out.println("\n************** BIGRAM MODEL ************\n");
        biGram(mysteryFileContent);

        executorService.execute(cosineSimilarity);
        Thread.sleep(5000);
        System.out.println("\n************ Cosine Similarity *********\n");
        similarityBetweenDocs(retfiles,mysteryFileContent);


        System.out.printf("\nTasks started, main ends.%n%n");
        executorService.shutdown();
    }

    private static void uniGram(String str) throws IOException {

        String[] tokens = str.replaceAll("(?!')\\p{P}", "").toLowerCase().split("\\s");

        HashMap<String, Integer> hashmap = new HashMap<String, Integer>();

        for (String wOccurrences : tokens) {
            hashmap.merge(wOccurrences, 1, Integer::sum);
        }
        Iterator<Map.Entry<String, Integer>> iterator = hashmap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pair = iterator.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
            iterator.remove();
        }
    }

    private static void biGram(String str) {

        String removeAllWhitespace = str.replaceAll("(?!')\\p{P}", "").replaceAll("\\s", "").toLowerCase();

        NGram ngrm =new NGram(removeAllWhitespace);

         List<String> ss = new ArrayList<>();
        new NGram(removeAllWhitespace).forEachRemaining((n) -> ss.add(n));
        Set<String> st = new HashSet<String>(ss);
        for (String s : st)
            System.out.println(s + ": " + Collections.frequency(ss, s));

    }

    private static void similarityBetweenDocs( List<language> languages, String mysteryFile) throws IOException {

        List<FileContent> listContent = new ArrayList<>();

        for (language elm:languages) {

            String contentLanguage = new String(Files.readAllBytes(Paths.get(elm.path)));

            if(listContent.stream().anyMatch(o -> elm.language.equals(o.getLanguage())))
            {
             String  ss=listContent.stream()
                        .filter(x->elm.language.equals(x.getLanguage()))
                        .findFirst()
                        .get().getContent();

                listContent.stream()
                        .filter(x->elm.language.equals(x.getLanguage()))
                        .findFirst().get().setContent(ss + "" + contentLanguage);

            }else
            {
                listContent.add(new FileContent(elm.language,contentLanguage));
            }

        }




        NGramRepositoryFromFileImpl mysteryDotTxtLocalFolder = new NGramRepositoryFromFileImpl();

        for (String w : mysteryFile.split("[^a-zA-Z]+")) {
            mysteryDotTxtLocalFolder.incCount(w);
        }

        NGramRepositoryFromFileImpl languagesfiles = new NGramRepositoryFromFileImpl();

        List<FileSimilarity> listsm = new ArrayList<>();

       for (FileContent contentObj:listContent) {
           languagesfiles = new NGramRepositoryFromFileImpl();
           for (String w : contentObj.getContent().split("[^a-zA-Z]+")) {

               languagesfiles.incCount(w);
           }
           FileSimilarity sm = new FileSimilarity();
           sm.Similarity=mysteryDotTxtLocalFolder.getCosineSimilarityWith(languagesfiles);
           sm.Language = contentObj.Language;
           listsm.add(sm);
        }

       for(FileSimilarity elm : listsm )
       {
           System.out.println("Similarity for language "+elm.getLanguage() + " =" +elm.getSimilarity());
       }

    }

    private static List<String> getFiles(String path, String fileExtensionToRead) throws IOException {
        Path getPath = Paths.get(path);
        try (Stream<Path> walk = Files.walk(getPath, Integer.MAX_VALUE)) {
            return walk
                    .map(String::valueOf)
                    .filter(file -> file.contains(fileExtensionToRead))
                    .collect(toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getFolders(String path) throws IOException {
        Path getPath = Paths.get(path);
        try (Stream<Path> walk = Files.walk(getPath, 1)) {
            return walk
                    .map(String::valueOf)
                    .filter(file -> new File(file).isDirectory() && !file.equalsIgnoreCase(path))
                    .collect(toList());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
        return Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream()
                .collect(Collectors.groupingBy(keyFunction));
    }

    public static List<language> readFiles() {

     List<String> retList =  readFolder();

        for (String folder:retList ) {

            String directoryPath = folder;
            try {
                String fileExtensionToRead = ".txt";
                List<String> txtFiles = getFiles(directoryPath, fileExtensionToRead);
                for (String filePath : txtFiles) {
                    System.out.println(filePath);
                    languageListObj.add(new language(filePath, directoryPath.substring(directoryPath.indexOf("-")+1)));
                }
            } catch (IOException e) {
                System.out.println("Error due to: " + e.getMessage());
            }
        }

        return languageListObj;
    }

    public  static List<String> readFolder() {
        String directoryPath = ".idea/data/";
        List<String> lst = new ArrayList<>();
        try {
            List<String> Folders = getFolders(directoryPath);
            for (String filePath : Folders) {
                System.out.println(filePath);
              if(!filePath.equals(".idea\\data")) {
                  lst.add(filePath);
              }
            }
        } catch (IOException e) {
            System.out.println("Error due to: " + e.getMessage());
        }
        return lst;
    }

    public static void readInsideTheFile() throws IOException {

        //read mystery.txt file with lambdas and streams
        Path path = Paths.get(".idea/data/mystery.txt");

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
