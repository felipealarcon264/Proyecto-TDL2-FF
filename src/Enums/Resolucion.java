package Enums;

/**
 * Representa las distintas calidades de resolución de video disponibles
 * en la plataforma, desde definición estándar hasta ultra alta definición.
 *
 * @author Grupo 4 - Proyecto TDL2
 * @version 1.0
 */
public enum Resolucion {
    /**
     * Definición Estándar (480p).
     * Calidad básica, ideal para conexiones lentas o dispositivos pequeños.
     */
    SD_480P,

    /**
     * Alta Definición (720p).
     * Buena calidad de video para la mayoría de las pantallas.
     */
    HD_720P,

    /**
     * Full Alta Definición (1080p).
     * Calidad de video excelente, estándar para streaming de alta calidad.
     */
    FULL_HD_1080P,

    /**
     * Ultra Alta Definición (2160p o 4K).
     * Máxima calidad de video, requiere una conexión a internet muy rápida.
     */
    UHD_4K;
}
