//Kate Walker
public interface MyLists<E>{//interface with the functions that the classes need to have
    E get(int index);//function to get a value at an index
    boolean add(E value);//function to add to the list at the end
    E remove(int index);//function to remove a given index
    int getSize();//function to return the size of the list
}
class MyArrayList<E> implements MyLists<E>{
    E[] array;//making a array of type E
    int size;//int to hold the size of the array

    public MyArrayList(){//constructor for the MyArrayList class
        array = (E[]) new Object[15];//cast to type E and make the array size 15
        size = 0;//set the size to 0
    }
    public int getSize(){//function to return the size
        return size;//return the size
    }

    public E get(int index){//function to get information at a index
        if (index < 0 && index >= size){//if the index is less than 0 or greater than the size
            System.out.println("Invalid index");//print invalid index

        }
        return array[index];//return the value at the index
    }

    public boolean add(E value){//function to add to the arraylist
        if (size == array.length){//if the size equals the length of the array
            growArray();//call the growArray function
        }
        array[size] = value;//set array at the size to the value
        size++;//increment the size
        return true;//return true
    }


    public E remove(int index){//function to remove a value from the array at a specified index
        if (index < 0 && index >= size){//if the index is less than 0 or greater than or equal to the size
            System.out.println("Invalid index");//print invalid index
        }
        E value = array[index];//set value to the array at the given index
        for (int i = index + 1; i < size; i++){//loop through the values in the array
            array[i - 1] = array[i];//set the value at i-1 to the value at array i
        }
        size--;//decrement the size
        return value;//return the value
    }


    private void growArray(){//function to grow the array
        E[] new_arr = (E[]) new Object[array.length * 2];//set the new array to be 2 times the size of the original array
        for (int i = 0; i < array.length; i++){//loop through the array
            new_arr[i] = array[i];//add the values in the array to the new array
        }
        array = new_arr;//set the array to the new array
    }
}

class MyLinkedList<E> implements MyLists<E>{
    Node<E> head;//node to store the head
    int size;// int to store the size
    private static class Node<E>{//Node class
        E data;//variable to store the data
        Node<E> next;//variable to store the next node

        public Node(E value){
            data = value;//set the data to equal the value
            next = null;//set next to be null
        }
    }

    public MyLinkedList(){//linkedlist constructor
        head = null;//set head to null
        size = 0;//set size to zero
    }

    public int getSize(){//function to get the size
        return size;//return size
    }

    public E get(int index){//function to get a value at a given index
        if (index < 0 && index >= size){//if the index is less than zero or greater than or equal to the size
            System.out.println("Invalid Index");//print invalid index

        }
        Node<E> current = head;//set the current node to be the head
        for (int i = 0; i < index; i++){//loop through the list until you reach the given index
            current = current.next;//set current to equal current.next
        }
        return current.data;//return the data at current
    }

    public boolean add (E value){//function to add a value to the end of the linkedlist
        if (head == null){//if the head is null
            head = new Node<>(value);//set head to be a new node with the given value
            size++;//increment size
            return true;//return true
        }
        Node<E> previous = head;//set previous to the head
        for (int i = 0; i < size - 1; i++){//loop through the nodes in the linkedlist
            previous = previous.next;//set previous to previous.next
        }
        Node<E> Nnode = new Node<>(value);//make a new node with the value in it
        previous.next = Nnode;//set previous.next ewual to the new node
        size++;//increment the size
        return true;//return true
    }


    public E remove(int index){//function to remove a node
        if (index < 0 && index >= size){//if the index is less than zero or greater than or equal to the size
            System.out.println("Invalid index");//print invalid index
        }
        if (index == 0){//if the index is 0
            Node<E> onode = head;//set the new node to the head
            head = head.next;//set the head to equal head.next
            size--;//decrement the size
            return onode.data;//return the node's data that was removed
        }
        else{
            Node<E> previous = head;//set previous to the head
            for(int i = 0; i < index - 1; i++){//loop through until index minus 1
                previous = previous.next;//set previous to previous.next
            }
            Node<E> Onode = previous.next;//set the node to previous.next
            previous.next = Onode.next;//set previous.next to node.next
            size--;//decrement size
            return Onode.data;//return the node's data that was removed
        }
    }
}


