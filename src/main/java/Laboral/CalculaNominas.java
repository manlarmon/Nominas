package Laboral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import Laboral.ConexionDB.ConexDB;
import Laboral.ConexionDB.conexDB;

/**
 * La clase CalculaNominas se utiliza para calcular y mostrar las nóminas de
 * empleados.
 */
public class CalculaNominas {

    /**
     * El método principal de la aplicación.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este
     *             caso).
     */
    public static void main(String[] args) {
        try {
            // Crear dos empleados
            Empleado e1 = new Empleado("James Cosling", "32000032G", 'M', 4, 7);
            Empleado e2 = new Empleado("Ada Lovelace", "32000031R", 'F');

            //Crear la conexion con la DB
             ConexDB conexionDB = new ConexDB();
            

            // Muestra los detalles iniciales de los empleados
            // escribe(e1, e2);

            // Incrementa el año de servicio del segundo empleado
            // e2.incrAnyo();

            // Cambia la categoría del primer empleado
            // e1.setCategoria(0);

            // Muestra los detalles actualizados de los empleados
            // escribe(e1, e2);

            // PARTE 2
            // Lista de empleados recogidos del archivo de texto.
            List<Empleado> empleados = lecturaFicheroEmpleados();

            // escribe(empleados.get(0), empleados.get(1));
            // escribe(empleados.get(2), empleados.get(3));

            conexionDB.conexion();

        } catch (DatosNoCorrectosException e) {
            System.out.println("Datos no correctos: " + e.getMessage());
        }

    }

    /**
     * Muestra los detalles y el sueldo de un empleado.
     *
     * @param e1 El primer empleado.
     * @param e2 El segundo empleado.
     */
    private static void escribe(Empleado e1, Empleado e2) {
        Nomina n = new Nomina();

        // Muestra los detalles del primer empleado
        e1.Imprime();
        System.out.println("Sueldo: " + n.sueldo(e1) + "\n");

        // Muestra los detalles del segundo empleado
        e2.Imprime();
        System.out.println("Sueldo: " + n.sueldo(e2) + "\n");
    };

    private static List<Empleado> lecturaFicheroEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        File archivoTexto = null;
        File archivoBinario = null;
        FileReader fr = null;
        BufferedReader br = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (metodo readLine()).
            archivoTexto = new File(
                    "src\\main\\Recursos\\empleados.txt");
            fr = new FileReader(archivoTexto);
            br = new BufferedReader(fr);

            // Abre el archivo binario para escritura
            archivoBinario = new File("src\\main\\Recursos\\salarios.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(archivoBinario);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Creo un objeto nomina para poder calcular el sueldo de los empleados.
            Nomina n = new Nomina();

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length > 0 && datos.length <= 5) {
                    String dni = datos[0];
                    String nombre = datos[1];
                    char sexo = (datos[2].charAt(0));
                    int categoria = 1;
                    double años = 0;

                    if (datos.length > 3) {
                        // Si hay al menos 3 elementos en datos, entonces los campos de categoría y años están presentes
                        if (!datos[3].isEmpty()) {
                            categoria = Integer.parseInt(datos[3]);
                        }
                        if (datos.length > 4 && !datos[4].isEmpty()) {
                            años = Double.parseDouble(datos[4]);
                        }
                    }
                    Empleado empleado = new Empleado(nombre, dni, sexo, categoria, años);
                    empleados.add(empleado);

                    objectOutputStream.writeUTF(dni);
                    objectOutputStream.writeDouble(n.sueldo(empleado));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cierra el archivo binario y el resto de recursos
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return empleados;
    }
}