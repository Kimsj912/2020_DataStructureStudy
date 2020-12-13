package week06;

import java.util.Vector;

public class DisjointSetTree {
   
   private static Vector<Dset> treeBucket = new Vector<Dset>();
   
   public Dset makeSet (int i) {
      Dset set = new Dset();
      Node newNode = new Node(i);
      set.makeSet(newNode);
      treeBucket.add(set);
      return set;
   }
   private void union(Dset dset, Dset dset2) {
      if (dset.equals(dset.union(dset2))) {
         int winnerIdx = treeBucket.indexOf(dset) ;
         int loserIdx = treeBucket.indexOf(dset2);
         treeBucket.set(loserIdx, treeBucket.get(winnerIdx));
      }else {
         int winnerIdx = treeBucket.indexOf(dset2) ;
         int loserIdx = treeBucket.indexOf(dset);
         treeBucket.set(loserIdx, treeBucket.get(winnerIdx));
      }
   }
   
   public String toString() {
      String returnVal = "";
      for( Dset s: this.treeBucket) {
         returnVal += "<set> \n"+ s.toString()+"\n";
      }
      return returnVal;
   }
   
   private Node findset(int i) {
      for(Dset s:treeBucket) {
         boolean iscontain = s.findset(i);
         if(iscontain) {
            return s.root;
         }
      }
      return null;
   }

   
   // Tree Set 
   private class Dset{
      Node root;
      int rank;
      
      public Dset() {
         root = null;
      }
      
      public Dset makeSet (Node n) {
         root = n;
         rank = 1; 
         return this;
      }
      
      public Dset union (Dset another) {
         if(this.rank >= another.rank) { // 해당 트리의 높이가 더 클경우
            another.root.parent = this.root;
            this.root.children.add(another.root);
            // root setting
            another.root = this.root;
            // rank setting 
            this.rank = countRank(this.root, 0);
            another.rank = this.rank;
            return this;
         } else { // 해당 트리의 높이가 더 작을 경우
            this.root.parent = another.root;
            another.root.children.add(this.root);
            // root setting
            this.root = another.root;
            // rank setting 
            another.rank = countRank(another.root, 0);
            this.rank = another.rank;
            return another;
         }
      }
      public int countRank(Node n, int count) {
         if(n.children.isEmpty()) count = 1;
         for (Node subtmp : n.children) {
            int subc = countRank(subtmp,count)+1;
            count = count > subc ? count : subc;
         }
         return count;
      }
      
      public boolean findset(int i) { // 정해진 set의 root를 리턴
         boolean iscontain = finding(root, i);
         if (iscontain) return true;
         else return false;
      }
      
      public boolean finding(Node tmp, int i) {
         if (tmp.key == i) return true;
         boolean isContain = false;
         for(Node subtmp : tmp.children) {
            if (finding(subtmp,i)) return true;
         }
         return isContain;

      }
      
      public String toString () {
         if(root == null) return null;
         String returnVal = "";
         returnVal += iter(root);
         return "set contains : ["+returnVal +"] \n rank : "+rank;
      }
      
      public String iter(Node tmp) {
         String returnVal = tmp.toString();
         for(Node subtmp : tmp.children) {
            returnVal+=", "+iter(subtmp);
         }
         return returnVal;
      }
   }
   
   // Node
   private class Node {
      int key;
      Node parent; 
      Vector<Node> children;
      
      public Node(int k) {
         key = k;
         parent = null;
         this.children = new Vector<Node>();
      }
      public String toString() {
         String returnval = "";
         return returnval + key;
      }
   }
   
   public static void main (String[] args) {
      DisjointSetTree a = new DisjointSetTree();

      System.out.println("------------Create Test : ");
      a.makeSet(1);
      a.makeSet(2);
      a.makeSet(3);
      a.makeSet(4);
      a.makeSet(5);
      a.makeSet(6);
      System.out.println(a.toString());
      
      System.out.println("-----------union Test : ");
      System.out.println("1 + 2 >>>> ");
      a.union(treeBucket.get(0), treeBucket.get(1));
      System.out.println(a.toString());
      System.out.println("4 + 5 >>>> ");
      a.union(treeBucket.get(3), treeBucket.get(4));
      System.out.println(a.toString());
      System.out.println("1 + 5 >>>> ");
      a.union(treeBucket.get(1), treeBucket.get(4));
      System.out.println(a.toString());
      
      
      System.out.println("-----------find set Test : ");
      System.out.println("findset(2) : "+a.findset(2).toString());
      System.out.println("findset(3) : "+a.findset(3).toString());
      System.out.println("findset(5) : "+a.findset(5).toString());
      
   }
}