import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Main 
{
    public static void  apiget (int rannum) throws IOException
    {
        URL myURL = new URL("http://numbersapi.com/" + rannum + "/trivia");
        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder text = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
        {
            text.append(inputLine);
        }
        in.close();

        connection.disconnect();

        charactersFrequency(text, rannum);
    }

    public static void charactersFrequency(StringBuilder text, int rannum)
    {
        System.out.println("\nGET http://numbersapi.com/" + rannum + "/trivia\n" + text);

        Map<Character,Integer> frequencies = new HashMap<>();
        
        for (char ch : text.toString().toCharArray())
        {
            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);
        }
        int Sum = 0;
        System.out.println("\nЧастоты: ");
        for (Map.Entry<Character,Integer> entry : frequencies.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            Sum += entry.getValue();
        }

        double averagefreq = (double) Sum / frequencies.size();

        System.out.println("\nСреднее значение частоты " + Sum + "/" + frequencies.size() + " = " + averagefreq);

        System.out.println("\nСимволы, которые соответствуют условию наиболее близкого значения частоты к среднему значению: ");
        for (Map.Entry<Character,Integer> entry : frequencies.entrySet())
        {
            if(entry.getValue() == Math.round(averagefreq))
            {
                System.out.print(entry.getKey() + "(" + (int) entry.getKey() + ") " );
            }
        }
    }

    public static void main(String[] args) throws IOException 
    {
        int rannum = ThreadLocalRandom.current().nextInt(0, 1000);
        apiget(rannum);
    }
}