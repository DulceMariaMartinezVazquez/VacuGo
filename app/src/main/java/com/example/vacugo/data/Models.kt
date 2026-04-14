package com.example.vacugo.data

// ── Paciente ─────────────────────────────────────────────────────
data class Paciente(
    val id: String = "1",
    val nombre: String = "Carlos Mendoza Ruiz",
    val fechaNacimiento: String = "15 de Mayo, 1990",
    val genero: String = "Masculino",
    val curp: String = "PEGA900515HMZRR05",
    val telefono: String = "+52 55 1234 5678",
    val email: String = "carlos.mendoza@email.com",
    val direccion: String = "Calle Reforma 123, Col. Juárez, CDMX, CP 06600",
    val tipoSangre: String = "O Positivo (O+)",
    val alergias: String = "Penicilina, Nueces de árbol",
    val condiciones: String = "Asma leve controlada",
    val medicamentos: String = "Salbutamol (solo rescate), Loratadina 10mg",
    val contactoEmergenciaNombre: String = "María García",
    val contactoEmergenciaTelefono: String = "+52 55 987 5432",
    val contactoEmergenciaParentesco: String = "Madre"
)

// ── Vacuna ───────────────────────────────────────────────────────
data class RegistroVacuna(
    val id: String,
    val nombre: String,
    val fecha: String,
    val dosis: String,
    val esquema: EsquemaVacuna,
    val laboratorio: String = "",
    val centro: String = ""
)

enum class EsquemaVacuna { COMPLETO, INCOMPLETO, PENDIENTE }

// ── Cita ─────────────────────────────────────────────────────────
data class Cita(
    val id: String,
    val vacuna: String,
    val fecha: String,
    val hora: String,
    val centro: String,
    val estado: EstadoCita = EstadoCita.PROGRAMADA
)

enum class EstadoCita { PROGRAMADA, CONFIRMADA, COMPLETADA, CANCELADA }

// ── Centro Vacunación ────────────────────────────────────────────
data class CentroVacunacion(
    val id: String,
    val nombre: String,
    val direccion: String,
    val distancia: Double,
    val personasEnFila: Int,
    val tiempoEspera: Int,
    val estaAbierto: Boolean
)

// ── Notificación ─────────────────────────────────────────────────
data class Notificacion(
    val id: String,
    val titulo: String,
    val tiempo: String,
    val tipo: TipoNotificacion
)

enum class TipoNotificacion { CONFIRMACION, RECORDATORIO, INFORMACION }

// ── Datos de ejemplo ─────────────────────────────────────────────
object DatosEjemplo {

    val paciente = Paciente()

    val historialVacunas = listOf(
        RegistroVacuna("1", "COVID-19 (Pfizer-BioNTech)", "15 Sep 2023",
            "Refuerzo (3ra)", EsquemaVacuna.COMPLETO, "Pfizer", "Centro Médico San Juan"),
        RegistroVacuna("2", "Influenza Estacional", "12 May 2023",
            "Única Anual", EsquemaVacuna.COMPLETO, "Sanofi", "Hospital de Clínicas"),
        RegistroVacuna("3", "Hepatitis B", "23 Feb 2023",
            "3ra Dosis", EsquemaVacuna.INCOMPLETO, "GSK", "Centro Salud Norte")
    )

    val proximasCitas = listOf(
        Cita("1", "MMR Prueba", "30 Ene 2024", "10:00 AM",
            "Centro Salud Norte", EstadoCita.CONFIRMADA),
        Cita("2", "Hepatitis A", "24 Abr 2024", "11:00 AM",
            "Clínica Salud Norte", EstadoCita.PROGRAMADA),
        Cita("3", "Refuerzo Influenza", "17 May 2024", "11:00 AM",
            "Centro Médico San Juan", EstadoCita.PROGRAMADA)
    )

    val centrosCercanos = listOf(
        CentroVacunacion("1", "Centro Salud Norte", "Av. Insurgentes 1001",
            1.3, 12, 18, true),
        CentroVacunacion("2", "Hospital de Clínicas", "Av. Córdoba 2351",
            2.1, 7, 10, true),
        CentroVacunacion("3", "Centro Médico San Juan", "Reforma 450",
            3.4, 20, 30, false)
    )

    val notificaciones = listOf(
        Notificacion("1", "Cita confirmada para el 14 de Oct",
            "Hace 2 horas", TipoNotificacion.CONFIRMACION),
        Notificacion("2", "Recordatorio: Ayuno de 8 horas",
            "Hace 5 horas", TipoNotificacion.RECORDATORIO),
        Notificacion("3", "Nueva campaña de vacunación",
            "Ayer", TipoNotificacion.INFORMACION)
    )
}
