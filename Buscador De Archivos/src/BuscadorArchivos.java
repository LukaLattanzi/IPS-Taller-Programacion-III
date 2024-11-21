import java.io.File; 
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class BuscadorArchivos {

    // Metodo principal para buscar archivos
    private static void BuscarArchivos(File directorio, String nombreArchivo) {
        List<String> resultados = new ArrayList<>(); // Lista para almacenar los resultados
        buscarEnDirectorio(directorio, nombreArchivo, resultados); // Iniciar la busqueda en el directorio

        // Mostrar los resultados
        if (resultados.isEmpty()) {
            System.out.println("No Se Encontraron Archivos Con El Nombre Especificado.");
        } else {
            System.out.println("Archivos Encontrados:");
            for (String resultado : resultados) {
                System.out.println(resultado);
            }
        }
    }

    // Metodo para buscar archivos en el directorio y subdirectorios
    private static void buscarEnDirectorio(File directorio, String nombreArchivo, List<String> resultados) {
        if (!directorio.isDirectory()) return; // Si no es un directorio, terminar el método
        File[] archivos = directorio.listFiles(); // Obtener la lista de archivos y directorios
        if (archivos != null) { 
            for (File archivo : archivos) {
                if (archivo.isDirectory()) { // Si es un directorio, buscar en el directorio
                    buscarEnDirectorio(archivo, nombreArchivo, resultados);
                } else if (archivo.getName().toLowerCase(Locale.ROOT).contains(nombreArchivo)) { // Si el archivo contiene el nombre, agregarlo a los resultados
                    resultados.add(archivo.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in); // Escaner para leer la entrada del usuario

        // Solicitar al usuario que ingrese el directorio (Ejemplo: C:\Users\Usuario\Documents)
        System.out.print("Ingrese El Directorio: ");
        String directorio = scanner.nextLine();

        // Solicitar al usuario que ingrese el nombre del archivo a buscar (Ejemplo: archivo.txt)
        System.out.print("Ingrese El Nombre Del Archivo: ");
        String nombreArchivo = scanner.nextLine();

        // Verificar que los campos no esten vacios
        if (directorio.isEmpty() || nombreArchivo.isEmpty()) {
            System.out.println("Por Favor, Ingrese Un Directorio Y Un Nombre De Archivo.");
            return;
        }

        // Crear un hilo para la busqueda
        Thread searchThread = new Thread(() -> BuscarArchivos(new File(directorio), nombreArchivo.toLowerCase(Locale.ROOT)));
        searchThread.start();

        // Esperar a que el hilo termine
        try {
            searchThread.join();
        } catch (InterruptedException e) {
            System.err.println("El Hilo De Busqueda Fue Interrumpido.");
        }
    }
}


