import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class NGramRepositoryFromFileImpl implements Runnable {

    private final static SecureRandom generator = new SecureRandom();
    Map<String, Integer> wordMap = new HashMap<String, Integer>();

    // pick random sleep time between 0 and 5 seconds
    private  int sleepTime;
    private  String taskName;
    private int n;
    public NGramRepositoryFromFileImpl() {}
    public NGramRepositoryFromFileImpl(String taskName ){
        this.taskName = taskName;
        // pick random sleep time between 0 and 5 seconds
        sleepTime = 2000 + generator.nextInt(5000); // milliseconds;
    }

    @Override
    public void run() {
        try // put thread to sleep for sleepTime amount of time
        {
            System.out.printf("%s going to sleep for %d milliseconds (thread: %s).%n",
                    taskName, sleepTime, Thread.currentThread().getName());
            Thread.sleep(sleepTime); // put thread to sleep
            // getAll();
        }
        catch (InterruptedException exception)
        {
            exception.printStackTrace();
            Thread.currentThread().interrupt();  // re-interrupt the thread
        }

        // print task name
        System.out.printf("%s done sleeping%n", taskName);
    }

    public void incCount(String word) {
        Integer oldCount = wordMap.get(word);
        wordMap.put(word, oldCount == null ? 1 : oldCount + 1);
    }

    double getCosineSimilarityWith(NGramRepositoryFromFileImpl otherVector) {
        double innerProduct = 0;
        for (String w : this.wordMap.keySet()) {
            innerProduct += this.getCount(w) * otherVector.getCount(w);
        }
        return innerProduct / (this.getNorm() * otherVector.getNorm());
    }

    double getNorm() {
        double sum = 0;
        for (Integer count : wordMap.values()) {
            sum += count * count;
        }
        return Math.sqrt(sum);
    }

    int getCount(String word) {
        return wordMap.containsKey(word) ? wordMap.get(word) : 0;
    }
}
