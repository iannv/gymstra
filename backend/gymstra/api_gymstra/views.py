from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.authtoken.views import ObtainAuthToken
from rest_framework.authtoken.models import Token
from rest_framework import generics, permissions, status, viewsets
from .models import *
from .serializers import *

# Administrador
class RegistroView(APIView):
    def post(self, request, *args, **kwargs):
        serializer = AdministradorSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response('Administrador registrado', status=status.HTTP_201_CREATED)
        return Response('No se pudo registrar al administrador', 'Error: ', serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    

class IniciarSesionView(ObtainAuthToken):
    pass


class AdministradorPerfilView(generics.RetrieveUpdateAPIView):
    serializer_class = AdministradorSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Administrador.objects.all()
    
    def get_object(self):
        return self.request.user


# Alumno
class AlumnoView(viewsets.ModelViewSet):
    serializer_class = AlumnoSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Alumno.objects.all()


# CUOTA
class CuotaView(viewsets.ModelViewSet):
    serializer_class = CuotaSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Cuota.objects.all()


# CLASE
class ClaseView(viewsets.ModelViewSet):
    serializer_class = ClaseSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Clase.objects.all()


# ASISTENCIA
class AsistenciaView(viewsets.ModelViewSet):
    serializer_class = AsistenciaSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Asistencia.objects.all()


# ZONA MUSCULAR
class ZonaMuscularView(viewsets.ModelViewSet):
    serializer_class = ZonaMuscularSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = ZonaMuscular.objects.all()


# EJERCICIO
class EjercicioView(viewsets.ModelViewSet):
    serializer_class = EjercicioSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Ejercicio.objects.all()


# RUTINA
class RutinaView(viewsets.ModelViewSet):
    serializer_class = RutinaSerializer
    # permission_classes = [permissions.IsAuthenticated]
    permission_classes = [permissions.AllowAny]
    queryset = Rutina.objects.all()
