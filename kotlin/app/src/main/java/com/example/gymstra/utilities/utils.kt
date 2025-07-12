package com.example.gymstra.utilities

// Convertir primera letra de cada palabra en mayúscula
fun capitalizar(c: String): String {
    return c.lowercase().split(" ").joinToString(" ") { c -> c.replaceFirstChar { it.uppercase() }
    }
}

