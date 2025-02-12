import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// processing forum how to edit a text file
import java.io.FileWriter;
import java.io.BufferedWriter;

public class AutoFill{
    //Arrays and array lists used throught the project
    private ArrayList<String> words;
    private ArrayList<Integer> counts;
    private ArrayList<String> text = new ArrayList<String>();
    private String[] predict = new String[3];
    
    

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
        String letters1 = letters.toLowerCase();
        for(int i=0; i<words.size();i++){
            if(words.get(i).length()>=letters.length()){
                if(letters1.equals(words.get(i).substring(0,letters.length()))){
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

    public void changeInts(String word){
        int index = words.indexOf(word);
        if(index != -1){
        int value = counts.get(index);
        if(value > 1000){
            value = value + (10000-value)/1000;
        }
        else if(value > 100){
            value = value + (1000-value)/10;
        }
        else{
            value += 101-value;
        }
        counts.set(index,value);
        System.out.println(counts.get(index));
        }
    }



    

    public AutoFill(){
        text.add(" ");
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

        JPanel buttons = new JPanel(new GridLayout(1,3));
        JButton b1 = new JButton("B1");
        JButton b2 = new JButton("B2");
        JButton b3 = new JButton("B3");
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        panel.add(buttons,BorderLayout.SOUTH);
        
        
        // JButton b1 = new JButton("B1");
        // //panel.add(b1 , BorderLayout.SOUTH);
        //  b1.setBounds(10,100,120,30);
        // frame.add(b1);
        // JButton b2 = new JButton("B2");
        // //panel.add(b2 , BorderLayout.WEST);
        // b2.setBounds(140,100,120,30);
        // frame.add(b2);
        // JButton b3 = new JButton("B3");
        // b3.setBounds(270,100,120,30);
        // frame.add(b3);
        frame.add(panel);
        
        
        
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String currentText = textArea.getText();
                int t = text.size();
                text = new ArrayList<String>();
                int x=0;
                for(int i=0; i<currentText.length(); i++){
                    if(currentText.substring(i,i+1).equals(" ")){
                        text.add(currentText.substring(x,i));
                        x=i+1;
                    }
                }
                text.add(currentText.substring(x));
                //System.out.println(text);
                predict = predictText(text.get(text.size()-1));
               // System.out.println(predict[0]+" "+predict[1]+" "+predict[2]);
                b1.setText(predict[0]);
                b2.setText(predict[1]);
                b3.setText(predict[2]);

                System.out.println(t);
                System.out.println(text.size());
                System.out.println(text);
                if(text.size()>t && t!=2){
                    changeInts(text.get(text.size()-2).substring(0));
                } else if(text.size()>t){
                    changeInts(text.get(text.size()-2));
                }
                
                

                // currentText = textArea.getText();
                // if(currentText.substring(currentText.length()-1).equals(" ")) {
                //     int checkLength = 1;
                //     boolean notWord = true;
                //     while(notWord){
                //         if(currentText.substring(currentText.length()-checkLength-1,currentText.length()-checkLength).equals(" ")){
                //             notWord=false;
                //         }else{
                //             checkLength++;
                //         }
                //     }
                //     changeInts(substring(currentText.length()-checkLength+1,currentText.length()-1));
                // }
                
            }
        });
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t = "";
                text.set(text.size()-1,predict[0]);
                for(int i=0;i<text.size();i++){
                    t += text.get(i)+ " ";
                }
                textArea.setText(t);
                changeInts(predict[0]);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t = "";
                text.set(text.size()-1,predict[1]);
                for(int i=0;i<text.size();i++){
                    t += text.get(i)+ " ";
                }
                textArea.setText(t);
                changeInts(predict[1]);
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t = "";
                text.set(text.size()-1,predict[2]);
                for(int i=0;i<text.size();i++){
                    t += text.get(i)+ " ";
                }
                textArea.setText(t);
                changeInts(predict[2]);
            }
        });
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new AutoFill();
    }
}