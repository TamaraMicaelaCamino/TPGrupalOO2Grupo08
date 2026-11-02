package com.oo2.tpgrupalhito2grupo08.components;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.oo2.tpgrupalhito2grupo08.dtos.LiquidacionHaberesDTO;
import com.oo2.tpgrupalhito2grupo08.dtos.ReciboSueldoPdfDTO;
import org.springframework.stereotype.Component;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class ReciboSueldoPdf {

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    private static final Font SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font VALOR = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private static final Font ETIQUETA = FontFactory.getFont(FontFactory.HELVETICA, 7, Color.DARK_GRAY);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale AR = Locale.forLanguageTag("es-AR");
    private static final float ANCHO_TABLA = 504f;
    private static final float BORDE = 1f;

    public byte[] generar(ReciboSueldoPdfDTO recibo) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document documento = new Document(PageSize.A4, 40, 40, 40, 40);

        try {
            PdfWriter.getInstance(documento, salida);
            documento.open();

            Paragraph titulo = new Paragraph("EPICENTRO GOURMET", TITULO);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            Paragraph subtitulo = new Paragraph("RECIBO DE SUELDO", SUBTITULO);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(12);
            documento.add(subtitulo);

            documento.add(armarEncabezado(recibo));
            documento.add(new Paragraph(" "));
            documento.add(armarHaberes(recibo.getLiquidacion()));

            documento.close();
        } catch (DocumentException e) {
            throw new IllegalStateException("No se pudo generar el recibo de sueldo", e);
        }

        return salida.toByteArray();
    }

    private PdfPTable armarEncabezado(ReciboSueldoPdfDTO recibo) throws DocumentException {
        LiquidacionHaberesDTO d = recibo.getLiquidacion();
        LocalDate fechaFin = recibo.getFechaFinFestival();
        String mes = fechaFin.getMonth().getDisplayName(TextStyle.FULL, AR).toUpperCase(AR);

        PdfPTable tabla = new PdfPTable(12);
        fijarAncho(tabla);


        agregarCampo(tabla, d.getNombreCompleto(), "APELLIDOS Y NOMBRES", 8);
        agregarCampo(tabla, String.valueOf(recibo.getDni()), "NÚMERO DE DOCUMENTO", 4);


        agregarCampo(tabla, d.getTipoPersonal(), "CARGO", 4);
        agregarCampo(tabla, String.valueOf(d.getIdLiquidacion()), "NÚMERO DE LIQUIDACIÓN", 4);
        agregarCampo(tabla, mes, "MES", 2);
        agregarCampo(tabla, String.valueOf(fechaFin.getYear()), "AÑO", 2);


        agregarCampo(tabla, recibo.getFechaIngreso().format(FECHA), "FECHA DE INGRESO", 6);
        agregarCampo(tabla, fechaFin.format(FECHA), "FECHA DE FIN", 6);


        agregarCampo(tabla, recibo.getTipoUnidad(), "TIPO UNIDAD DE VENTA", 4);
        agregarCampo(tabla, d.getNombreUnidad(), "UNIDAD DE VENTA", 4);
        agregarCampo(tabla, d.getNombreFestival(), "FESTIVAL", 4);

        return tabla;
    }

    private void agregarCampo(PdfPTable tabla, String valor, String etiqueta, int columnas) {
        Phrase frase = new Phrase();
        frase.add(new Chunk(valor + "\n", VALOR));
        frase.add(new Chunk(etiqueta, ETIQUETA));

        PdfPCell celda = new PdfPCell(frase);
        celda.setColspan(columnas);
        celda.setPadding(6);
        celda.setBorderWidth(BORDE);
        tabla.addCell(celda);
    }


    private PdfPTable armarHaberes(LiquidacionHaberesDTO d) throws DocumentException {
        PdfPTable tabla = new PdfPTable(3);
        fijarAncho(tabla);
        tabla.setWidths(new float[]{300f, 76f, 128f});

        agregarCabecera(tabla, "DETALLE HABERES", Element.ALIGN_LEFT);
        agregarCabecera(tabla, "CANTIDAD", Element.ALIGN_CENTER);
        agregarCabecera(tabla, "IMPORTE", Element.ALIGN_RIGHT);

        double subtotal = d.getSueldoBase() + d.getAdicional();
        int porcentaje = (int) Math.round(d.getFactor() * 100);

        agregarFila(tabla, "SUELDO BASE", "1", dinero(d.getSueldoBase()), NORMAL);

        if ("CAJERO".equals(d.getTipoPersonal())) {
            String detalle = "PLUS POR ANTIGÜEDAD";
            if (d.getAntiguedad() > 0) {
                double valorPorAnio = d.getAdicional() / d.getAntiguedad();
                detalle = "PLUS POR ANTIGÜEDAD (" + dinero(valorPorAnio) + " por año)";
            }
            agregarFila(tabla, detalle, String.valueOf(d.getAntiguedad()), dinero(d.getAdicional()), NORMAL);
        } else {
            agregarFila(tabla, "PLUS POR CATEGORÍA (" + d.getTipoPersonal() + ")", "1",
                    dinero(d.getAdicional()), NORMAL);
        }

        agregarFila(tabla, "SUBTOTAL (sueldo base + plus)", "", dinero(subtotal), NEGRITA);
        agregarFila(tabla, "AJUSTE POR DURACIÓN DEL FESTIVAL (" + d.getDiasFestival() + " días)",
                porcentaje + "%", dinero(d.getTotal()), NORMAL);
        agregarFila(tabla, "TOTAL NETO", "", dinero(d.getTotal()), NEGRITA);

        return tabla;
    }

    private void agregarCabecera(PdfPTable tabla, String texto, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, NEGRITA));
        celda.setBackgroundColor(Color.LIGHT_GRAY);
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(5);
        celda.setBorderWidth(BORDE);
        tabla.addCell(celda);
    }

    private void agregarFila(PdfPTable tabla, String detalle, String cantidad, String importe, Font fuente) {
        tabla.addCell(celda(detalle, fuente, Element.ALIGN_LEFT));
        tabla.addCell(celda(cantidad, fuente, Element.ALIGN_CENTER));
        tabla.addCell(celda(importe, fuente, Element.ALIGN_RIGHT));
    }

    private PdfPCell celda(String texto, Font fuente, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(5);
        celda.setBorderWidth(BORDE);
        return celda;
    }

    private String dinero(double monto) {
        return String.format(AR, "$ %,.2f", monto);
    }

    private void fijarAncho(PdfPTable tabla) {
        tabla.setTotalWidth(ANCHO_TABLA);
        tabla.setLockedWidth(true);
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
    }
}