#include <graphics.h>
#include<vector>
#include<stack>
#include<iostream>
using namespace std;

struct state {
   int n;
   char X;
   char Y;

   state(int n, int X, int Y) {
      this->n = n;
      this->X = X;
      this->Y = Y;
   }
};

int l=100, t=100, r=105, b=300;

vector<state> callStack;
vector<int> rod1, rod2, rod3;

void draw_bars() {
   bar(l, t, r , b);
   bar(l+200, t, r+200, b);
   bar(l+400, t, r+400, b);
}


//multiplier decides which peg to move on
//Initial disk size is 80 pixel (-40 to +40)
//i decides size of ith disc

void draw_discs(vector<int> peg, int multiplier) {

   for(int i=0; i<peg.size(); ++i) {
      cout<<"inside draw_discs"<<endl;
      setcolor(peg[i]);
      rectangle(l+200*multiplier-40-peg[i]*5, b-(i-1)*5, r+200*multiplier+40+peg[i]*5, b-(i)*5);
   }

}

void draw() {

   draw_bars();

   draw_discs(rod1, 0);
   draw_discs(rod2, 1);
   draw_discs(rod3, 2);

   outtextxy(l + 100, t + 325, "Tower of Hanoi");
   delay(1000);
}

void visualise() {
   for(int i=0; i<callStack.size(); ++i) {

      /* 
         n, X, Y represents state. Pop nth disk from disk X.
         Push nth disk to disk Y.
      */

      if(callStack[i].X == 'A')
         rod1.pop_back();
      else if(callStack[i].X == 'C')
         rod2.pop_back();
      else
         rod3.pop_back();


      if(callStack[i].Y == 'A')
         rod1.push_back(callStack[i].n);
      else if(callStack[i].Y == 'C')
         rod2.push_back(callStack[i].n);
      else
         rod3.push_back(callStack[i].n);

      draw();
      
      if(i != callStack.size()-1)
         cleardevice();

   }

   delay(5000);
   closegraph();
}

void towerOfHanoi(int n, char s, char d, char aux) {
   if(n==0)
      return;
   towerOfHanoi(n-1, s, d, aux);
   callStack.push_back(state(n, s, aux));
   cout<<"Move "<<n<<"th disc from" <<s<<" to " <<aux<<endl;
   towerOfHanoi(n-1, d, s, aux);
   callStack.push_back(state(n, aux, d));
   cout<<"Move "<<n<<"th disc from "<<aux <<" to "<<d<<endl;
   towerOfHanoi(n-1, s, d, aux);
}

int main() {
   int n;
   cin>>n;

   for(int i=n; i>0; i--)
      rod1.push_back(i);
   towerOfHanoi(n, 'A', 'B', 'C');

   int gd = DETECT, gm;
   initgraph(&gd, &gm, NULL);
   draw();
   visualise();
   
   return 0;
}