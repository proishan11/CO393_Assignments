#include<iostream>
using namespace std;

int main() {
	int size;

	cin>>size;

	int a[size];
	int count0=0, count1=0;

	for(int i=0; i<size; ++i) {
		cin>>a[i];
		if(a[i])
			count1++;
		else
			count0++;
	}

	cout<<count0<<" "<<count1<<endl;

	for(int i=0; i<size; ++i){
		if(i<count0)
			a[i] = 0;
		else
			a[i] = 1;
	}

	for(int i=0; i<size; ++i)
		cout<<a[i]<<" ";
	cout<<endl;
}