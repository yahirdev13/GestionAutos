import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;

public class  BookManager {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String ruta = "books.db4o";
        ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), ruta);
        Book[] book = new Book[10];
        Book libro1 = null, libro2 = null, libro3 = null;
        String opc;
        try {
            book[0] = new Book("El Señor de los Anillos", "J.R.R. Tolkien", 1954);
            book[1] = new Book("Cien años de soledad", "Gabriel García Márquez", 1967);
            book[2] = new Book("El Hobbit", "J.R.R. Tolkien", 1937);
            book[3] = new Book("1984", "George Orwell", 1949);
            book[4] = new Book("Orgullo y Prejuicio", "Jane Austen", 1813);
            book[5] = new Book("El Principito", "Antoine de Saint-Exupéry", 1943);
            book[6] = new Book("El Alquimista", "Paulo Coelho", 1988);
            book[7] = new Book("El Diario de Ana Frank", "Ana Frank", 1947);
            book[8] = new Book("El Perfume", "Patrick Süskind", 1985);
            book[9] = new Book("El Nombre de la Rosa", "Umberto Eco", 1980);

            for (int i = 0; i < book.length; i++){
                db.store(book[i]);
            }

            db.close();
            db = Db4o.openFile(Db4o.newConfiguration(), ruta);

            ObjectSet libros = db.queryByExample(new Book(null, null, 0));
            while (libros.hasNext()){
                System.out.println(libros.next());
            }

            System.out.println("\n--------------------------Consulta especifica 1----------------------------------");
            libro1 = (Book) db.queryByExample(new Book("El Señor de los Anillos", null, 0)).next();
            System.out.println(libro1);
            System.out.println("---------------------------------------------------------------------------------");

            System.out.println("\n--------------------------Consulta especifica 2----------------------------------");
            libro2 = (Book) db.queryByExample(new Book("El Hobbit", null, 0)).next();
            System.out.println(libro2);
            System.out.println("---------------------------------------------------------------------------------");

            System.out.println("\n--------------------------Consulta especifica 3----------------------------------");
            libro3 = (Book) db.queryByExample(new Book("El Nombre de la Rosa", null, 0)).next();
            System.out.println(libro3);
            System.out.println("---------------------------------------------------------------------------------\n");

            libro1.setTitulo("El Señor de los Anillos: La Comunidad del Anillo");
            libro2.setTitulo("El Hobbit: Un Viaje Inesperado");
            libro3.setTitulo("El Silmarillion");

            db.store(libro1);
            db.store(libro2);
            db.store(libro3);

            System.out.println(libro1);
            System.out.println(libro2);
            System.out.println(libro3);
            System.out.println("");

            ObjectSet elim = db.queryByExample(new Book(null, "J.R.R. Tolkien", 0));
            while (elim.hasNext()){
                Book libroElim = (Book) elim.next();
                db.delete(libroElim);
                System.out.println(libroElim + " eliminado con exito");
            }

            System.out.println("");

            libros = db.queryByExample(new Book(null, null, 0));
            while (libros.hasNext()){
                System.out.println(libros.next());
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            db.close();
        }
    }
}
