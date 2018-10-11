#include<iostream>
using namespace std;

class Node {

private:
    int key;
    int degree;
    
    Node *parent, *child;
    
    Node *next, *prev;

    bool isMarked;

public:
    Node(int value) {
        degree = 0;
        key = value;
        isMarked = false;
        next = prev = this;
        parent = child = NULL;
    }

    int getValue() {
        return key;
    }

    int setValue(int value) {
        this->key = value;
    }

    Node* getNext() {
        return this->next;
    }

    void setNext(Node *next) {
        this->next = next;
    }

    Node* getPrev() {
        return this->prev;
    }

    void setPrev(Node *prev) {
        this->prev = prev;
    }

    void increaseDegree() {
        degree++;
    }

};

class FiboHeap {

private:
    Node *Hmin;
    int n;


public:
    FiboHeap() {
        this->Hmin = NULL;
        this->n = 0;
    }

    int getn() {
        return n;
    }

    int setn(int n) {
        this->n = n;
    }

    int getMinimumValue() {
        return (*Hmin).getValue();
    }

    Node* getMinimumNode() {
        return Hmin;
    }

    void insert(int value) {
        Node *newNode = new Node(value);
        fiboInsert(newNode);
    }

    void fiboInsert(Node *newNode) {
        if(this->Hmin == NULL)
            this->Hmin = newNode;



        else {
            //cout<<"here\n";
            Node *temp = (*Hmin).getNext();

            (*Hmin).setNext(newNode);
            (*newNode).setNext(temp);
            (*newNode).setPrev(Hmin);

            // free temp here
        }

        if((*Hmin).getValue() > (*newNode).getValue()) {
            Hmin = newNode;
        }

        (*Hmin).increaseDegree();
    }

    FiboHeap* fiboUnion(FiboHeap* h1, FiboHeap* h2) {
        FiboHeap *h = new FiboHeap();

        h = h1;

        (*h).fiboInsert((*h2).getMinimumNode());

        
        if(h1 == NULL || (h2 != NULL && 
            (*h2).getMinimumNode()->getValue() < 
                (*h1).getMinimumNode()->getValue()) )
            
            h = h2;

        (*h).setn((*h1).getn() + (*h2).getn());

        return h;
    }

    void print() {
        printList(Hmin);
    }

    void printList(Node *Hmin) {
        cout<<(*Hmin).getValue()<<" ";
        Node *iterator = Hmin->getNext();
        while(iterator->getNext() != Hmin) {
            cout<<(*iterator).getValue()<<" ";
            iterator = (*iterator).getNext();
        }
        cout<<(*iterator).getValue();
        cout<<endl;
    }

};

int main() {
    FiboHeap *heap = new FiboHeap();

    (*heap).insert(1);
    (*heap).insert(2);
    (*heap).insert(3);
    (*heap).insert(-1);

    FiboHeap *heap2 = new FiboHeap();
    (*heap2).insert(-2);
    (*heap2).insert(0);

    cout<<"yahan\n";
    FiboHeap *newHeap = (*heap).fiboUnion(heap2, heap);



    //cout<<(*heap).getMinimum();
    (*heap).print();
    (*heap2).print();
    (*newHeap).print();
    cout<<(*newHeap).getMinimumValue();

}
