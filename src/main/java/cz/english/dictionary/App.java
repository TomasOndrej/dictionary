package cz.english.dictionary;

public class App 
{
    public static void main( String[] args )
    {
        FileReader fr = new FileReader();
        
        MainWindow mw = new MainWindow(fr.getResult());
    }
}
