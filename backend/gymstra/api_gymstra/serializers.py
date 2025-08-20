from .models import *
from rest_framework import serializers

# Administrador
class AdministradorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Administrador
        fields = '__all__'
        extra_kwargs = {'clave': {'write_only': True}}


# Alumno
class AlumnoSerializer(serializers.ModelSerializer):
    rutina = serializers.PrimaryKeyRelatedField(required=False, allow_empty=True, many=True, queryset=Rutina.objects.all())
    
    class Meta:
        model = Alumno
        fields = '__all__'
        extra_kwargs = {'fecha_ingreso': {'read_only': True}}


# CUOTA
class CuotaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Cuota
        fields = '__all__'


# CLASE
# class ClaseSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = Clase
#         fields = '__all__'


# ASISTENCIA
class AsistenciaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Asistencia
        fields = '__all__'


# ZONA MUSCULAR
class ZonaMuscularSerializer(serializers.ModelSerializer):
    class Meta:
        model = ZonaMuscular
        fields = '__all__'


# EJERCICIO
class EjercicioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ejercicio
        fields = ['id_ejercicio', 'nombre', 'id_zona_muscular']


# RUTINA EJERCICIO
class RutinaEjercicioSerializer(serializers.ModelSerializer):
    ejercicio = EjercicioSerializer(read_only=True)
    
    class Meta:
        model = RutinaEjercicio
        fields = ['id_rutina_ejercicio', 'ejercicio', 'series', 'repeticiones']

    def to_representation(self, instance):
        rep = super().to_representation(instance)
        if isinstance(rep['repeticiones'], int):
            rep['repeticiones'] = [rep['repeticiones']]
        return rep


# RUTINA
class RutinaSerializer(serializers.ModelSerializer):
    ejercicios = RutinaEjercicioSerializer(source='rutinaejercicio_set', many=True, read_only=True)

    class Meta:
        model = Rutina
        fields = ['id_rutina', 'nombre', 'ejercicios']