#include<iostream>
#include<cmath>
using namespace std;

struct node {
	int key;
	int number;
	bool marked;
	bool visited;
	node *parent;
	node *child;
	node *next;
	node *prev;
	int degree;

	node(int value, int n) {
		key = value;
		number = n;
	}	
};


class FiboHeap {
	int n;
	node *h;

public:
	FiboHeap() {
		h = NULL;
	}

	node* getMin() {
		return h;
	}

	void setMin(node *hmin) {
		h = hmin;
	}

	void insertByValue(int value, int number) {
		node *newNode = new node(value, number);
		h = insert(this->getMin(), newNode);
	}

	node* insert(node* h, node* x) {
		x->degree = 0;
		x->next = x->prev = x;
		x->parent = x->child = NULL;
		x->marked = false;
		x->visited = false;

		if(h == NULL) {
			h = x;
		}
		else {
			(h->prev)->next = x;
			x->next = h;
			x->prev = h->prev;
			h->prev = x;

			if(x->key < h->key) {
				h = x;
			}
		}

		n++;
		return h;
	}

	node* fiboUnion(node *h1, node *h2) {

		FiboHeap *h = new FiboHeap;
		h->setMin(h1);
		
		node* hmin = h->getMin();
		hmin->prev->next = h2;
		h2->prev->next = hmin;

		node* temp = hmin->prev;
		hmin->prev = h2->prev;
		h2->prev = temp;

		if(h1 == NULL || (h2 != NULL && h2->key < h1->key))
			h->setMin(h2);

		return h->getMin();

	}

	node* extractMin(node* h) {
		
		cout<<"*******extractMin ******\n";
		node* z = h;

		if(z != NULL) {
			node* x;
			if(z->child != NULL)
				x = z->child;
			else x = NULL;
			node* origin = x;

			cout<<"** p1 ** \n";
			

			if(x != NULL) {
				do {
					h->prev->next = x;
					x->next = h;
					x->prev = h->prev;
					h->prev = x;

					if(x->key < h->key)
						h = x;

					x->parent = NULL;
					x = x->next;

				}while(origin != x);
			}

			cout << "*** p2 ***\n";
			z->next->prev = z->prev;
			z->prev->next = z->next;

			cout<<"*** p3 ***\n";
			if(z == z->next && z->child == NULL) {
				this->h = NULL;
			}

			else this->h = z->next;

			consolidate(h);
			this->n = this->n - 1;
		}

		return z;
	}

	void consolidate(node *h) {

		cout<<"********consolidate******* \n";

		int D = 100; 

		cout<<"D = "<<D<<endl;

		node* A[D];
		for(int i=0; i<D; ++i)
			A[i] = NULL;
		
		node* origin = h;
		node* x = h;
		do {

			int d = x->degree;
			while(A[d] != NULL) {

				cout<<"****consolidate while  ***\n";
				node* y = A[d];
				if(x->key > y->key) {
					node* temp = x;
					x = y;
					y = temp;
				}

				cout<<"(****before link***\n";
				link(h, x, y);
				cout<<"**** after link ***\n";

				A[d] = NULL;
				d++;
			}

			A[d] = x;
			x = x->next;

			cout<<"***consolidate outside while **** \n";

		}while(x != origin);

		this->h = NULL;

		cout<<"**** outside all while ***\n";

		for(int i=0; i<D; ++i) {
			if(A[i] != NULL) {
				if(this->h == NULL) {
					node* l = A[i];
					this->h = A[i];
				}
				else {
					this->h->prev->next = A[i];
					A[i]->next = this->h;
					A[i]->prev = h->prev;
					this->h->prev = A[i];

					if(A[i]->key < this->h->key)
						this->h = A[i];
				}
			}
		} 
	}

	/*int consolidate(node* H1) {
    
    int d, i;
    float f = (log(this->n)) / (log(2));
    int D = f;
    node* A[D];
    for (i = 0; i <= D; i++)
        A[i] = NULL;
    node* x = H1;
    node* y;
    node* np;
    node* pt = x;
    do
    {
        pt = pt->next;
        d = x->degree;
        while (A[d] != NULL)
        {
            y = A[d];
            if (x->key > y->key)
            {
                np = x;
                x = y;
                y = np;
            }
            if (y == H1)
                H1 = x;
            link(H1, y, x);
            if (x->next == x)
                H1 = x;
                A[d] = NULL;
            d = d + 1;
        }
        A[d] = x;
        x = x->next;
    }while (x != H1);
    this->h = NULL;
    for (int j = 0; j <= D; j++)
    {
        if (A[j] != NULL)
        {
            A[j]->prev = A[j];
            A[j]->next =A[j];
            if (this->h != NULL)
            {
                (h->prev)->next = A[j];
                A[j]->next = this->h;
                A[j]->prev = this->h->prev;
                h->prev = A[j];
                if (A[j]->key < h->key)
                h = A[j];
            }
            else
            {
                this->h = A[j];
            }
            if(this->h == NULL)
                this->h = A[j];
            else if (A[j]->key < this->h->key)
                this->h = A[j];
        }
    }
}*/

int link(node* H1, node* y, node* z) {
    cout<<"******link*******\n";
    y->prev->next = y->next;
    y->next->prev = y->prev;
    
    if (z->next == z)
        H1 = z;
    
    cout<<"****l1****\n";
    y->prev = y;
    y->next = y;
    y->parent = z;
    
    cout<<"****l2*****\n";
    if (z->child == NULL)
        z->child = y;

    cout<<"****l3****\n";
    y->next = z->child;
    y->prev = (z->child)->prev;
    ((z->child)->prev)->next = y;
    (z->child)->prev = y;
    
    cout<<"****l4****\n";
    if (y->key < (z->child)->key)
        z->child = y;
    
    z->degree++;
}
	
	node* findNode(node* h, int val) {
		if(h == NULL)
			return h;

		h->visited = true;
		node *result = NULL;
		
		if(h->key == val) {
			h->visited = false;
			return h;
		}

		else {
			if(h->child != NULL)
				result = findNode(h->child, val);
			if((h->next) -> visited != true)
				result = findNode(h->next, val);
		}
		
		h->visited = false;
		return result;

	}

	void print(node *h) {
		if(h == NULL)
			cout<<"Empty heap \n";
		else {
			cout<<h->key<<" -> ";
			node* iter = h->next;
			while(iter->next != h) {
				cout<<iter->key<<" -> ";
				iter = iter->next;
			}
			cout<<iter->key<<endl;
		}
	}


};

int main() {
	FiboHeap *h = new FiboHeap;

	// h->insert(h->getMin(), new node(1));
	// h->insert(h->getMin(), new node(2));
	// h->insert(h->getMin(), new node(3));

	h->insertByValue(1, 1);
	h->insertByValue(2, 2);
	h->insertByValue(3, 3);
	h->print(h->getMin());

	FiboHeap *h2 = new FiboHeap;
	h2->insertByValue(-1, -1);
	h2->insertByValue(-2, -2);
	h2->insertByValue(-3, -3);
	h2->print(h2->getMin());

	node* newh = h->fiboUnion(h->getMin(), h2->getMin());
	h->print(newh);
	h->setMin(newh);

	cout<<"----------\n\n";
	
	cout<<h->extractMin(h->getMin())<<endl;
	cout<<h->extractMin(h->getMin())<<endl;

	cout<<(h->findNode(h->getMin(), 1)-> key)<<endl;

	cout<<newh->key<<endl;

	//node *n = new node(1);

}