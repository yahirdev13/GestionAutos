import com.db4o.Db4o;
import com.db4o.ObjectContainer;

import java.util.Scanner;
import com.db4o.*;

public class GestionAutos {

    public static void main(String[] args) {

        Scanner read = new Scanner(System.in).useDelimiter("\n");


        //1. Definir
        String ruta = "autos.db4o";

        //2,Definir la instancia de DB4o
        ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(),ruta);

        //3. Colocar el codigo en el bloque de try

        try {
            String opc = "";
            salida:
            do {
                System.out.println("Seleccione una opcion");
                System.out.println("Insertar");
                System.out.println("Consultar");
                System.out.println("Eliminar");
                System.out.println("Modificar");

                opc = read.next();

                switch (opc){
                    case "Insertar":
                        Auto auto = new Auto();
                        System.out.print("Marca: ");
                        auto.setMarca(read.nextLine());
                        System.out.print("Modelo: ");
                        auto.setModelo(read.nextLine());
                        System.out.print("Anio: ");
                        auto.setAnio(read.nextLine());
                        db.store(auto);
                        System.out.println(auto);
                        System.out.println("Auto guardado con exito");
                        break;
                    case "Consultar":
                        //Objeto de referencia
                        Auto autoB = new Auto(null, null, null);
                        ObjectSet resultadosB = db.queryByExample(autoB);
                        //Iterar nuestros resultados
                        while(resultadosB.hasNext()){
                            Auto a = (Auto) resultadosB.next();
                            System.out.println(a);
                        }
                        //Obtener el arraylist de resultados
                        break;
                    case "Eliminar":
                        //Obtener el parametro de busqueda
                        String filtro;
                        System.out.println("Introduzca el modelo a eliminar: ");
                        filtro = read.next();
                        //Crear el objeto de referencia
                        Auto autoElim = new Auto(null, filtro, null);
                        ObjectSet resultadosElim = db.queryByExample(autoElim);
                        while(resultadosElim.hasNext()){
                            Auto a = (Auto) resultadosElim.next();
                            db.delete(a);
                            System.out.println("Auto eliminado con exito");
                        }
                        break;
                    case "Modificar":
                        //Obtener el parametro de busqueda
                        String filtroEdit;
                        System.out.println("Introduzca el modelo a eliminar: ");
                        filtroEdit = read.next();
                        //Crear el objeto de referencia
                        Auto autoEdit = new Auto(null, filtroEdit, null);
                        ObjectSet resultadosEdit = db.queryByExample(autoEdit);
                        while(resultadosEdit.hasNext()) {
                            //Recuperar los objetos de la DB
                            Auto edit = (Auto) resultadosEdit.next();
                            System.out.println("Auto encontrado" + edit);
                            System.out.println("Nueva Marca: ");
                            edit.setMarca(read.nextLine());
                            System.out.println("Nuevo Modelo: ");
                            edit.setModelo(read.nextLine());
                            System.out.println("Nuevo anio: ");
                            edit.setAnio(read.nextLine());
                            //Guardar los cambios en la DB
                            db.store(edit);
                        }


                        break;
                    case "Salir":
                        break salida;
                    default:
                        System.out.println("Opcion invalida");
                }
                System.out.println("Deseas continuar? 1. SI 2. NO");
                opc = read.next();
            } while (opc.equalsIgnoreCase("0"));
        }catch (Exception e){
            System.out.println("El sistema se cerro");

            db.close();
        }finally {
            //Cierre de nuestro documento
            db.close();
        }

    }
}
