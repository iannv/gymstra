from django.contrib import admin
from .models import *

# SuperUser: ADMIN - a@gmail.com - 123
admin.site.register(Administrador)
admin.site.register(Alumno)
admin.site.register(Cuota)
admin.site.register(Clase)
admin.site.register(Ejercicio)
admin.site.register(Rutina)
admin.site.register(Asistencia)
admin.site.register(ZonaMuscular)