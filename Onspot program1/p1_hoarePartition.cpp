#include<iostream>
using namespace std;

int main() {
	
	int size;
	cin >> size;

	int a[size];

	for(int i=0; i<size; ++i){
		cin>>a[i];
	}

	int index1 = 0;
	int index2 = size-1;

	while(index1<=index2){
		if(a[index1] == 0)
			index1++;

		if(a[index2] == 1)
			index2--;

		if(a[index1]==1 && a[index2]==0 && index2>index1){
			swap(a[index1], a[index2]);
			index1++;
			index2--;
		}
	}

	for(int i=0; i<size; ++i)
		cout<<a[i]<<" ";

	cout<<endl;
}