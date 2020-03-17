				
// |**********************************************************************;
// * Project           : modelling-complex-software-systems-1a
// *
// * Program name      : checker.java
// *
// * Author            : Uvin Abeysinghe
// *
// * Date created      : 20200317
// *
// * Purpose           : To check if assignemnt solution is correct.
// *
// * Revision History  :
// *
// * Date        Author             Ref 
// * 20200317  Uvin Abeysinghe      1       
// *
// |**********************************************************************;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Check {

 public static void main(String[] args) {

    HashMap<Integer, Integer[]> data = new HashMap<Integer, Integer[]>();
    Boolean isKingArthurPresent = false;
    int newQuestID = 1;
    ArrayList<Integer> newAgenda = new ArrayList<Integer>();
    ArrayList<Integer> oldAgenda = new ArrayList<Integer>();
    int noMingling = 0;


     BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
     String x = "null";


    while( x != null )
    {
    try{
        
        x = f.readLine();
        if(x == null){
            continue;
        }
        System.out.println(x);
        String Knight = checkfor("^Knight\\s\\d*", x);
        if (Knight != null){

            Knight=Knight.replaceAll("^Knight\\s", "");
            Integer knightID=Integer.parseInt(Knight);


            if( data.get(knightID) == null){
                // 0 == false , 1 = true
                //  entered the meeting room |  quest ID | questCompleted | ismingling
                Integer[] temp = new Integer[]{0, 0, 1, 0};
                data.put(knightID, temp);
            }

            Integer[] states = data.get(knightID);

            // if enters the great hall
            if(checkfor("enters Great Hall", x) != null){



                if(states[0] == 1 || states[2] == 0 || states[3] == 1 || isKingArthurPresent ){
                    return;
                }
                states[0] = 1;
                states[3] = 1; 
                data.put(knightID, states);
                noMingling++;
                continue;

            }
            // sits at the round table
            if(checkfor("sits at the Round Table", x) != null){

                if(states[0] == 0 || states[2] == 0 || states[3] == 0  ){
                    return;
                }
                states[3] = 0; 
                data.put(knightID, states);
                noMingling--;
                continue;

            }
            // stands from the round table
            if(checkfor("stands from the Round Table", x) != null){

                if(states[0] == 0 || states[2] == 1 || states[3] == 0 || !isKingArthurPresent ){
                    return;
                }
                states[3] = 1; 
                data.put(knightID, states);
                continue;

            }

                        // knight exits
            if(checkfor("exits", x) != null){

                
                if(states[0] == 0 || states[2] == 1 || states[3] == 0 ){
                    return;
                }
                states[0] = 0;
                states[3] = 0;
                data.put(knightID, states);
                continue;

            }

            


            String Quest = checkfor("Quest\\s\\d*", x);
            if ( Quest != null ){
                Quest=Quest.replaceAll("Quest\\s", "");
                Integer QuestID=Integer.parseInt(Quest);

                // acquires
                if(checkfor("acquires", x) != null){


                    // check loosened.
                    if(states[0] == 0 || (states[2] == 1 && false)|| states[3] == 1 || !isKingArthurPresent ){
                        return;
                    }


                    if( newAgenda.size() <= 0){
                        return;
                    }
                    

                    int t = newAgenda.remove(0);
                    if( QuestID != t ){
                        return;
                    }



                    states[2] = 0;
                    states[1] = QuestID;
                    states[3] = 1; 
                    data.put(knightID, states);
                    continue;

                }  
                
                // releases
                if(checkfor("releases", x) != null){


                    int t = states[1];
                    if(states[0] == 0 || t!=QuestID || states[2] == 0 || states[3] == 1 || !isKingArthurPresent ){
                        return;
                    }

                    states[2] = 0;
                    data.put(knightID, states);
                    continue;


                }
                // sets off to complete
                if(checkfor("sets off to complete", x) != null){

                    continue;

                }   
                

                
                // completes the quest
                if(checkfor("completes", x) != null){


                    int t = states[1];
                    if(states[0] == 1 || t!=QuestID || states[2] == 1 || states[3] == 1 ){
                        return;
                    }
                    

                    oldAgenda.add(QuestID);


                    states[2] = 1;
                    data.put(knightID, states);
                    continue;

                }

            }


        }



    


        String Quest = checkfor("^Quest\\s\\d*", x);
        if ( Quest != null ){
            Quest=Quest.replaceAll("^Quest\\s", "");
            Integer QuestID=Integer.parseInt(Quest);

            if(checkfor("added to New Agenda", x) != null){
                if (QuestID != newQuestID){
                    return;
                }
                newAgenda.add(QuestID);
                newQuestID++;
                continue;

            }      

            if(checkfor("removed from Complete Agenda", x) != null){


                oldAgenda.remove(0);
                continue;

            }
            
        }

        if(checkfor("King Arthur enters the Great Hall.", x) != null){
            if (isKingArthurPresent == true){
                return;
            }
            isKingArthurPresent = true;
            continue;
        }
        if(checkfor("King Arthur exits the Great Hall", x) != null){
            if (isKingArthurPresent == false){
                return;
            }
            isKingArthurPresent = false;
            continue;
        }

        if(checkfor("Meeting begins", x) != null){
            if (isKingArthurPresent == false || noMingling != 0){
                return;
            }
            continue;


        }

        if(checkfor("Meeting ends", x) != null){
            if (isKingArthurPresent == false){
                return;
            }
            continue;



        }


    }
    catch (IOException e) {e.printStackTrace();}
    System.out.println(x);
    }
    System.out.println("Successful");
    }

    public static String checkfor( String pattern, String line ) {
 
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
  
        // Now create matcher object.
        Matcher m = r.matcher(line);
        if (m.find( )) {
           return m.group(0) ;
        }else {
           return null;
        }
     }




}
