# AdvanceJava_FirstAssignment

![image](https://user-images.githubusercontent.com/86987951/221179535-01423ff3-d67c-4dd7-ba55-efd332946f17.png)
![image](https://user-images.githubusercontent.com/86987951/221179681-c92cdbe8-9cac-480c-8861-8f5df163f78f.png)
![image](https://user-images.githubusercontent.com/86987951/221179756-22be6082-99ba-4b6c-b3b2-ca73bff25877.png)
![image](https://user-images.githubusercontent.com/86987951/221180626-02f77f63-49e2-4e4a-a2cf-0f42ee768618.png)
![image](https://user-images.githubusercontent.com/86987951/221180818-bdd31d7a-88da-42a0-a9a1-db0ab2af75d7.png)
![image](https://user-images.githubusercontent.com/86987951/221180290-b75c3630-bb57-450d-b562-6de44a602f05.png)

# Report
# Introduction 

The program is designed to read the .txt files for each language dynamically, so if we add other files, it will read them. 
The idea of the task was to have a main text file called "Mystery.txt" that would be the detected language, 
and several folders containing content related to different languages.

We have constructed the program language model for two languages English and Albanian and when asked to classify the given
text, it find the language similarity between all language subfolders. We have tried 
to achieve this task using streams and lambdas. And also process the content via 
multithreading. Each of the categories have the following Classes, methods and 
respective operations.

# Implementation

The program is implemented mainly with the followingclasses, and methods respectively. 

The MainAppOfNGramer class includes following methods and operations: The execute() method is responsible for calling and executing all 
unigram, twogram, cosineSimilarity and threads methods.

The unigram() method, separate the sentences in single words and we count the occurrence of each word in the text. To do this we have used a for each and 
while loops. Also, for navigating the list we have used map Iterator.

The bigram() method, separate the word in two pair letters and count the occurrence of each pair. We have removed all white spaces, punctuation, and have 
standardize letters into lower case. Moreover, with a for each we calculate the distribution frequency of each pair of letters.

The similarityBetweenDocs() method is used to find the similarity between docs. It saves the content of each file and then, reads text and compare the
content. After that with getCosineSimilarityWith() which take as parameter languagesfiles it calculates the cosine similarity of file mystery with first language 
and then with the second language subfolder.

The readFiles() method read files directoryPath, and fileExtensionToRead and enable us to add dynamically as many files as we want.

The NGramRepositoryFromFileImpl class is used to calculate the similarity of languages given in respective text files as we described above. 
Method to complete this task are run() -contains the code that the thread will execute.

incCount() method, getCosineSimilarityWith() method, getNorm() method, and getCount method. 
In addition, this class is intended to be executed by a thread that’s why it also implements runnable interface.

The FileContent class saves the content of all files for each languages, with the help of geter and seter methods.
The language class help us to read the path of language folders. The NGram class help us make the calculation for Bigram calculation. 
It implements iterator interface. We have two overridden method here, hasNext() method and Next() method which make possible to return two pair of letters, 
BiGram length -2 letters. The class FileSimilarity help us to save the similarity between files.

# Conclusion

The natural language processing Java program, successfully preform the task of language classification. And construct two language models in Java, 
Albanian, English. It also calculates the document distance, otherwise known as cosine similarity of each language model. The program makes use of streams and 
lambdas in order to concisely process the text data and update the language models via threads. This enables the program to perform language classification effectively 
and efficiently.
