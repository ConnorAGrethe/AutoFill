import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AutoFill{
    
    private ArrayList<String> words;
    private ArrayList<Integer> counts;
    private ArrayList<String> text;
    

    //JFrame variables
    private JFrame frame;
    private JTextArea textArea;
    private JButton b1;
    private JButton b2;
    private JButton b3;

    /* finds 3 most common results with the following letters
    // iterates and finds all possibles words
    // after loop, finds 3 highest counts as possible word choices
    */
    public String[] predictText(String letters){   
        ArrayList<String> tempWords = new ArrayList<String>();
        ArrayList<Integer> tempInts = new ArrayList<Integer>();
        for(int i=0; i<words.size();i++){
            if(words.get(i).length()>=letters.length()){
                if(letters.equals(words.get(i).substring(0,letters.length()))){
                    tempWords.add(words.get(i));
                    tempInts.add(counts.get(i));
                }
            }
        }
        int[] peaks = {0,0,0};
        String[] peakWords = new String[3];
        for(int i=0; i<tempWords.size();i++){
            for(int x=0;x<peaks.length;x++){
                if(tempInts.get(i)>peaks[x]){
                    // if(x==2){
                        peaks[x] = tempInts.get(i);
                        peakWords[x] = tempWords.get(i);
                        x+=3;
                    // }
                    // else{
                    //     peaks[x+1] = peaks[x];
                    //     peakWords[x+1] = peakWords[x];
                    //     peaks[x] = tempInts.get(i);
                    //     peakWords[x] = tempWords.get(i);
                    // }
                }
            }
            
        }
        for(int i=0; i<peakWords.length; i++){
            if(peakWords[i] == null){
                peakWords[i] = " ";
            }
        }
        return peakWords;
    } 
    // end of method

    

    public AutoFill(){
        words = FileReader.toStringArrayList("1000-common-words.txt");
        counts = FileReader.toIntArrayList("values.txt");

    //JFrame shenanigans
        frame = new JFrame("AutoFill");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(panel);
        b1 = new JButton("B1");
        b1.setBounds(50,100,95,30);
        b2 = new JButton("B1");
        b2.setBounds(150,100,95,30);
        b3 = new JButton("B1");
        b3.setBounds(250,100,95,30);
        
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String currentText = textArea.getText();
                text = new ArrayList<String>();
                int x=0;
                for(int i=0; i<currentText.length(); i++){
                    if(currentText.substring(i,i+1).equals(" ")){
                        text.add(currentText.substring(x,i));
                        x=i+1;
                    }
                }
                text.add(currentText.substring(x));
                System.out.println(text);
                String[] predict = predictText(text.get(text.size()-1));
                System.out.println(predict[0]+" "+predict[1]+" "+predict[2]);
                //b1.setText(predict[1]);
               // b2.setText(predict[2]);
               // b3.setText(predict[3]);
            }
        });
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new AutoFill();
    }
}