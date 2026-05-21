package com.kelompok3.oceana

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://fvoxsvuoofyxuwzdvpij.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZ2b3hzdnVvb2Z5eHV3emR2cGlqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzkwNTIyMzIsImV4cCI6MjA5NDYyODIzMn0.rDQ1D184wgNFanQZW_Q8Etp9lh4sG9hFFr7-AJyz2DM"
) {
    install(Auth)
    install(Postgrest)
}