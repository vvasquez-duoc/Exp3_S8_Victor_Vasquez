package exp3_s8_victor_vasquez;

import java.util.Random;
import java.util.HashMap;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Exp3_S8_Victor_Vasquez {
    static String[] tipoEntradas = new String[3];
    static String[] precioEntradas = new String[3];

    static int edadTerceraEdad = 60;
    
    static ArrayList<Integer> descuentos = new ArrayList<>();
    static ArrayList<String> nombreDescuentos = new ArrayList<>();
    
    static HashMap<String, String[]> entradasPalco = new HashMap<>();
    static HashMap<String, String[]> entradasPA = new HashMap<>();
    static HashMap<String, String[]> entradasPB = new HashMap<>();
    
    static ArrayList<String> entradasVendidasPalco = new ArrayList<>();
    static ArrayList<String> entradasVendidasPA = new ArrayList<>();
    static ArrayList<String> entradasVendidasPB = new ArrayList<>();
    
    static String[] letrasColumnas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
    static int cantFilas = 2;
    static int cantColumnas = 10;
    static String separador = "-------------------------------------------";
    
    static String asientoSeleccionado = "";
    static boolean asientoNoValido = false;
    static boolean asientoOcupado = false;
    static boolean hayAsientosVendidos = false;
    
    static String nombreCliente = "";
    static String rutCliente = "";
    
    public static void limpiaPantalla(){
        for(int i=0; i<30; i++){
            System.out.println("");
        }
    }
    
    public static Boolean validaEdad(String edad){
        int edadNum;
        
        if(edad == null || edad.isEmpty()) return false;
        
        try{
            edadNum = Integer.parseInt(edad);
        }catch(NumberFormatException e){
            return false;
        }
        
        return (edadNum >= 5);
    }
    
    public static Boolean validaRut(String rut){
        int rutNum;
        if(rut == null || rut.isEmpty() || rut.length() <= 1) return false;
        
        String R = rut.substring(0, rut.length() - 1);
        String DV = rut.substring(rut.length() - 1, rut.length());
        
        try{
            rutNum = Integer.parseInt(R);
        }catch(NumberFormatException e){
            return false;
        }
        
        return DV.toLowerCase().equals(dv(R));
    }
    
    public static String dv(String rut){
        Integer M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        
        return ( S > 0 ) ? String.valueOf(S-1) : "k";		
    }
    
    public static void seleccionaAsiento(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);

        imprimeMapaAsientos(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        
        System.out.println("(*) ASIENTOS VENDIDOS");
        if(asientoNoValido){
            System.out.println("-- EL ASIENTO "+asientoSeleccionado.toUpperCase()+" NO ES VALIDO --");
        }
        if(asientoOcupado){
            System.out.println("-- EL ASIENTO "+asientoSeleccionado.toUpperCase()+" YA ESTA VENDIDO --");
        }
        System.out.println("SELECCIONE EL ASIENTO QUE QUIERE COMPRAR");
        System.out.println("POR EJEMPLO: A2");
        
        asientoSeleccionado = teclado.nextLine();
        asientoNoValido = false;
        asientoOcupado = false;
        
        if(asientoSeleccionado.isEmpty() || asientoSeleccionado == null){
            limpiaPantalla();
            seleccionaAsiento(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }
        
        String columnaAux = asientoSeleccionado.substring(0,1);
        String filaAux = asientoSeleccionado.substring(1,2);
        int filaAsiento = Integer.parseInt(filaAux);
        filaAsiento -= 1;
        int columnaAsiento = buscaColumnaDesdeLetra(columnaAux, letrasColumnas);
        
        System.out.println(filaAsiento);
        System.out.println(columnaAsiento);
        System.out.println(cantColumnas);
        
        if(columnaAsiento < 0 || columnaAsiento > cantColumnas || filaAsiento < 0 || filaAsiento > cantFilas) asientoNoValido = true;
        else{
            if(opcion == 1){
                if(asientosPalco[filaAsiento][columnaAsiento] == 1) asientoOcupado = true;
                else asientosPalco[filaAsiento][columnaAsiento] = 1;
            }else if(opcion == 2){
                if(asientosPA[filaAsiento][columnaAsiento] == 1) asientoOcupado = true;
                else asientosPA[filaAsiento][columnaAsiento] = 1;
            }else if(opcion == 3){
                if(asientosPB[filaAsiento][columnaAsiento] == 1) asientoOcupado = true;
                else asientosPB[filaAsiento][columnaAsiento] = 1;
            }
        }
        
        if(asientoNoValido || asientoOcupado){
            limpiaPantalla();
            seleccionaAsiento(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }else{
            limpiaPantalla();
            otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
        }
        
    }
    
    public static void otrosDatosCompra(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB, String asientoSeleccionado){
        Scanner teclado = new Scanner(System.in);
        String edad;
        int edadNumerica = 0;
        int precioEntrada = 15000;
        int descuento = 0;
        String rut;
        String nombre;
        
        if(opcion == 2) precioEntrada = 10000;
        else if(opcion == 3) precioEntrada = 7000;
        
        System.out.println("UBICACION: "+ zonaTeatro);
        System.out.println("ASIENTO: "+asientoSeleccionado.toUpperCase());
        
        if(nombreCliente.equals("")){
            System.out.println("");
            System.out.println("INGRESE SU NOMBRE:");
            nombre = teclado.nextLine();
            if(nombre.isEmpty()){
                limpiaPantalla();
                System.out.println("-- ERROR: EL NOMBRE NO PUEDE ESTAR EN BLANCO --");
                System.out.println("-- INGRESE SUS DATOS NUEVAMENTE --");
                System.out.println("");
                otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
            }
            nombreCliente = nombre;
        }else nombre = nombreCliente;
        
        if(rutCliente.equals("")){
            System.out.println("");
            System.out.println("INGRESE SU RUT SIN PUNTOS NI GUION:");
            System.out.println("POR EJEMPLO: 12345678K");
            rut = teclado.nextLine();
            if(!validaRut(rut)){
                limpiaPantalla();
                System.out.println("-- ERROR: EL RUT INGRERSADO NO ES VALIDO --");
                System.out.println("-- INGRESE SUS DATOS NUEVAMENTE --");
                System.out.println("");
                otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
            }
            rutCliente = rut;
        }else rut = rutCliente;
        
        System.out.println("");
        System.out.println("INGRESE LA EDAD DEL USUARIO DE LA ENTRADA PARA POSIBLES DESCUENTOS:");
        edad = teclado.nextLine();

        if(!validaEdad(edad)){
            limpiaPantalla();
            System.out.println("-- ERROR: LA EDAD NO PUEDE SER MENOR A CINCO AÑOS (5) --");
            System.out.println("-- INGRESE SUS DATOS NUEVAMENTE --");
            System.out.println("");
            otrosDatosCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado);
        }

        edadNumerica = Integer.parseInt(edad);
        
        int precioFinal = precioEntrada;
        if(edadNumerica < 24){
            descuento = 10;
            precioFinal = precioEntrada - (precioEntrada * descuento / 100);
        }
        if(edadNumerica > 60){
            descuento = 15;
            precioFinal = precioEntrada - (precioEntrada * descuento / 100);
        }
        
        limpiaPantalla();
        resumenCompra(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB, asientoSeleccionado, precioEntrada, descuento, precioFinal, nombre, rut);
    }
    
    public static void resumenCompra(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB, String asientoSeleccionado, int precioEntrada, int descuento, int precioFinal, String nombre, String rut){
        Scanner teclado = new Scanner(System.in);
        String otraCompra;
        String txtDescuento;
        otraCompra = "";
        
        String[] datosEntrada = new String[7];
        
        do{
            System.out.println("-- RESUMEN DE COMPRA --");
            System.out.println("UBICACION: "+ zonaTeatro);
            System.out.println("ASIENTO: "+asientoSeleccionado.toUpperCase());
            System.out.println("PRECIO ENTRADA: "+precioEntrada);
            if(descuento == 0){
                txtDescuento = "SIN DESCUENTO";
            }else txtDescuento = (descuento == 10)? "ESTUDIANTE - " : "TERCERA EDAD - ";
            txtDescuento += (descuento == 0)? "" : descuento+"%";
            System.out.println("DESCUENTO: "+txtDescuento);
            System.out.println("PRECIO FINAL: "+precioFinal);
            System.out.println("NOMBRE COMPRADOR: "+nombre.toUpperCase());
            System.out.println("RUT COMPRADOR: "+rut.toUpperCase());
               
            System.out.println("");
            System.out.println("DESEA COMPRAR UNA NUEVA ENTRADA (S/N)");
            otraCompra = teclado.nextLine();
            if(!("N".equals(otraCompra.toUpperCase()) || "S".equals(otraCompra.toUpperCase()))){
                limpiaPantalla();
                System.out.println("-- OPCION NO VALIDA. SOLO SE PERMITE 'S' O 'N' --");
                System.out.println("");
            }
        }while(!("N".equals(otraCompra.toUpperCase()) || "S".equals(otraCompra.toUpperCase())));
        
        datosEntrada[0] = zonaTeatro;
        datosEntrada[1] = asientoSeleccionado.toUpperCase();
        datosEntrada[2] = String.valueOf(precioEntrada);
        datosEntrada[3] = txtDescuento;
        datosEntrada[4] = String.valueOf(precioFinal);
        datosEntrada[5] = nombre.toUpperCase();
        datosEntrada[6] = rut.toUpperCase();
        
        switch(zonaTeatro){
            case "ZONA A - PALCO":
                entradasPalco.put(asientoSeleccionado.toUpperCase(), datosEntrada);
                if(!entradasVendidasPalco.contains(rut.toUpperCase()+"|"+nombre.toUpperCase())) entradasVendidasPalco.add(rut.toUpperCase()+","+nombre.toUpperCase());
                break;
            case "ZONA B - TRIBUNA BAJA":
                entradasPB.put(asientoSeleccionado.toUpperCase(), datosEntrada);
                if(!entradasVendidasPB.contains(rut.toUpperCase()+"|"+nombre.toUpperCase())) entradasVendidasPB.add(rut.toUpperCase()+","+nombre.toUpperCase());
                break;
            case "ZONA C - TRIBUNA ALTA":
                entradasPA.put(asientoSeleccionado.toUpperCase(), datosEntrada);
                if(!entradasVendidasPA.contains(rut.toUpperCase()+"|"+nombre.toUpperCase())) entradasVendidasPA.add(rut.toUpperCase()+","+nombre.toUpperCase());
                break;
        }
        
        if("N".equals(otraCompra.toUpperCase())){
            nombreCliente = "";
            rutCliente = "";
            
            limpiaPantalla();
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }
        
        if("S".equals(otraCompra.toUpperCase())){
            limpiaPantalla();
            tipoAsiento(asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static int buscaColumnaDesdeLetra(String letra, String[] letrasColumnas){
        int pos = -1;
        for(int i=0; i<letrasColumnas.length; i++){
            if(letra.toUpperCase().equals(letrasColumnas[i])){
                pos = i;
                break;
            }
        }
        return pos;
    }
    
    public static void tipoAsiento(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        int opcion;
        String[] itemsMenu = new String[3];
        String zonaTeatro = "";
        
        opcion = 0;
        itemsMenu[0] = "ZONA A - PALCO";
        itemsMenu[1] = "ZONA B - TRIBUNA BAJA";
        itemsMenu[2] = "ZONA C - TRIBUNA ALTA";
        
        try{
            do{
                System.out.println("-- SELECCIONE EL TIPO DE ENTRADA --");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION:");
                for(int i=0; i<itemsMenu.length; i++){
                    String txtOpcion = (i + 1) + ".- " + itemsMenu[i];
                    if(i == 0) txtOpcion += " ($15.000)";
                    if(i == 1) txtOpcion += " ($10.000)";
                    if(i == 2) txtOpcion += " ($7.000)";
                    System.out.println(txtOpcion);
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.length){
                    limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.length);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            tipoAsiento(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion >=1 && opcion <= itemsMenu.length){
            zonaTeatro = itemsMenu[(opcion - 1)];
            limpiaPantalla();
            seleccionaAsiento(opcion, zonaTeatro, asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void menuPrincipal(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        int opcion;
        String[] itemsMenu = new String[7];
        
        opcion = 0;
        itemsMenu[0] = "VENTA DE ENTRADAS";
        itemsMenu[1] = "VER PROMOCIONES";
        itemsMenu[2] = "EDITAR PROMOCIONES";
        itemsMenu[3] = "IMPRIMIR BOLETA";
        itemsMenu[4] = "LISTADO DE VENTAS";
        itemsMenu[5] = "CALCULAR INGRESOS TOTALES";
        itemsMenu[6] = "SALIR";
        
        try{
            do{
                System.out.println("BIENVENIDO A LA ADMINISTRACION DEL TEATRO MORO");
                System.out.println("-- VENTA DE ENTRADAS --");
                System.out.println("");
                System.out.println("SELECCIONE UNA OPCION");
                for(int i=0; i<itemsMenu.length; i++){
                    System.out.println((i + 1) + ".- " + itemsMenu[i]);
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > itemsMenu.length){
                    limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > itemsMenu.length);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 1){
            limpiaPantalla();
            tipoAsiento(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 2){
            limpiaPantalla();
            muestraPromociones(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 3){
            limpiaPantalla();
            editaPromociones(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 4){
            limpiaPantalla();
            impresionBoleta(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 5){
            limpiaPantalla();
            listadoVentas(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion == 6){
            limpiaPantalla();
            calculaIngresosTotales(asientosPalco, asientosPA, asientosPB);
        }
        
        if(opcion >= 7){
            limpiaPantalla();
            salir();
        }
    }
    
    public static void listadoVentas(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        if(entradasVendidasPalco.isEmpty() && entradasVendidasPA.isEmpty() && entradasVendidasPB.isEmpty()){
            System.out.println("NO HAY VENTAS AUN");
            System.out.println("");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            limpiaPantalla();
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }else{
            limpiaPantalla();
            listarTodasLasVentas(asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void listarTodasLasVentas(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        String[] datosEntrada = null;
        
        tipoEntradas[0] = "ZONA A - PALCO";
        tipoEntradas[1] = "ZONA B - TRIBUNA BAJA";
        tipoEntradas[2] = "ZONA C - TRIBUNA ALTA";
        
        NumberFormat precio = NumberFormat.getInstance(new Locale("es", "CL"));
        precio.setGroupingUsed(true);
        
        System.out.println("LISTADO DE VENTAS");
        System.out.println("");
        
        System.out.println("===  VENTAS "+tipoEntradas[0]+" ===");
        if(entradasPalco.isEmpty()){
            System.out.println("SIN VENTAS");
            System.out.println("-------------------------------------");
        }
        for(String key : entradasPalco.keySet()){
            datosEntrada = entradasPalco.get(key);
            
            String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
            
            System.out.println("ZONA         : "+datosEntrada[0]+" (ASIENTO "+datosEntrada[1]+")");
            System.out.println("PRECIO NORMAL: $"+precio.format(Integer.parseInt(datosEntrada[2])));
            System.out.println("DESCUENTO    : $"+descuentoEntrada);
            System.out.println("PRECIO PAGADO: $"+precio.format(Integer.parseInt(datosEntrada[4])));
            System.out.println("-------------------------------------");
        }
        
        System.out.println("===  VENTAS "+tipoEntradas[1]+" ===");
        if(entradasPB.isEmpty()){
            System.out.println("SIN VENTAS");
            System.out.println("-------------------------------------");
        }
        for(String key : entradasPB.keySet()){
            datosEntrada = entradasPB.get(key);
            
            String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
            
            System.out.println("ZONA         : "+datosEntrada[0]+" (ASIENTO "+datosEntrada[1]+")");
            System.out.println("PRECIO NORMAL: $"+precio.format(Integer.parseInt(datosEntrada[2])));
            System.out.println("DESCUENTO    : $"+descuentoEntrada);
            System.out.println("PRECIO PAGADO: $"+precio.format(Integer.parseInt(datosEntrada[4])));
            System.out.println("-------------------------------------");
        }
        
        System.out.println("===  VENTAS "+tipoEntradas[2]+" ===");
        if(entradasPA.isEmpty()){
            System.out.println("SIN VENTAS");
            System.out.println("-------------------------------------");
        }
        for(String key : entradasPA.keySet()){
            datosEntrada = entradasPA.get(key);
            
            String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
            
            System.out.println("ZONA         : "+datosEntrada[0]+" (ASIENTO "+datosEntrada[1]+")");
            System.out.println("PRECIO NORMAL: $"+precio.format(Integer.parseInt(datosEntrada[2])));
            System.out.println("DESCUENTO    : $"+descuentoEntrada);
            System.out.println("PRECIO PAGADO: $"+precio.format(Integer.parseInt(datosEntrada[4])));
            System.out.println("-------------------------------------");
        }
        
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static void calculaIngresosTotales(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        if(entradasVendidasPalco.isEmpty() && entradasVendidasPA.isEmpty() && entradasVendidasPB.isEmpty()){
            System.out.println("NO HAY VENTAS AUN");
            System.out.println("");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            limpiaPantalla();
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }else{
            limpiaPantalla();
            calcularIngresosTotales(asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void calcularIngresosTotales(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        String[] datosEntrada = null;
        
        tipoEntradas[0] = "ZONA A - PALCO";
        tipoEntradas[1] = "ZONA B - TRIBUNA BAJA";
        tipoEntradas[2] = "ZONA C - TRIBUNA ALTA";
        
        int totalIngresos = 0;
        int totalIngresosPalco = 0;
        int totalIngresosPA = 0;
        int totalIngresosPB = 0;
        
        NumberFormat precio = NumberFormat.getInstance(new Locale("es", "CL"));
        precio.setGroupingUsed(true);
        
        System.out.println("INGRESOS TOTALES POR VENTA DE ENTRADAS");
        System.out.println("");
        
       for(String key : entradasPalco.keySet()){
            datosEntrada = entradasPalco.get(key);
            totalIngresosPalco += Integer.parseInt(datosEntrada[4]);
        }
        for(String key : entradasPB.keySet()){
            datosEntrada = entradasPB.get(key);
            totalIngresosPB += Integer.parseInt(datosEntrada[4]);
        }
        for(String key : entradasPA.keySet()){
            datosEntrada = entradasPA.get(key);
            totalIngresosPA += Integer.parseInt(datosEntrada[4]);
        }
        
        totalIngresos = totalIngresosPalco + totalIngresosPA + totalIngresosPB;
        
        System.out.println("TOTAL INGRESOS "+tipoEntradas[0]+"\t\t$"+precio.format(totalIngresosPalco));
        System.out.println("TOTAL INGRESOS "+tipoEntradas[1]+"\t$"+precio.format(totalIngresosPB));
        System.out.println("TOTAL INGRESOS "+tipoEntradas[2]+"\t$"+precio.format(totalIngresosPA));
        System.out.println("");
        System.out.println("TOTAL INGRESOS \t\t\t\t$"+precio.format(totalIngresos));
        System.out.println("-------------------------------------");
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
        
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static void impresionBoleta(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        if(entradasVendidasPalco.isEmpty() && entradasVendidasPA.isEmpty() && entradasVendidasPB.isEmpty()){
            System.out.println("ERROR: NO HAY VENTAS AUN");
            System.out.println("Para imprimir boletas, primero debe haber ventas de entradas");
            System.out.println("");
            System.out.println("Presione ENTER para continuar...");
            teclado.nextLine();
        
            limpiaPantalla();
            menuPrincipal(asientosPalco, asientosPA, asientosPB);
        }else{
            limpiaPantalla();
            imprimeBoleta(asientosPalco, asientosPA, asientosPB);
        }
    }
    
    public static void imprimeMapaAsientos(int opcion, String zonaTeatro, int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        String filaLetras = "  | ";
        String filaNumeros = "";
        
        if(opcion == 1){
            cantFilas = 2;
            cantColumnas = 10;
            separador = "-------------------------------------------";
        }
        if(opcion == 2){
            cantFilas = 5;
            cantColumnas = 12;
            separador = "---------------------------------------------------";
        }else if(opcion == 3){
            cantFilas = 3;
            cantColumnas = 8;
            separador = "-----------------------------------";
        }
        
        for(int i=0; i<cantColumnas; i++){
            filaLetras += letrasColumnas[i]+" | ";
        }
        
        hayAsientosVendidos = false;
        
        System.out.println("-- "+zonaTeatro+" --");
        System.out.println("");
        System.out.println(filaLetras);
        System.out.println(separador);
        
        for(int i=0; i<cantFilas; i++){
            filaNumeros = (i + 1)+" |";
            for(int j=0; j<cantColumnas; j++){
                if(opcion == 1){
                    if(asientosPalco[i][j] == 1){
                        filaNumeros += " * |";
                        hayAsientosVendidos = true;
                    }
                    else filaNumeros += "   |";
                }else if(opcion == 2){
                    if(asientosPA[i][j] == 1){
                        filaNumeros += " * |";
                        hayAsientosVendidos = true;
                    }
                    else filaNumeros += "   |";
                }else if(opcion == 3){
                    if(asientosPB[i][j] == 1){
                        filaNumeros += " * |";
                        hayAsientosVendidos = true;
                    }
                    else filaNumeros += "   |";
                }
            }
            System.out.println(filaNumeros);
            System.out.println(separador);
        }
    }
    
    public static void imprimeBoleta(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        int nroBoleta = generarNroBoleta(1000, 9999);
        int hashBoleta = generarNroBoleta(100000, 999999);
        int opcion = 0;
        int espaciosAdicionales = 0;
        
        int totalBoleta = 0;
        
        String[] datosEntrada = null;
        String txtDescuentoEntrada = "";
        String txtTotalEntrada = "";
        String txtCodigoBarras = "";
        
        ArrayList<String> rutsUnicos = new ArrayList<>();
        ArrayList<String> nombresUnicos = new ArrayList<>();
        ArrayList<String> datosUnicos = new ArrayList<>();
        for(String datos : entradasVendidasPalco){
            if(!datosUnicos.contains(datos)) datosUnicos.add(datos);
        }
        for(String datos : entradasVendidasPA){
            if(!datosUnicos.contains(datos)) datosUnicos.add(datos);
        }
        for(String datos : entradasVendidasPB){
            if(!datosUnicos.contains(datos)) datosUnicos.add(datos);
        }
        
        System.out.println("IMPRESION DE BOLETAS");
        System.out.println("");
        System.out.println("SELECCIONE LOS DATOS DEL CLIENTE PARA IMPRIMIR SU BOLETA:");
        int aux = 1;
        for(String DU : datosUnicos){
            String[] datosBoleta = DU.split(",");
            System.out.println("["+aux+"] RUT: "+formatoRut(datosBoleta[0])+" - NOMBRE: "+datosBoleta[1]);
            rutsUnicos.add(datosBoleta[0]);
            nombresUnicos.add(datosBoleta[1]);
            aux++;
        }
        System.out.println("");
        
        try{
            do{
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > aux){
                    limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > aux);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            imprimeBoleta(asientosPalco, asientosPA, asientosPB);
        }
        
        String rutSeleccionado = rutsUnicos.get((opcion - 1));
        String nombreSeleccionado = nombresUnicos.get((opcion - 1));
                
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String txtHoy = hoy.format(formatter);
        
        limpiaPantalla();
        
        System.out.println("********************************************************************************************");
        System.out.println("*    TEATRO MORO SA                                                                        *");
        System.out.println("*   RUT "+formatoRut("732767800")+"                                                       --------------- *");
        System.out.println("*  AV TOMAS MORO 1285                                                      | BOLETA "+nroBoleta+" | *");
        System.out.println("* LAS CONDES. SANTIAGO                                                     --------------- *");
        System.out.println("* TEL: +56 2 2233 4455                                                                     *");
        System.out.println("********************************************************************************************");
        System.out.println("FECHA:        "+txtHoy);
        System.out.println("RAZON SOCIAL: "+nombreSeleccionado);
        System.out.println("RUT:          "+formatoRut(rutSeleccionado));
        System.out.println("********************************************************************************************");
        System.out.println("CANT  DESCRIPCION                                                         PRECIO   DESCUENTO");
        
        NumberFormat precio = NumberFormat.getInstance(new Locale("es", "CL"));
        precio.setGroupingUsed(true);
        
        for(String key : entradasPalco.keySet()){
            datosEntrada = entradasPalco.get(key);
            if(rutSeleccionado.equals(datosEntrada[6])){
                txtDescuentoEntrada = "";
                
                String descripcionEntrada = "ENTRADA "+datosEntrada[0]+" (ASIENTO: "+datosEntrada[1]+"). "+datosEntrada[3];
                espaciosAdicionales = 43 - datosEntrada[0].length() - datosEntrada[3].length();
                for(int i=0; i<espaciosAdicionales; i++) descripcionEntrada += " ";
                
                String precioEntrada = (Integer.parseInt(datosEntrada[2]) < 10000)? "$ "+precio.format(Integer.parseInt(datosEntrada[2])) : "$"+precio.format(Integer.parseInt(datosEntrada[2]));
                String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
                espaciosAdicionales = 11 - descuentoEntrada.length();
                for(int i=0; i<espaciosAdicionales; i++) txtDescuentoEntrada += " ";
                txtDescuentoEntrada += "$"+descuentoEntrada;

                totalBoleta += Integer.parseInt(datosEntrada[4]);
                
                System.out.println("   1  "+descripcionEntrada+precioEntrada+txtDescuentoEntrada);
            }
        }
        for(String key : entradasPB.keySet()){
            datosEntrada = entradasPB.get(key);
            if(rutSeleccionado.equals(datosEntrada[6])){
                txtDescuentoEntrada = "";
                
                String descripcionEntrada = "ENTRADA "+datosEntrada[0]+" (ASIENTO: "+datosEntrada[1]+"). "+datosEntrada[3];
                espaciosAdicionales = 43 - datosEntrada[0].length() - datosEntrada[3].length();
                for(int i=0; i<espaciosAdicionales; i++) descripcionEntrada += " ";
                
                String precioEntrada = (Integer.parseInt(datosEntrada[2]) < 10000)? "$ "+precio.format(Integer.parseInt(datosEntrada[2])) : "$"+precio.format(Integer.parseInt(datosEntrada[2]));
                String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
                espaciosAdicionales = 11 - descuentoEntrada.length();
                for(int i=0; i<espaciosAdicionales; i++) txtDescuentoEntrada += " ";
                txtDescuentoEntrada += "$"+descuentoEntrada;

                totalBoleta += Integer.parseInt(datosEntrada[4]);
                
                System.out.println("   1  "+descripcionEntrada+precioEntrada+txtDescuentoEntrada);
            }
        }
        for(String key : entradasPA.keySet()){
            datosEntrada = entradasPA.get(key);
            if(rutSeleccionado.equals(datosEntrada[6])){
                txtDescuentoEntrada = "";
                
                String descripcionEntrada = "ENTRADA "+datosEntrada[0]+" (ASIENTO: "+datosEntrada[1]+"). "+datosEntrada[3];
                espaciosAdicionales = 43 - datosEntrada[0].length() - datosEntrada[3].length();
                for(int i=0; i<espaciosAdicionales; i++) descripcionEntrada += " ";
                
                String precioEntrada = (Integer.parseInt(datosEntrada[2]) < 10000)? "$ "+precio.format(Integer.parseInt(datosEntrada[2])) : "$"+precio.format(Integer.parseInt(datosEntrada[2]));
                String descuentoEntrada = precio.format(Integer.parseInt(datosEntrada[2]) - Integer.parseInt(datosEntrada[4]));
                espaciosAdicionales = 11 - descuentoEntrada.length();
                for(int i=0; i<espaciosAdicionales; i++) txtDescuentoEntrada += " ";
                txtDescuentoEntrada += "$"+descuentoEntrada;

                totalBoleta += Integer.parseInt(datosEntrada[4]);
                
                System.out.println("   1  "+descripcionEntrada+precioEntrada+txtDescuentoEntrada);
            }
        }

        String totalEntrada = precio.format(totalBoleta);
        espaciosAdicionales = 91 - totalEntrada.length();
        for(int i=0; i<espaciosAdicionales; i++) txtTotalEntrada += " ";
        txtTotalEntrada += "$"+totalEntrada;
        
        String codigoBarra = codBarras(String.valueOf(hashBoleta));
        espaciosAdicionales = 31;
        for(int i=0; i<espaciosAdicionales; i++) txtCodigoBarras += " ";
        txtCodigoBarras += codigoBarra;
        
        System.out.println("");
        System.out.println("                                                                                       TOTAL");
        System.out.println(txtTotalEntrada);
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println(txtCodigoBarras);
        
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();teclado.nextLine();
        
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static void salir(){
        System.out.println("GRACIAS POR COMPRAR ENTRADAS EN TEATRO MORO");
    }
    
    public static void muestraPromociones(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        
        String correccionEspacios;
        
        tipoEntradas[0] = "ZONA A - PALCO";
        tipoEntradas[1] = "ZONA B - TRIBUNA BAJA";
        tipoEntradas[2] = "ZONA C - TRIBUNA ALTA";
        
        precioEntradas[0] = "$15.000";
        precioEntradas[1] = "$10.000";
        precioEntradas[2] = "$ 7.000";
        
        limpiaPantalla();
        System.out.println("-- PRECIOS DE ENTRADAS --");
        
        for(int i=0; i<tipoEntradas.length; i++){
            correccionEspacios = (i == 0)? ":        " : ": ";
            System.out.println(tipoEntradas[i]+correccionEspacios+precioEntradas[i]);
        }
        
        System.out.println("");
        System.out.println("-- PROMOCIONES DISPONIBLES --");
        for(int i=0; i<descuentos.size(); i++){
            System.out.println("DESCUENTO "+nombreDescuentos.get(i)+":\t"+descuentos.get(i)+"%");
        }
        System.out.println("* Para acceder al descuento de 3ra edad, debe ser mayor de "+edadTerceraEdad+" años");
        
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();
       
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static void editaPromociones(int[][] asientosPalco, int[][] asientosPA, int[][] asientosPB){
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        int i = 0;
        
        try{
            do{
                System.out.println("-- SELECCIONE EL CAMPO A EDITAR --");
                for(i=0; i<descuentos.size(); i++){
                    System.out.println((i + 1)+".- DESCUENTO "+nombreDescuentos.get(i)+":\t"+descuentos.get(i)+"%");
                }
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > descuentos.size()){
                    limpiaPantalla();
                    System.out.println("-- LA OPCION ("+opcion+") NO ES VALIDA --");
                    System.out.println("");
                }
            }while(opcion < 1 || opcion > descuentos.size());
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            editaPromociones(asientosPalco, asientosPA, asientosPB);
        }
        
        int nuevoDescuento = 0;
        
        try{
            do{
                System.out.println("INGRESE EL NUEVO PORCENTAJE DE DESCUENTO PARA "+nombreDescuentos.get((opcion - 1)));
                nuevoDescuento = teclado.nextInt();
                if(nuevoDescuento < 1 || nuevoDescuento > 100){
                    limpiaPantalla();
                    System.out.println("-- EL DESCUENTO DEBE ESTAR ENTRE 1 Y 99 --");
                    System.out.println("");
                }
            }while(nuevoDescuento < 1 || nuevoDescuento > 100);
        }catch(Exception e){
            limpiaPantalla();
            System.out.println("ERROR: LA OPCION INGRESADA NO ES UN NUMERO");
            System.out.println("");
            editaPromociones(asientosPalco, asientosPA, asientosPB);
        }
        
        descuentos.set((opcion - 1), nuevoDescuento);
        
        limpiaPantalla();
        System.out.println("-- LOS NUEVOS DESCUENTOS SON: --");
        for(i=0; i<descuentos.size(); i++){
            System.out.println("DESCUENTO "+nombreDescuentos.get(i)+":\t"+descuentos.get(i)+"%");
        }
        
        System.out.println("");
        
        System.out.println("Presione ENTER para continuar...");
        teclado.nextLine();teclado.nextLine();
       
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
    
    public static int generarNroBoleta(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    
    public static String formatoRut(String rut){
        int R = Integer.parseInt(rut.substring(0, rut.length() - 1));
        String DV = rut.substring(rut.length() - 1, rut.length());
        
        NumberFormat formato = NumberFormat.getInstance(new Locale("es", "CL"));
        formato.setGroupingUsed(true);
        return formato.format(R)+"-"+DV;
    }

    public static String codBarras(String nroBoleta){
        String barra = "";
        for(int i=0; i<nroBoleta.length(); i++) {
            char digit = nroBoleta.charAt(i);
            barra += digitoABarra(digit);
        }
        return barra;
    }

    public static String digitoABarra(char digito){
        if(digito == '0') return "|||||";
        else if(digito == '1') return ":||||";
        else if(digito == '2') return "::|||";
        else if(digito == '3') return ":::||";
        else if(digito == '4') return "::::|";
        else if(digito == '5') return ":::::";
        else if(digito == '6') return "|::::";
        else if(digito == '7') return "||:::";
        else if(digito == '8') return "|||::";
        else return "||||:";
    }
    
    public static void main(String[] args) {
        int[][] asientosPalco = new int[2][10];
        int[][] asientosPA = new int[5][12];
        int[][] asientosPB = new int[3][8];
        
        descuentos.add(10);
        descuentos.add(15);
        
        nombreDescuentos.add("ESTUDIANTE");
        nombreDescuentos.add("TERCERA EDAD");
        
        limpiaPantalla();
        menuPrincipal(asientosPalco, asientosPA, asientosPB);
    }
}