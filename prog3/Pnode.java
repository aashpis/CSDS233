package prog3;
/**
 * this class holds a character and records the total occurences
 * after a specified substring
 */
public class Pnode {
   char c; //records char 
   int count = 1; //records occurences of char

   public Pnode(char c){
    this.c = c;
   }

   public char getChar(){
    return this.c;
   }

   public int getCount(){
    return this.count;
   }
    //
   public void increment(){
    count++;
   }


}