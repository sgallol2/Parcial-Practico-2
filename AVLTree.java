import java.util.Scanner;

class Node{
    int valor;
    int altura;
    Node izquierda;
    Node derecha;

    // constructor
    Node(int valor){
        this.valor = valor;
        this.altura = 1;
        this.izquierda = null;
        this.derecha = null;
    }
}
class AVLTree{
    Node raiz;

    // metodo para tomar la altura de un nodo
    int getAltura(Node nodo){
        if(nodo == null){
            return 0;
        }
        return nodo.altura;
    }

    // metodo para acltualizar la altura de un nodo
    void nuevaAltura(Node node) {
        if (node == null) {
            return;
        }
        int altuIzq = getAltura(node.izquierda);
        int altuDere = getAltura(node.derecha);
        
        // comparar las dos alturas
        if (altuIzq > altuDere) {
            node.altura = altuIzq + 1;
        } else {
            node.altura = altuDere + 1;
        }
    }

    // metodo para obtener el factor de balance
    int getBalance(Node nodo){
        if(nodo == null){
            return 0;
        }
        return getAltura(nodo.izquierda) - getAltura(nodo.derecha);
    }

    // metodo para rotar a la derecha (este metodo recibe el nodo desbalanceado)
    Node rotarDerecha(Node desBalenced){
        Node hijoIzq = desBalenced.izquierda;
        Node nietoDere = hijoIzq.derecha;

        // rotacion
        hijoIzq.derecha = desBalenced;
        desBalenced.izquierda = nietoDere;

        // actualizar alturas
        nuevaAltura(desBalenced);
        nuevaAltura(hijoIzq);

        return hijoIzq; // el nuevo nodo raiz
    }

    // metodo para rotar a la izquierda (este metodo recibe el nodo desbalanceado)
    Node rotarIzquierda(Node desbalanced) {
        Node hijoDere = desbalanced.derecha;
        Node nietoIzq = hijoDere.izquierda;
    
        // rotacion
        hijoDere.izquierda = desbalanced;
        desbalanced.derecha = nietoIzq;
    
        // actualizar alturas
        nuevaAltura(desbalanced);
        nuevaAltura(hijoDere);
    
        return hijoDere; // el nuevo nodo raiz
    }

    // metodo para balancear el arbol
    Node balancear(Node node) {
        if (node == null) {
            return null;
        }
        nuevaAltura(node);
        int balance = getBalance(node);

        // verificacion desbalance Izquierda-Izquierda 
        if (balance > 1 && getBalance(node.izquierda) >= 0) {
            return rotarDerecha(node);
        }

        // verificacion desbalance Izquierda-Derecha 
        if (balance > 1 && getBalance(node.izquierda) < 0) {
            node.izquierda = rotarIzquierda(node.izquierda);
            return rotarDerecha(node);
        }

        // verificacion desbalance Derecha-Derecha 
        if (balance < -1 && getBalance(node.derecha) <= 0) {
            return rotarIzquierda(node);
        }

        // verificacion desbalance Derecha-Izquierda 
        if (balance < -1 && getBalance(node.derecha) > 0) {
            node.derecha = rotarDerecha(node.derecha);
            return rotarIzquierda(node);
        }

        return node;
    }

    // metodo para insertar un nodo
    Node insertar(Node node, int valor) {
        if (node == null) {
            return new Node(valor);
        }

        if (valor < node.valor) {
            node.izquierda = insertar(node.izquierda, valor);
        } else if (valor > node.valor) {
            node.derecha = insertar(node.derecha, valor);
        } else {
            return node;  // si el nodo ya existe, retornamos el nodo
        }

        return balancear(node);
    }

    // este metodo insert llama la metodo isertar para hacer mas facil la insercion 
    void insert(int value) {
        raiz = insertar(raiz, value);
    }

    // metodo para encontrar el nodo con el valor mínimo
    Node minValueNode(Node node) {
        Node actual = node;
        while (actual.izquierda != null) {
            actual = actual.izquierda;
        }
        return actual;
    }

     // metodo para eliminar un nodo
    Node Eliminar(Node node, int valor) {
        if (node == null) {//manejar excepciones
            return node;
        }

        if (valor < node.valor) {
            node.izquierda = Eliminar(node.izquierda, valor);
        } else if (valor > node.valor) {
            node.derecha = Eliminar(node.derecha, valor);
        } else {
            if (node.izquierda == null || node.derecha == null) {
                Node temp;
                if (node.izquierda != null) {
                    temp = node.izquierda;
                } else {
                    temp = node.derecha;
                }
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                Node temp = minValueNode(node.derecha);
                node.valor = temp.valor;
                node.derecha = Eliminar(node.derecha, temp.valor);
            }
        }
        return balancear(node);
    }

    // metod0 para Eliminar un nodo de forma mas facil
    void eliminar(int value) {
        raiz = Eliminar(raiz, value);
    }

    void Preorder(Node node) {
        if (node != null) {
            System.out.print(node.valor + " "); // raíz
            Preorder(node.izquierda); // izquierda
            Preorder(node.derecha); // derecha
        }
    }

    void preorder() {
        Preorder(raiz);
    }

    // metodo para imprimir el arbol en inorder
    void Inorder(Node node) {
        if (node != null) {
            Inorder(node.izquierda); //izquierda
            System.out.print(node.valor + " ");  // raiz
            Inorder(node.derecha); // derecha
        }
    }

    // metodo para hacer mas facil la impresion del arbol
    void inorder() {
        Inorder(raiz);
    }

    void Postorder(Node node) {
        if (node != null) {
            Postorder(node.izquierda); // izquierda
            Postorder(node.derecha); // derecha
            System.out.print(node.valor + " "); // raíz
        }
    }

    void postorder() {
        Postorder(raiz);
    }

    // AQUÍ COMIENZA EL CODIGO DE PRUEBA
    // CREAREMOS UN ARBOL AVL Y AGREGAREMOS ALGUNOS NODOS
    public static void main(String[] args) {

        // caso de prueba 1 insersion orden ascendente
        AVLTree tree = new AVLTree();

        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.preorder();
        // resultado esperado: 2 1 4 3 5
        System.out.println();


        //caso de prueba 2 insercion orden descendente
        AVLTree tree2 = new AVLTree();  
        tree2.insert(50);
        tree2.insert(40);
        tree2.insert(30);
        tree2.insert(20);
        tree2.insert(10);
        tree2.inorder();
        // resultado esperado: 10 20 30 40 50
        System.out.println();


        //caso de prueba 3 insercion aleatoria
        AVLTree tree3 = new AVLTree();
        tree3.insert(10);
        tree3.insert(20);
        tree3.insert(30);
        tree3.insert(15);
        tree3.insert(25);
        tree3.inorder();
        // resultado esperado: 10 15 20 25 30
        System.out.println();

        //caso de prueba 4 insercion con duplicados
        AVLTree tree4 = new AVLTree();
        tree4.insert(10);
        tree4.insert(20);
        tree4.insert(30);
        tree4.insert(20);
        tree4.insert(30);
        tree4.postorder();
        // resultado esperado: 10 30 20
        System.out.println();

        //caso de prueba 5 eliminacion de un nodo
        AVLTree tree5 = new AVLTree();
        tree5.insert(1);
        tree5.insert(2);
        tree5.insert(3);
        tree5.insert(4);
        tree5.insert(5);
        tree5.eliminar(2);
        tree5.preorder();
        // resultado esperado: 3 1 4 5
        System.out.println();

        //caso de prueba 6 rotacion simple izquierda
        AVLTree tree6 = new AVLTree();
        tree6.insert(30);
        tree6.insert(40);
        tree6.insert(50);
        tree6.inorder();
        // resultado esperado: 30 40 50
        System.out.println();

        //caso de prueba 7 rotacion simple derecha
        AVLTree tree7 = new AVLTree();
        tree7.insert(50);
        tree7.insert(40);
        tree7.insert(30);
        tree7.postorder();
        // resultado esperado: 30 50 40
        System.out.println();

        //caso de prueba 8 rotacion izquierda-derecha
        AVLTree tree8 = new AVLTree();
        tree8.insert(50);
        tree8.insert(30);
        tree8.insert(40);
        tree8.inorder();
        // resultado esperado: 30 40 50
        System.out.println();

        //caso de prueba 9 rotacion derecha-izquierda
        AVLTree tree9 = new AVLTree();
        tree9.insert(30);
        tree9.insert(50);
        tree9.insert(40);
        tree9.preorder();
        // resultado esperado: 40 30 50

        Scanner scanner = new Scanner(System.in);
        AVLTree avl = new AVLTree();
    
        while (true) {
            System.out.println("\nAVL Tree\n"
                    + "\n\t1) Insertar nodo"
                    + "\n\t2) Eliminar nodo"
                    + "\n\t3) Recorrido preorden"
                    + "\n\t4) Recorrido inorden"
                    + "\n\t5) Recorridoo postorden"
                    + "\n\t6) Exit");
            System.out.print("\nElige una opcion: ");
            int opc = scanner.nextInt();
    
            switch (opc) {
                case 1:
                    System.out.print("\nIngresar el valor de nodo: ");
                    int node = scanner.nextInt();
                    avl.insert(node);
                    break;
                case 2:
                    System.out.print("\nIngresar el valor del nodo a eliminar: ");
                    node = scanner.nextInt();
                    avl.eliminar(node);
                    break;
                    case 3:
                        System.out.print("Recorrido preorden ");
                        avl.preorder();
                        System.out.println();
                        break;
                    case 4:
                        System.out.print("Recorrido inorden ");
                        avl.inorder();;
                        System.out.println();
                        break;
                    case 5:
                        System.out.print("Recorrido postorden ");
                        avl.postorder();
                        System.out.println();
                        break;
                    case 6:
                    scanner.close();
                    return;
                    default:
                        System.out.println("Opción inválida. Por favor ingrese otro numero");
                }
            }
        } 
    }
