from django.db import models
from django.core.validators import MinValueValidator, MaxValueValidator
from django.contrib.auth.models import AbstractUser
from .utils import calcularFechaVto

# ADMINISTRADOR
class Administrador(AbstractUser):
    id_administrador = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    nombre = models.CharField(max_length=255)
    email = models.EmailField(max_length=320, unique=True)
    clave = models.CharField(max_length=50)
    logo = models.ImageField(upload_to="logos/", default="logos/default_logo.png")
    
    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', 'clave', 'nombre']

    class Meta:
        db_table = 'Administradores'
        verbose_name = 'Administrador'
        verbose_name_plural = 'Administradores'
    
    def __str__(self):
        return self.nombre


# ALUMNO
class Alumno(models.Model):
    id_alumno = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    dni = models.CharField(max_length=8, blank=False, null=False)
    nombre = models.CharField(max_length=100, blank=False, null=False)
    apellido = models.CharField(max_length=100, blank=False, null=False)
    telefono = models.CharField(max_length=10)
    fecha_ingreso = models.DateField(auto_now_add=True, editable=False)
    vecesXsemana = models.IntegerField(validators=[MinValueValidator(1), MaxValueValidator(7)])
    fecha_ultimo_dia = models.DateField(auto_now=True)
    activo = models.BooleanField(default=True)
    id_administrador = models.ForeignKey(Administrador, on_delete=models.CASCADE)
    clase = models.ManyToManyField('Clase')
    rutina = models.ManyToManyField('Rutina')
    
    class Meta:
        db_table = 'Alumnos'
        verbose_name = 'Alumno'
        verbose_name_plural = 'Alumnos'
    
    def __str__(self):
        return f'{self.nombre} {self.apellido}'


# CUOTA
class Cuota(models.Model):
    id_cuota = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    fecha_pago = models.DateField(auto_now=True)
    fecta_vto = models.DateField(default=calcularFechaVto)
    estado = models.BooleanField(blank=False, null=False, default=False)
    monto = models.DecimalField(decimal_places=2, max_digits=10)
    id_alumno = models.ForeignKey(Alumno, on_delete=models.CASCADE)
    
    class Meta:
        db_table = 'Cuotas'
        verbose_name = 'Cuota'
        verbose_name_plural = 'Cuotas'
    
    def __str__(self):
        return self.monto


# CLASE
class Clase(models.Model):
    id_clase = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    dias = models.CharField(max_length=255)
    horario = models.CharField(max_length=255)
    precio = models.DecimalField(decimal_places=2, max_digits=10)
    cantidad_alumnos = models.IntegerField()
    
    class Meta:
        db_table = 'Clases'
        verbose_name = 'Clase'
        verbose_name_plural = 'Clases'
    
    def __str__(self):
        return self.dias


# ASISTENCIA
class Asistencia(models.Model):
    id_asistencia = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    fecha = models.DateField(auto_now_add=True, editable=False)
    monto = models.DecimalField(decimal_places=2, max_digits=10)
    id_clase = models.ForeignKey(Clase, on_delete=models.CASCADE)
    id_alumno = models.ForeignKey(Alumno, on_delete=models.CASCADE)
    
    class Meta:
        db_table = 'Asistencias'
        verbose_name = 'Asistencia'
        verbose_name_plural = 'Asistencias'
    
    def __str__(self):
        return self.fecha


# ZONA MUSCULAR
class ZonaMuscular(models.Model):
    id_zona_muscular = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    zona = models.CharField(max_length=50)
    
    class Meta:
        db_table = 'Zonas Musculares'
        verbose_name = 'Zona Muscular'
        verbose_name_plural = 'Zonas Musculares'
    
    def __str__(self):
        return self.zona


# EJERCICIO
class Ejercicio(models.Model):
    id_ejercicio = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    nombre = models.CharField(max_length=255)
    id_zona_muscular = models.ForeignKey(ZonaMuscular, on_delete=models.CASCADE)
    
    class Meta:
        db_table = 'Ejercicios'
        verbose_name = 'Ejercicio'
        verbose_name_plural = 'Ejercicios'
    
    def __str__(self):
        return self.nombre


# RUTINA
class Rutina(models.Model):
    id_rutina = models.IntegerField(primary_key=True, unique=True, blank=False, null=False)
    nombre = models.CharField(max_length=100)
    series = models.IntegerField()  
    repeticiones = models.IntegerField()
    ejercicio = models.ManyToManyField('Ejercicio')
    
    class Meta:
        db_table = 'Rutinas'
        verbose_name = 'Rutina'
        verbose_name_plural = 'Rutinas'
    
    def __str__(self):
        return self.nombre
